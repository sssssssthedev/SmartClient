package ro.sssssssthedev.AntiCheat.check.impl.velocity;

import org.bukkit.Location;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PositionCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.data.type.VelocityManager;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;

@CheckData(name = "Velocity (B)")
public final class VelocityB extends PositionCheck {
    private double buffer;

    public VelocityB(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final double deltaY = to.getY() - from.getY();

        if (from.getY() % 1.0 == 0.0 && to.getY() % 1.0 > 0.0 && deltaY > 0.0 && deltaY < 0.41999998688697815D && !playerData.getPositionManager().getBelowBlocks().get()) {
            final double velocityY = playerData.getVelocityManager().getVelocities().stream().mapToDouble(VelocityManager.VelocitySnapshot::getVertical).min().orElse(0.0);

            if (velocityY > 0.0) {
                final double ratio = deltaY / velocityY;

                if (ratio < 0.99) {
                    if (++buffer > 3) {
                        this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                    }
                } else {
                    buffer = 0;
                }
            }
        }
    }
}
