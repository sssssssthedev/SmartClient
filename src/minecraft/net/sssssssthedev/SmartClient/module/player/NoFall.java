package net.sssssssthedev.SmartClient.module.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.sssssssthedev.SmartClient.event.Event;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Keyboard.KEY_B, Category.PLAYER);
    }

    // Packet Based nofall, gonna be changed in the future
    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if(mc.thePlayer.fallDistance > 2F)
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
    }
}
