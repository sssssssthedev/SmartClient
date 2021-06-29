package net.sssssssthedev.SmartClient.command;

import net.sssssssthedev.SmartClient.command.exploits.Fawe;
import net.sssssssthedev.SmartClient.command.exploits.LocalCrash;
import net.sssssssthedev.SmartClient.command.modules.Bind;
import net.sssssssthedev.SmartClient.command.modules.Toggle;
import net.sssssssthedev.SmartClient.command.other.Help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager {
    private final List<Command> commands = new ArrayList<>();

    public static String Chat_Prefix = "#";

    public CommandManager() {
        addCommand(new Help());
        addCommand(new Bind());
        addCommand(new Toggle());
        addCommand(new Fawe());
        addCommand(new LocalCrash());
    }

    public void addCommand(Command cmd) {
        this.commands.add(cmd);
    }

    public boolean execute(String text) {
        if (!text.startsWith(Chat_Prefix))
            return false;
        text = text.substring(1);
        String[] arguments = text.split(" ");
        for (Command cmd : this.commands) {
            if (cmd.getName().equalsIgnoreCase(arguments[0])) {
                String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
                cmd.execute(args);
                return true;
            }
        }
        return false;
    }

    public Collection<String> autoComplete(String currCmd) {
        String raw = currCmd.substring(1);
        String[] split = raw.split(" ");
        List<String> ret = new ArrayList<>();
        Command currentCommand = (split.length >= 1) ? this.commands.stream().filter(cmd -> cmd.match(split[0])).findFirst().orElse(null) : null;
        if (split.length >= 2 || (currentCommand != null && currCmd.endsWith(" "))) {
            if (currentCommand == null)
                return ret;
            String[] args = new String[split.length - 1];
            System.arraycopy(split, 1, args, 0, split.length - 1);
            List<String> autocomplete = currentCommand.autocomplete(args.length + (currCmd.endsWith(" ") ? 1 : 0), args);
            return (autocomplete == null) ? new ArrayList<>() : autocomplete;
        }
        if (split.length == 1) {
            for (Command command : this.commands)
                ret.addAll(command.getNames());
            return ret.stream().map(str -> "#" + str).filter(str -> str.toLowerCase().startsWith(currCmd.toLowerCase())).collect(Collectors.toList());
        }
        return ret;
    }

    public List<Command> getCommands() {
        return this.commands;
    }
}
