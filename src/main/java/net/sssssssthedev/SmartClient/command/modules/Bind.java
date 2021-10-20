package net.sssssssthedev.SmartClient.command.modules;

import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.command.Command;
import net.sssssssthedev.SmartClient.module.Module;
import net.sssssssthedev.SmartClient.utils.ColorUtils;
import net.sssssssthedev.SmartClient.utils.Wrapper;

public class Bind extends Command {
    public Bind() {
        super("Bind", "Binds a key to a mod");
    }

    public void execute(String[] args) {
        if (args.length == 2) {
            Module mod = Main.instance.moduleManager.getModuleByName(args[0]);
            String rkey = args[1];

            if (rkey == null) {
                msg("\u00a7b" + mod.getName() + " is bound to \u00a7b" + mod.getKeyName());
                return;
            }

            int key = Wrapper.getKey(rkey);
            if (rkey.equalsIgnoreCase("none")) {
                key = -1;
            }
            if (key == 0) {
                msg("Unknown key: \u00a7b" + rkey);
                return;
            }
            mod.setKey(key);
            msg("Bind for \u00a7b" + mod.getName() + "\u00a7f set to \u00a7b" + rkey.toUpperCase());
        } else {
            ingameguimessage(ColorUtils.color + "fUsage: " + ColorUtils.color + "b#" + getName() + " modname key");
        }
    }
}
