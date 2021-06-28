package ro.sssssssthedev.AntiCheat.check.impl.flight;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffectType;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PositionCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;
import ro.sssssssthedev.AntiCheat.utils.MathUtil;
import ro.sssssssthedev.AntiCheat.utils.ReflectionUtil;

@CheckData(name = "Flight (E)")
public final class FlightE extends PositionCheck {
    private int airTicks, buffer;
    private double total;

    public FlightE(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final boolean touchingAir = playerData.getPositionManager().getTouchingAir().get() && !playerData.getPositionManager().getTouchingClimbable().get();

        final int jumpModifier = MathUtil.getPotionEffectLevel(playerData.getPlayer(), PotionEffectType.JUMP);

        final double motionY = ReflectionUtil.getMotionY(playerData);
        final double deltaY = to.getY() - from.getY();

        final double jumpLimit = jumpModifier > 0 ? 1.25220341408 + (Math.pow(jumpModifier + 4.2, 2D) / 16D) : 1.25220341408;

        if (playerData.getVelocityManager().getMaxVertical() > 0.0 || playerData.getVelocityManager().getMaxHorizontal() > 0.0) {
            return;
        }

        if (touchingAir) {
            ++airTicks;

            if (airTicks > 9 && deltaY > 0.0 && motionY < 0.0) {
                total += deltaY;

                if (total > jumpLimit) {
                    if (++buffer > 4) {
                        this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                    }
                } else {
                    buffer = 0;
                }
            }
        } else {
            buffer = 0;
            airTicks = 0;
        }
    }
}
