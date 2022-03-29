package net.sssssssthedev.SmartClient.module.movement;

import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;
import org.lwjgl.input.Keyboard;

@IModule(
        name = "Step",
        key = Keyboard.KEY_L,
        category = Category.MOVEMENT
)
public class Step extends Module {

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        mc.thePlayer.stepHeight = 1.5F;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.thePlayer.stepHeight = .5F;
    }
}
