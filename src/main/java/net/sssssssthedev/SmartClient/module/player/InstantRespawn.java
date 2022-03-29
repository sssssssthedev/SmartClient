package net.sssssssthedev.SmartClient.module.player;

import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;

@IModule(
        name = "InstantRespawn",
        key = 0,
        category = Category.PLAYER
)
public class InstantRespawn extends Module {

    @EventTarget
    public void onUpdate() {
        this.mc.thePlayer.respawnPlayer();
    }
}
