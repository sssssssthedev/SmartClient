package ro.sssssssthedev.AntiCheat.alert;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.config.impl.CheckConfig;
import ro.sssssssthedev.AntiCheat.config.impl.MessageConfig;
import ro.sssssssthedev.AntiCheat.utils.ColorUtil;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.check.Check;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.hook.DiscordManager;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public final class Alert {
    @Getter
    private int violations;

    private final List<Long> alerts = new ArrayList<>();
    public Check check;

    private final String base = AntiCheatAPI.INSTANCE.getConfigManager().getConfig(MessageConfig.class).getAlertMessage();

    /**
     *
     * @param violationLevel - The violations you want to add for a specific check
     * @return - Returns the alert manager. so we can have a nice look. (this.handleAlert().addViolation(ViolationLevel.HIGH).create())
     */
    public Alert addViolation(final ViolationLevel violationLevel) {
        final long now = System.currentTimeMillis();


        // We don't want double alerts
        if (!alerts.contains(now) && check.isEnabled()) {
            // Add alert to the recent alerts list.
            alerts.add(now);

            // Get the level from the enum and get the threshold from the check
            final int level = violationLevel.getLevel();
            final int threshold = check.getThreshold();

            // Add level to the current check violations
            violations += level;

            // If the violations exceeds or is equal to the threshold, ban.
            if (violations >= threshold && check.isAutobans()) {
                final String format = AntiCheatAPI.INSTANCE.getConfigManager().getConfig(CheckConfig.class).getPunishment(check).replace("%player%", check.getPlayerData().getPlayer().getName());

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), format);
            }
        }

        return this;
    }

    /*
     * This is a void so its the final statement of the method. After you create, you can't go back to prevent confusion.
     */
    public void create() {

        final DiscordManager discordManager = AntiCheatAPI.INSTANCE.getDiscordManager();

        final PlayerData playerData = check.getPlayerData();

        final String playerName = playerData.getPlayer().getName();
        final String checkName = check.getCheckName();
        final String violationsMessage = String.valueOf(violations);

        final String alert = ColorUtil.format(base).replace("%player%", playerName).replace("%check%", checkName).replace("%vl%", violationsMessage);



            AntiCheatAPI.INSTANCE.getAlertExecutor().execute(() ->
                    Bukkit.getOnlinePlayers()
                            .stream()
                            .filter(toSend -> toSend.hasPermission("overflow.alerts"))
                            .forEach(toSend -> toSend.sendMessage(alert)));

            if (discordManager != null) {
                discordManager.log(check);
            
        }
    }

    public int getViolations() {
        return violations;
    }

    public void setViolations(int violations) {
        this.violations = violations;
    }
}
