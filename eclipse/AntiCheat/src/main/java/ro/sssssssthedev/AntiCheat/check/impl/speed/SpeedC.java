package ro.sssssssthedev.AntiCheat.check.impl.speed;

import org.bukkit.Location;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PositionCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.data.type.PositionManager;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;
import ro.sssssssthedev.AntiCheat.utils.MathUtil;

@CheckData(name = "Speed (C)")
public final class SpeedC extends PositionCheck {
    private double lastHorizontal;
    private int buffer;

    public SpeedC(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final PositionManager positionManager = playerData.getPositionManager();

        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final double horizontalDistance = MathUtil.vectorDistance(from, to);

        if ((System.currentTimeMillis() - playerData.getLastUnknownTeleport()) < 1000L || (getPlayerData().getClientTicks()) < 100 || positionManager.getTouchingClimbable().get() || positionManager.getTouchingIllegalBlocks().get() || positionManager.getTouchingHalfBlocks().get() || positionManager.getBelowBlocks().get()) {
            return;
        }

        if (horizontalDistance >= 1) {
            this.handleViolation().addViolation(ViolationLevel.HIGH).create();
        }
    }
}
