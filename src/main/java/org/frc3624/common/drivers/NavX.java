package org.frc3624.common.drivers;

import com.kauailabs.navx.frc.AHRS;

public class NavX {
    private final AHRS ahrs = new AHRS();

    public boolean isConnected() {
        return ahrs.isConnected();
    }
    public double getAngle() {
        return ahrs.getAngle();
    }
    public double getRotationalVelocity() {
        return ahrs.getRate();
    }
    public boolean isRotating() {
        return ahrs.isRotating();
    }
    public void calibrate() {
        ahrs.calibrate();
    }

    /**
     * TODO
     * Add an setInverted method eventually
     */

    public void setAngleAdjustment(double adjustment) {
        ahrs.setAngleAdjustment(adjustment);
    }
    public float getX() {
        return ahrs.getDisplacementX();
    }
    public float getY() {
        return ahrs.getDisplacementY();
    }
    public float getXVelocity() {
        return ahrs.getVelocityX();
    }
    public float getYVelocity() {
        return ahrs.getVelocityY();
    }
}
