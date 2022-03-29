package net.sssssssthedev.SmartClient.module.world;

import net.minecraft.world.WorldSettings;
import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;

@IModule(
        name = "FakeCreative",
        key = 0,
        category = Category.WORLD
)
public class FakeCreative extends Module {

    @EventTarget
    public void onUpdate(UpdateEvent e){
        this.mc.thePlayer.setGameType(WorldSettings.GameType.CREATIVE);
    }

    public void onDisable() {
        this.mc.thePlayer.setGameType(WorldSettings.GameType.SURVIVAL);
    }
}
