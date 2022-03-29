package net.sssssssthedev.SmartClient.module.world;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import net.sssssssthedev.SmartClient.utils.BlockUtil;

@IModule(
        name = "BedFucker",
        key = 0,
        category = Category.WORLD
)
public class BedFucker extends Module {

    public void setup() {
        Main.instance.settingsManager.rSetting(new Setting("Range", this, 6, 1, 100, true));
    }

    @SuppressWarnings("rawtypes")
    protected void sendPacket(Packet p) {
        mc.thePlayer.sendQueue.addToSendQueue(p);
    }


    @EventTarget
    public void onUpdate(UpdateEvent e) {
        BlockPos[] bps = BlockUtil.getBlocksAround(mc.thePlayer, (int) Main.instance.settingsManager.getSettingByName("Range").getValDouble(), false);
        for(final BlockPos bp : bps)
            if(mc.theWorld.getBlockState(bp).getBlock().getUnlocalizedName().equals("tile.bed"))
                new Thread(() -> {
                    sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, bp, EnumFacing.UP));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, bp, EnumFacing.UP));
                }).start();
    }
}
