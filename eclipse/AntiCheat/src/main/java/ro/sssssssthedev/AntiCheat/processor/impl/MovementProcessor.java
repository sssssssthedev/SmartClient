package ro.sssssssthedev.AntiCheat.processor.impl;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ro.sssssssthedev.AntiCheat.check.type.PositionCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;
import ro.sssssssthedev.AntiCheat.processor.type.Processor;

public final class MovementProcessor implements Processor<PositionUpdate> {

    @Override
    public void process(final PlayerData playerData, final PositionUpdate positionUpdate) {
        final Player player = playerData.getPlayer();

        // Get the from and the to location
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        // Player did not move
        if (from.distance(to) == 0.0) {
            return;
        }

        // Spoofable but we will have bad packets checks for all of them.
        if (player.isInsideVehicle() || player.isFlying() || player.getAllowFlight() || player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        // We do not want checks to mess up due to player being inside an unloaded chunk
        if (!player.getWorld().isChunkLoaded(to.getBlockX() >> 4, to.getBlockZ() >> 4)) {
            return;
        }

        // Teleporting bad
        if (playerData.getActionManager().getTeleported().get()) {
            return;
        }

        //noinspection unchecked
        playerData.getCheckManager().getChecks().stream().filter(PositionCheck.class::isInstance).forEach(check -> check.process(positionUpdate));
        playerData.getPositionManager().updatePositionFlags(to);
    }
}
