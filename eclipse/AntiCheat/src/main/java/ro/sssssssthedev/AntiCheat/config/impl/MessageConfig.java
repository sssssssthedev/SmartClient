package ro.sssssssthedev.AntiCheat.config.impl;

import org.bukkit.configuration.file.YamlConfiguration;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.AntiCheatPlugin;
import ro.sssssssthedev.AntiCheat.config.type.Config;

import java.io.File;

public final class MessageConfig implements Config {

    private File file;
    private YamlConfiguration config;

    @Override
    public void generate() {
        this.create();

        if (!config.contains("messages")) {
            config.set("messages", "");
        }

        if (!config.contains("messages.alert.message")) {
            config.set("messages.alert.message", "&7[&cOverFlow&7] &c%player% &7failed &c%check% &7[VL: %vl%]");
        }

        if (!config.contains("messages.alert.broadcast")) {
            config.set("messages.alert.broadcast", "&7[&cOverFlow&7] &c%player% &7was detected &ccheating &7and was removed from the network.");
        }

        if (!config.contains("messages.judgement.broadcast")) {
            config.set("messages.judgement.broadcast", "&7[&cOverFlow&7] &c%player% &7was detected &ccheating &7and was removed from the network.");
        }

        if (!config.contains("messages.judgement.start")) {
            config.set("messages.judgement.start", "&7[&cOverFlow&7] &cStarting the judgement day...");
        }

        try {
            config.save(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void create() {
        final AntiCheatPlugin plugin = AntiCheatAPI.INSTANCE.getPlugin();

        this.file = new File(plugin.getDataFolder(), "messages.yml");

        if (!file.exists()) {
            try {
                file.getParentFile().mkdir();

                plugin.saveResource("messages.yml", false);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.config = new YamlConfiguration();

        try {
            config.load(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAlertMessage() {
        return config.getString("messages.alert.message");
    }

    public String getAlertBroadcastMessage() {
        return config.getString("messages.alert.broadcast");
    }

    public String getJudgementMessage() {
        return config.getString("messages.judgement.broadcast");
    }

    public String getJudgementStart() {
        return config.getString("messages.judgement.start");
    }
}
