package org.frc3624.swerve;

import org.frc3624.swerve.subsystems.Drive;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private static OI oi;

    private static Drive drivetrain;

    public static OI getOi() {
        return oi;
    }

    @Override
    public void robotInit() {
        oi = new OI();
        drivetrain = Drive.getInstance();
    }

    @Override
    public void robotPeriodic() {
		CommandScheduler.getInstance().run();
    }
}
