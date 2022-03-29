package net.sssssssthedev.SmartClient.module.movement;

import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;
import org.lwjgl.input.Keyboard;

@IModule(
        name = "Sprint",
        key = Keyboard.KEY_M,
        category = Category.MOVEMENT
)
public class Sprint extends Module {

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0)
            mc.thePlayer.setSprinting(true);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.thePlayer.setSprinting(false);
    }
}
