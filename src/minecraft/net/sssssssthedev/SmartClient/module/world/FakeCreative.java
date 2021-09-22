package net.sssssssthedev.SmartClient.module.world;

import net.minecraft.world.WorldSettings;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import org.lwjgl.input.Keyboard;

public class FakeCreative extends Module {
    public FakeCreative() {
        super("FakeCreative", Keyboard.KEY_0, Category.WORLD);
    }

    @EventTarget
    public void onUpdate(UpdateEvent e){
        this.mc.thePlayer.setGameType(WorldSettings.GameType.CREATIVE);
    }

    public void onDisable() {
        this.mc.thePlayer.setGameType(WorldSettings.GameType.SURVIVAL);
    }
}
