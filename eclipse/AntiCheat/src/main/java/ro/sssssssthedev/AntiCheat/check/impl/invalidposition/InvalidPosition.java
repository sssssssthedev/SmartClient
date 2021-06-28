package ro.sssssssthedev.AntiCheat.check.impl.invalidposition;

import org.bukkit.Location;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PositionCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;
import ro.sssssssthedev.AntiCheat.utils.MathUtil;
import ro.sssssssthedev.AntiCheat.utils.ReflectionUtil;

@CheckData(name = "InvalidPosition")
public final class InvalidPosition extends PositionCheck {

    public InvalidPosition(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final double horizontalDistance = MathUtil.vectorDistance(from, to);

        if (horizontalDistance > 1e-12 && horizontalDistance < 0.00089) {
            final double velocityV = playerData.getVelocityManager().getMaxVertical();
            final double velocityH = playerData.getVelocityManager().getMaxHorizontal();

            final double motionX = ReflectionUtil.getMotionX(playerData);
            final double motionY = ReflectionUtil.getMotionY(playerData);
            final double motionZ = ReflectionUtil.getMotionZ(playerData);

            if (velocityV == Math.abs(motionY) && Math.hypot(motionX, motionZ) == velocityH) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        }
    }
}
