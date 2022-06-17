package org.frc3624.swerve.commands;

import org.frc3624.swerve.Robot;
import org.frc3624.swerve.subsystems.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwerveDrive extends CommandBase {
	public SwerveDrive() {
		addRequirements(Drive.getInstance());
	}

	@Override
	public void execute() {
		Drive.getInstance().swerveDriveField(
			Robot.getOI().getDriveStick().getX(),
			Robot.getOI().getDriveStick().getY(),
			Robot.getOI().getSpinStick().getX()
		);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
