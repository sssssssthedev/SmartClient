package net.sssssssthedev.SmartClient.module.world;

import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;

@IModule(
        name = "AutoMine",
        key = 0,
        category = Category.WORLD
)
public class AutoMine extends Module {

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        if (mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null && !mc.gameSettings.keyBindAttack.pressed)
            mc.gameSettings.keyBindAttack.pressed = !mc.theWorld.isAirBlock(mc.objectMouseOver.getBlockPos());
    }
}
