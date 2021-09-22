package net.sssssssthedev.SmartClient.module.player;

import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import org.lwjgl.input.Keyboard;

public class InstantRespawn extends Module {

    public InstantRespawn() {
        super("InstantRespawn", Keyboard.KEY_0, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate() {
        this.mc.thePlayer.respawnPlayer();
    }
}
