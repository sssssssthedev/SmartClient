package ro.sssssssthedev.AntiCheat;

import org.bukkit.plugin.java.JavaPlugin;

public final class AntiCheatPlugin extends JavaPlugin {
	public static AntiCheatPlugin instance;
    @Override
    public void onEnable() {
    	instance = this;
        AntiCheatAPI.INSTANCE.start(this);
    }

    @Override
    public void onDisable() {
        AntiCheatAPI.INSTANCE.shutdown();
    }
}
