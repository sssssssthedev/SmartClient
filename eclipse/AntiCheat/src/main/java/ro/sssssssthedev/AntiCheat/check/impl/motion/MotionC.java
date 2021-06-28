package ro.sssssssthedev.AntiCheat.check.impl.motion;

import org.bukkit.Location;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PositionCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;

@CheckData(name = "Flight (A)")
public final class MotionC extends PositionCheck {

    private double lastDeltaY;
    private double buffer;
    private int airTicks;

    public MotionC(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final double deltaY = to.getY() - from.getY();

        if (playerData.getVelocityManager().getMaxVertical() > 0.0) {
            return;
        }

        final boolean touchingAir = playerData.getPositionManager().getTouchingAir().get();

        if (touchingAir) {
            ++airTicks;

            if (airTicks > 9) {
                final double estimation = (lastDeltaY * 0.9800000190734863) - 0.08;

                if (Math.abs(deltaY + 0.0980000019) < 0.005) {
                    buffer = 0.0D;
                    return;
                }

                if (Math.abs(estimation - deltaY) > 0.002) {
                    buffer += 1.5;

                    if (buffer > 5) {
                        this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                    }
                } else {
                    buffer = Math.max(0, buffer - 1.25);
                }
            }
        } else {
            airTicks = 0;

            buffer = Math.max(0, buffer - 10D);
        }

        this.lastDeltaY = deltaY;
    }
}
