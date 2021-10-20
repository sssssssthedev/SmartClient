package net.sssssssthedev.SmartClient.command.modules;

import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.command.Command;
import net.sssssssthedev.SmartClient.module.Module;
import net.sssssssthedev.SmartClient.utils.ColorUtils;

public class Toggle extends Command {
    public Toggle() {
        super("Toggle", "Toggles a Module");
    }

    public void execute(String[] args) {
        if (args.length == 1) {
            Module mod = Main.instance.moduleManager.getModuleByName(args[0]);
            if (!Main.instance.moduleManager.getModuleByName(args[0]).isToggled()) {
                msg(ColorUtils.color + "b" + mod.getName() + ColorUtils.color + "f has been " + ColorUtils.color + "aenabled" + ColorUtils.color + "f!");
                mod.toggle();
            } else {
                msg(ColorUtils.color + "b" + mod.getName() + ColorUtils.color + "f has been " + ColorUtils.color + "cdisabled" + ColorUtils.color + "f!");
                mod.toggle();
            }
        } else {
            ingameguimessage(ColorUtils.color + "fUsage: " + ColorUtils.color + "b#" + getName() + " modname");
        }
    }
}
