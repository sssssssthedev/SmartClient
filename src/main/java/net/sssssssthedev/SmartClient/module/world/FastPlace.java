package net.sssssssthedev.SmartClient.module.world;

import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;

@IModule(
        name = "FastPlace",
        key = 0,
        category = Category.WORLD
)
public class FastPlace extends Module {

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        mc.rightClickDelayTimer = 0;
    }
}
