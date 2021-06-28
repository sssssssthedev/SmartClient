package ro.sssssssthedev.AntiCheat.update.box;

import lombok.Getter;

@Getter
public class PlayerPosition {
    private double minX, centerX, maxX;
    private double minZ, centerZ, maxZ;

    private final long timestamp = System.currentTimeMillis();

    public PlayerPosition(final double x, final double z) {
        this.minX = x - 0.3;
        this.centerX = x;
        this.maxX = x + 0.3;
        this.minZ = z - 0.3;
        this.centerZ = z;
        this.maxZ = z + 0.3;
    }

    public double getDistanceSquared(final PlayerPosition hitbox) {
        double dx = Math.min(Math.abs(hitbox.centerX - minX), Math.abs(hitbox.centerX - maxX));
        double dz = Math.min(Math.abs(hitbox.centerZ - minZ), Math.abs(hitbox.centerZ - maxZ));
        return dx * dx + dz * dz;
    }
}