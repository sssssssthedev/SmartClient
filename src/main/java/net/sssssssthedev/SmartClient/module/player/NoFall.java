package net.sssssssthedev.SmartClient.module.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;
import org.lwjgl.input.Keyboard;

@IModule(
        name = "NoFall",
        key = Keyboard.KEY_B,
        category = Category.PLAYER
)
public class NoFall extends Module {

    // Packet Based nofall, gonna be changed in the future
    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if(mc.thePlayer.fallDistance > 2F)
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
    }
}
