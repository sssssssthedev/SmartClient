package net.sssssssthedev.SmartClient.module.combat;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.SendPacketEvent;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import net.sssssssthedev.SmartClient.utils.PlayerUtils;

import java.util.ArrayList;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", 0, Category.COMBAT);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Packet");
        options.add("MiniJump");
        Main.instance.settingsManager.rSetting(new Setting("Criticals Mode", this, "Packet", options));
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        String mode = Main.instance.settingsManager.getSettingByName("Criticals Mode").getValString();
        this.setDisplayName("Criticals \u00A77" + mode);
    }

    @EventTarget
    public void onSendPacket(SendPacketEvent event) {
        String mode = Main.instance.settingsManager.getSettingByName("Criticals Mode").getValString();

        if(canCrit()) {
            if (event.getPacket() instanceof C02PacketUseEntity) {
                C02PacketUseEntity packet = (C02PacketUseEntity)event.getPacket();
                if(packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                    if(mode.equalsIgnoreCase("Packet")) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + .1625, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 4.0E-6, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-6, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                    }
                }
            }
            if(mode.equalsIgnoreCase("MiniJump")) {
                mc.thePlayer.jump();
                mc.thePlayer.motionY -= .30000001192092879;
            }
        }
    }

    private boolean canCrit() {
        return !PlayerUtils.isInLiquid() && mc.thePlayer.onGround;
    }
}
