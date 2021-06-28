package ro.sssssssthedev.AntiCheat.command;

import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.AntiCheatPlugin;
import ro.sssssssthedev.AntiCheat.command.impl.OverflowCommand;
import ro.sssssssthedev.AntiCheat.trait.Startable;
import ro.sssssssthedev.AntiCheat.utils.LogUtil;

public final class CommandManager implements Startable {

    @Override
    public void start() {
        final AntiCheatPlugin plugin = AntiCheatAPI.INSTANCE.getPlugin();

        LogUtil.log("Attempting to register the commands...");

        new OverflowCommand(plugin);
    }
}
