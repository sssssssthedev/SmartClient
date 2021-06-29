package net.sssssssthedev.SmartClient.event.impl;

import net.sssssssthedev.SmartClient.event.Event;

public class MoveEvent extends Event {
    private double x;

    private double y;

    private double z;

    private boolean safeWalk;

    private boolean cancelSprint;

    public MoveEvent(double x, double y, double z) {
        this(x, y, z, false, true);
    }

    public MoveEvent(double x, double y, double z, boolean safeWalk, boolean cancelSprint) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.safeWalk = safeWalk;
        this.cancelSprint = cancelSprint;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public boolean isSafeWalk() {
        return this.safeWalk;
    }

    public boolean isCancelSprint() {
        return this.cancelSprint;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setSafeWalk(boolean safeWalk) {
        this.safeWalk = safeWalk;
    }

    public void setCancelSprint(boolean cancelSprint) {
        this.cancelSprint = cancelSprint;
    }
}

