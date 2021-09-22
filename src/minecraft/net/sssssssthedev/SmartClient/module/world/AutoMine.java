package net.sssssssthedev.SmartClient.module.world;

import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import org.lwjgl.input.Keyboard;

public class AutoMine extends Module {

    public AutoMine() {
        super("AutoMine", Keyboard.KEY_0, Category.WORLD);
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        if (mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null && !mc.gameSettings.keyBindAttack.pressed)
            mc.gameSettings.keyBindAttack.pressed = !mc.theWorld.isAirBlock(mc.objectMouseOver.getBlockPos());
    }
}
