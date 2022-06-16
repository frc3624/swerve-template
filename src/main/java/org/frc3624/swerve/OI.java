package org.frc3624.swerve;

import org.frc3624.swerve.commands.SwerveDrive;
import org.frc3624.swerve.subsystems.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;

public class OI {
	private Joystick driveJoystick = new Joystick(0);
	private Joystick spinJoystick = new Joystick(1);

	private final Drive drive = new Drive();
	private final SwerveDrive swerveDrive = new SwerveDrive();
	// Controller and Buttons
	public OI() {
		configureButtonBindings();
		drive.setDefaultCommand(swerveDrive);
	}

	public Joystick getDriveStick() {
		return driveJoystick;
	}
	public Joystick getSpinStick() {
		return spinJoystick;
	}

	private void configureButtonBindings() {
	}

	public Command getAutonomousCommand() {
		return null;
	}
}
