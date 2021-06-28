package ro.sssssssthedev.AntiCheat.command.type.manager;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.AntiCheatPlugin;
import ro.sssssssthedev.AntiCheat.trait.Startable;
import ro.sssssssthedev.AntiCheat.utils.LogUtil;
import ro.sssssssthedev.AntiCheat.command.impl.OverflowCommand;
import ro.sssssssthedev.AntiCheat.command.type.AbstractCommand;

import java.util.Collection;

public final class CommandManager implements Startable {

    private final AntiCheatPlugin plugin = AntiCheatAPI.INSTANCE.getPlugin();

    // This is where we will store our commands
    private final ClassToInstanceMap<AbstractCommand> commands = new ImmutableClassToInstanceMap.Builder<AbstractCommand>()
            .put(OverflowCommand.class, new OverflowCommand(plugin))
            .build();

    @Override
    public void start() {
        LogUtil.log("Successfully started the command manager");
    }

    // Get all the registered commands
    public final Collection<AbstractCommand> getCommands() {
        return commands.values();
    }

    // Get a specific command class
    public final <T extends AbstractCommand> T getCommand(final Class<T> clazz) {
        return commands.getInstance(clazz);
    }
}
