package ro.sssssssthedev.AntiCheat.check.impl.flight;

import org.bukkit.Location;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PositionCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;
import ro.sssssssthedev.AntiCheat.utils.MathUtil;
import ro.sssssssthedev.AntiCheat.utils.ReflectionUtil;

@CheckData(name = "Flight (D)")
public final class FlightD extends PositionCheck {
    private int airTicks, horizontalBuffer, hoverBuffer;

    public FlightD(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final boolean touchingAir = playerData.getPositionManager().getTouchingAir().get() && !playerData.getPositionManager().getTouchingClimbable().get();
        final boolean properMotion = ReflectionUtil.getMotionY(playerData) > 0.0;

        if (playerData.getVelocityManager().getMaxHorizontal() > 0.0 || playerData.getVelocityManager().getMaxVertical() > 0.0) {
            return;
        }

        if (touchingAir && properMotion) {
            ++airTicks;

            final double deltaY = to.getY() - from.getY();
            final double horizontalDistance = MathUtil.vectorDistance(from, to);

            if (airTicks > 8 && deltaY >= 0.0 && horizontalDistance > 0.15) {
                if (++horizontalBuffer > 10) {
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }
            } else {
                horizontalBuffer = 0;
            }

            if (airTicks > 9 && deltaY == 0.0) {
                if (++hoverBuffer > 9) {
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }
            } else {
                hoverBuffer = 0;
            }
        } else {
            hoverBuffer = 0;
            horizontalBuffer = 0;

            airTicks = 0;
        }
    }
}
