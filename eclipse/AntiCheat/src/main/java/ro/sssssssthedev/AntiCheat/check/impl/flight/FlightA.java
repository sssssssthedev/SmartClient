package ro.sssssssthedev.AntiCheat.check.impl.flight;

import org.bukkit.Location;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PositionCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;
import ro.sssssssthedev.AntiCheat.utils.MathUtil;
import ro.sssssssthedev.AntiCheat.utils.ReflectionUtil;

@CheckData(name = "Flight (A)")
public final class FlightA extends PositionCheck {
    private Location lastGroundLocation = null;

    public FlightA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final boolean properMotion = ReflectionUtil.getMotionY(playerData) > 0.0;
        final boolean touchingAir = playerData.getPositionManager().getTouchingAir().get() && !playerData.getPositionManager().getTouchingClimbable().get();

        if (playerData.getPositionManager().getTouchingLiquid().get() || playerData.getVelocityManager().getMaxHorizontal() > 0.0 || playerData.getVelocityManager().getMaxVertical() > 0.0 || getPlayerData().getPositionManager().getTouchingWeb().get()) {
            return;
        }

        if (touchingAir && properMotion) {
            final double deltaY = to.getY() - from.getY();

            if (deltaY >= 0.0 && lastGroundLocation != null) {
                final double horizontalDistance = MathUtil.vectorDistance(to, lastGroundLocation);

                if (horizontalDistance > 10.d) {
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }
            }
        } else {
            lastGroundLocation = to;
        }
    }
}
