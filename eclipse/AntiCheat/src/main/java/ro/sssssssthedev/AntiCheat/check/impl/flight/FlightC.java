package ro.sssssssthedev.AntiCheat.check.impl.flight;

import org.bukkit.Location;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PositionCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;
import ro.sssssssthedev.AntiCheat.utils.ReflectionUtil;

@CheckData(name = "Flight (C)")
public final class FlightC extends PositionCheck {
    private int airTicks = 0, hoverBuffer, accelerationBuffer, blockAboveTicks;
    private double lastDeltaY, lastAcceleration;

    public FlightC(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        if (getPlayerData().getPositionManager().getBelowBlocks().get()) {
            if (blockAboveTicks < 100) blockAboveTicks+=20;
        } else {
            if (blockAboveTicks > 0) blockAboveTicks--;
        }

        if ((System.currentTimeMillis() - playerData.getLastUnknownTeleport()) < 1000L || getPlayerData().getPositionManager().getTouchingClimbable().get() || blockAboveTicks > 0 || playerData.getVelocityManager().getMaxVertical() > 0.0 || playerData.getVelocityManager().getMaxHorizontal() > 0.0) {
            hoverBuffer = 0;
            return;
        }

        // Get the difference between the current and the last Y
        final double deltaY = to.getY() - from.getY();

        // Get the Y acceleration of the player and the delta between the two accelerations
        final double acceleration = Math.abs(deltaY - lastDeltaY);
        final double deltaAcceleration = Math.abs(acceleration - lastAcceleration);

        // Making sure the player is only touching air and no other blocks to prevent false flags
        final boolean touchingAir = !playerData.getPositionManager().getTouchingClimbable().get() && (playerData.getPositionManager().getTouchingAir().get() || to.getY() % 0.015625 != 0.0) && !playerData.getPositionManager().getBelowBlocks().get();
        final boolean properMotion = ReflectionUtil.getMotionY(playerData) > 0.0;

        // If the player isn't touching any blocks / isn't on ground
        if (touchingAir && properMotion) {
            ++airTicks;

            // Invalid actions into booleans, we will use as the checks layer
            final boolean invalidAcceleration = airTicks > 8 && (deltaAcceleration == 0.0 || deltaAcceleration > 0.018);
            final boolean invalidHover = airTicks > 8 && deltaY == 0.0;

            if (invalidHover) {
                if (++hoverBuffer > 5) {
                    this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                }
            } else {
                hoverBuffer = Math.max(hoverBuffer - 2, 0);
            }

            if (invalidAcceleration) {
                if (++accelerationBuffer > 4) {
                    this.handleViolation().addViolation(ViolationLevel.LOW).create();
                }
            } else {
                accelerationBuffer = 0;
            }
        }

        // Parse the deltas.
        this.lastDeltaY = deltaY;
        this.lastAcceleration = acceleration;
    }
}
