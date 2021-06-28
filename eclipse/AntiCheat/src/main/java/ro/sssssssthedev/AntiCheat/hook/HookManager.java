package ro.sssssssthedev.AntiCheat.hook;

import org.bukkit.Bukkit;
import ro.sssssssthedev.AntiCheat.listener.PlayerListener;
import ro.sssssssthedev.AntiCheat.trait.Startable;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;

/**
 * Created on 28/04/2020 Package us.overflow.anticheat.hook
 */
public final class HookManager {
    public HookManager() {
        AntiCheatAPI.INSTANCE.startables.add(AntiCheatAPI.INSTANCE.getProcessorManager());
        AntiCheatAPI.INSTANCE.startables.add(AntiCheatAPI.INSTANCE.getPlayerDataManager());
        AntiCheatAPI.INSTANCE.startables.add(AntiCheatAPI.INSTANCE.getJudgementManager());
        AntiCheatAPI.INSTANCE.startables.add(AntiCheatAPI.INSTANCE.getConfigManager());
        AntiCheatAPI.INSTANCE.startables.add(AntiCheatAPI.INSTANCE.getCommandManager());
        AntiCheatAPI.INSTANCE.startables.forEach(Startable::start);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), AntiCheatAPI.INSTANCE.getPlugin());
    }
}
