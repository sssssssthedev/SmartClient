package ro.sssssssthedev.AntiCheat.config.impl;

import org.bukkit.configuration.file.YamlConfiguration;
import ro.sssssssthedev.AntiCheat.config.type.Config;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.AntiCheatPlugin;

import java.io.File;

public final class WebConfig implements Config {

    private File file;
    private YamlConfiguration config;

    @Override
    public void generate() {
        this.create();

        if (!config.contains("discord")) {
            config.set("discord", "");
        }

        if (!config.contains("discord.header")) {
            config.set("discord.header", "Overflow AntiCheat");
        }

        if (!config.contains("discord.body")) {
            config.set("discord.body", "| %player% failed %check% [VL: %vl%]");
        }

        if (!config.contains("discord.hook")) {
            config.set("discord.hook", "discord.gg/web");
        }

        if (!config.contains("discord.enabled")) {
            config.set("discord.enabled", false);
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

        this.file = new File(plugin.getDataFolder(), "web.yml");

        if (!file.exists()) {
            try {
                file.getParentFile().mkdir();

                plugin.saveResource("web.yml", false);
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

    public boolean getEnabled() {
        return config.getBoolean("discord.enabled");
    }

    public String getLink() {
        return config.getString("discord.hook");
    }

    public String getHeader() {
        return config.getString("discord.header");
    }

    public String getBody() {
        return config.getString("discord.body");
    }
}
