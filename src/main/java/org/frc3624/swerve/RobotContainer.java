package org.frc3624.swerve;

import org.frc3624.swerve.commands.SwerveDrive;
import org.frc3624.swerve.subsystems.Drive;

import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
	private final Drive drive = new Drive();
	private final SwerveDrive swerveDrive = new SwerveDrive();
	// Controller and Buttons
    public RobotContainer() {
        configureButtonBindings();
        drive.setDefaultCommand(swerveDrive);
    }

    private void configureButtonBindings() {
    }

    public Command getAutonomousCommand() {
		return null;
	}
}
