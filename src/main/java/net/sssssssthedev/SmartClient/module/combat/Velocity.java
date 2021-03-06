package net.sssssssthedev.SmartClient.module.combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.SendPacketEvent;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import net.sssssssthedev.SmartClient.utils.ColorUtils;

import java.util.ArrayList;

@IModule(
        name = "Velocity",
        key = 0,
        category = Category.COMBAT
)
public class Velocity extends Module {

    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Null");
        Main.instance.settingsManager.rSetting(new Setting("Velocity Mode", this, "Null", options));
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        String mode = Main.instance.settingsManager.getSettingByName("Velocity Mode").getValString();;
        this.setDisplayName("Velocity " + ColorUtils.color + "7" + mode);
    }

    @EventTarget
    public void onPacket(SendPacketEvent e) {
        String mode = Main.instance.settingsManager.getSettingByName("Velocity Mode").getValString();
        if (mode.equalsIgnoreCase("Null") && e.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity velocity = (S12PacketEntityVelocity)e.getPacket();
            if (velocity.getEntityID() != this.mc.thePlayer.getEntityId())
                return;
        }
    }
}
