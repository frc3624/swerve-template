package org.frc3624.swerve.subsystems;

import static org.frc3624.swerve.RobotConstants.TRACKWIDTH;
import static org.frc3624.swerve.RobotConstants.WHEELBASE;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import org.frc3624.common.Utilities;
import org.frc3624.common.drivers.Mk2SwerveModuleBuilder;
import org.frc3624.common.drivers.NavX;
import org.frc3624.common.drivers.SwerveModule;
import org.frc3624.common.math.Vector2;
import org.frc3624.swerve.RobotConstants.DriveOffsets;
import org.frc3624.swerve.RobotConstants.DriveTrainBack;
import org.frc3624.swerve.RobotConstants.DriveTrainFront;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {

	public static Drive instance;

	private final SwerveModule frontLeftModule = new Mk2SwerveModuleBuilder(
		new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0))
		.angleEncoder(new AnalogInput(DriveTrainFront.leftEncoder.val()), DriveOffsets.frontLeft.val())
		.angleMotor(new CANSparkMax(DriveTrainFront.leftSpin.val(), CANSparkMaxLowLevel.MotorType.kBrushless),
				Mk2SwerveModuleBuilder.MotorType.NEO)
		.driveMotor(new CANSparkMax(DriveTrainFront.leftDrive.val(), CANSparkMaxLowLevel.MotorType.kBrushless),
				Mk2SwerveModuleBuilder.MotorType.NEO)
		.build();
	private final SwerveModule frontRightModule = new Mk2SwerveModuleBuilder(
		new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0))
		.angleEncoder(new AnalogInput(DriveTrainFront.rightEncoder.val()), DriveOffsets.frontRight.val())
		.angleMotor(new CANSparkMax(DriveTrainFront.rightSpin.val(), CANSparkMaxLowLevel.MotorType.kBrushless),
				Mk2SwerveModuleBuilder.MotorType.NEO)
		.driveMotor(new CANSparkMax(DriveTrainFront.rightDrive.val(), CANSparkMaxLowLevel.MotorType.kBrushless),
				Mk2SwerveModuleBuilder.MotorType.NEO)
		.build();
	private final SwerveModule backLeftModule = new Mk2SwerveModuleBuilder(
		new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0))
		.angleEncoder(new AnalogInput(DriveTrainBack.leftEncoder.val()), DriveOffsets.backLeft.val())
		.angleMotor(new CANSparkMax(DriveTrainBack.leftSpin.val(), CANSparkMaxLowLevel.MotorType.kBrushless),
				Mk2SwerveModuleBuilder.MotorType.NEO)
		.driveMotor(new CANSparkMax(DriveTrainBack.leftDrive.val(), CANSparkMaxLowLevel.MotorType.kBrushless),
				Mk2SwerveModuleBuilder.MotorType.NEO)
		.build();
	private final SwerveModule backRightModule = new Mk2SwerveModuleBuilder(
		new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0))
		.angleEncoder(new AnalogInput(DriveTrainBack.rightEncoder.val()), DriveOffsets.backRight.val())
		.angleMotor(new CANSparkMax(DriveTrainBack.rightSpin.val(), CANSparkMaxLowLevel.MotorType.kBrushless),
				Mk2SwerveModuleBuilder.MotorType.NEO)
		.driveMotor(new CANSparkMax(DriveTrainBack.rightDrive.val(), CANSparkMaxLowLevel.MotorType.kBrushless),
				Mk2SwerveModuleBuilder.MotorType.NEO)
		.build();

	private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
		new Translation2d(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
		new Translation2d(TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
		new Translation2d(-TRACKWIDTH / 2.0, WHEELBASE / 2.0),
		new Translation2d(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0)
	);

	private final NavX gyroscope = new NavX();

	public Drive() {
		gyroscope.calibrate();

		frontLeftModule.setName("Front Left");
		frontRightModule.setName("Front Right");
		backLeftModule.setName("Back Left");
		backRightModule.setName("Back Right");
	}

	public static Drive getInstance() {
		return instance == null ? new Drive() : instance;
	}

	@Override
	public void periodic() {
		frontLeftModule.updateSensors();
		frontRightModule.updateSensors();
		backLeftModule.updateSensors();
		backRightModule.updateSensors();

		SmartDashboard.putNumber("Front Left Module Angle", Math.toDegrees(frontLeftModule.getCurrentAngle()));
		SmartDashboard.putNumber("Front Right Module Angle", Math.toDegrees(frontRightModule.getCurrentAngle()));
		SmartDashboard.putNumber("Back Left Module Angle", Math.toDegrees(backLeftModule.getCurrentAngle()));
		SmartDashboard.putNumber("Back Right Module Angle", Math.toDegrees(backRightModule.getCurrentAngle()));

		SmartDashboard.putNumber("Gyroscope Angle", gyroscope.getAngle());

		frontLeftModule.updateState(TimedRobot.kDefaultPeriod);
		frontRightModule.updateState(TimedRobot.kDefaultPeriod);
		backLeftModule.updateState(TimedRobot.kDefaultPeriod);
		backRightModule.updateState(TimedRobot.kDefaultPeriod);
	}

	/**
	 * Base Swerve Drive Method
	 * @param translation
	 * @param theta
	 * @param fieldOriented
	 */
	private void drive(Translation2d translation, double theta, boolean fieldOriented) {
		theta *= 2.0 / Math.hypot(WHEELBASE, TRACKWIDTH);
		ChassisSpeeds speeds;
		if (fieldOriented) {
			speeds = ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), theta,
					Rotation2d.fromDegrees(gyroscope.getAngle()));
		} else {
			speeds = new ChassisSpeeds(translation.getX(), translation.getY(), theta);
		}

		SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);
		frontLeftModule.setTargetVelocity(states[0].speedMetersPerSecond, states[0].angle.getRadians());
		frontRightModule.setTargetVelocity(states[1].speedMetersPerSecond, states[1].angle.getRadians());
		backLeftModule.setTargetVelocity(states[2].speedMetersPerSecond, states[2].angle.getRadians());
		backRightModule.setTargetVelocity(states[3].speedMetersPerSecond, states[3].angle.getRadians());
	}

	/**
	 * Non-Field Oriented Swerve Drive Method. Takes in the (x,y,θ) for the swerve
	 * drive operation.
	 * @param transY [0,1]
	 * @param transX [0,1]
	 * @param theta [0,1]
	 */
	public void swerveDrive(double transY, double transX, double theta) {
		transY = Utilities.deadband(transY);
		// Square the transY stick
		transY = Math.copySign(Math.pow(transY, 2.0), transY);

		transX = Utilities.deadband(transX);
		// Square the transX stick
		transX = Math.copySign(Math.pow(transX, 2.0), transX);

		theta = Utilities.deadband(theta);
		// Square the theta stick
		theta = Math.copySign(Math.pow(theta, 2.0), theta);

		drive(new Translation2d(transY, transX), theta, false);
	}
	/**
	 * Field Oriented Swerve Drive Method. Takes in the (x,y,θ) for the swerve
	 * drive operation.
	 * @param transY [0,1]
	 * @param transX [0,1]
	 * @param theta [0,1]
	 */
	public void swerveDriveField(double transY, double transX, double theta) {
		transY = Utilities.deadband(transY);
		// Square the transY stick
		transY = Math.copySign(Math.pow(transY, 2.0), transY);

		transX = Utilities.deadband(transX);
		// Square the transX stick
		transX = Math.copySign(Math.pow(transX, 2.0), transX);

		theta = Utilities.deadband(theta);
		// Square the theta stick
		theta = Math.copySign(Math.pow(theta, 2.0), theta);

		drive(new Translation2d(transY, transX), theta, true);
	}

	public void resetGyroscope() {
		gyroscope.setAngleAdjustment(0);
	}
}
