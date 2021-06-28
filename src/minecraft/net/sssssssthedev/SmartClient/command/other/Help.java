package net.sssssssthedev.SmartClient.command.other;

import net.sssssssthedev.SmartClient.command.Command;

public class Help extends Command {

    public Help() {
        super("Help", "Shows the help");
    }

    public void execute(String[] args) {
        msg("\u00a7b#help \u00a78- \u00a7fShows the help");
        msg("\u00a7b#toggle \u00a78- \u00a7fToggles a Module");
        msg("\u00a7b#bind \u00a78- \u00a7fBinds a key to a mod");
        msg("\u00a7b#fawe \u00a78- \u00a7fCrash fawe servers");
    }
}
