package org.frc3624.swerve;

public class RobotConstants {
	public enum DriveTrainFront {
		rightDrive(0), rightSpin(4), leftDrive(1), leftSpin(5),
		rightEncoder(0), leftEncoder(1),
		rightOffset(-Math.toRadians(0.0)), leftOffset(-Math.toRadians(0.0));

		private final double id;
		private DriveTrainFront(double id) {
			this.id = id;
		}
		public int val() {
			return (int)id;
		}
	}
	public enum DriveTrainBack {
		rightDrive(2), rightSpin(6), leftDrive(3), leftSpin(7),
		rightEncoder(2), leftEncoder(3);

		private final int id;
		private DriveTrainBack(int id) {
			this.id = id;
		}
		public int val() {
			return id;
		}
	}
	public enum DriveOffsets {
		frontRight(-Math.toRadians(0.0)), frontLeft(-Math.toRadians(0.0)),
		backRight(-Math.toRadians(0.0)), backLeft(-Math.toRadians(0.0));
		private final double val;
		private DriveOffsets(double val) {
			this.val = val;
		}
		public double val() {
			return val;
		}
	}

	public static final double TRACK_WIDTH = 19.5;
	public static final double WHEELBASE = 23.5;
}
