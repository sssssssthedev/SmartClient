package ro.sssssssthedev.AntiCheat.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.data.PlayerData;

public final class PlayerListener implements Listener {

    public PlayerListener() {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            // Grab the player data from the player
            final PlayerData playerData = AntiCheatAPI.INSTANCE.getPlayerDataManager().getData(player);
           
            // Create the packet handler using the version handler with the appropriate version
            AntiCheatAPI.INSTANCE.getVersionHandler().create(playerData);
        });
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        final PlayerData playerData = AntiCheatAPI.INSTANCE.getPlayerDataManager().getData(event.getPlayer());

        if (event.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN) {
            playerData.setLastTeleport(System.currentTimeMillis());
        } else {
            playerData.setLastUnknownTeleport(System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            final PlayerData playerData = AntiCheatAPI.INSTANCE.getPlayerDataManager().getData((Player) event.getEntity());

            switch (event.getCause()) {
                case ENTITY_ATTACK:
                    playerData.setLastAttackDamage(System.currentTimeMillis());
                    break;

                case FALL:
                    playerData.setLastFallDamage(System.currentTimeMillis());
                    break;
            }
        }
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        // Grab the player data from the player
        final PlayerData playerData = AntiCheatAPI.INSTANCE.getPlayerDataManager().getData(player);

        // Create the packet handler using the version handler with the appropriate version
        AntiCheatAPI.INSTANCE.getVersionHandler().create(playerData);
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            final PlayerData playerData = AntiCheatAPI.INSTANCE.getPlayerDataManager().getData(player);

            playerData.getActionManager().onBukkitDig();
        }
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        // Get the player from the event
        final Player player = event.getPlayer();

        // Remove the player from the player data manager to prevent memory leaks
        AntiCheatAPI.INSTANCE.getPlayerDataManager().remove(player);
    }
}
