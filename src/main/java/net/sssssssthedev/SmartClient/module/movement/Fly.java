package net.sssssssthedev.SmartClient.module.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import net.sssssssthedev.SmartClient.utils.ColorUtils;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;
import java.util.ArrayList;

@IModule(
        name = "Fly",
        key = Keyboard.KEY_F,
        category = Category.MOVEMENT
)
public class Fly extends Module {

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Hypixel");
        options.add("Cubecraft");
        options.add("Vanilla");
        Main.instance.settingsManager.rSetting(new Setting("Fly Mode", this, "Hypixel", options));
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        String mode = Main.instance.settingsManager.getSettingByName("Fly Mode").getValString();
        this.setDisplayName("Fly " + ColorUtils.color + "7" + mode);
        if(mode.equalsIgnoreCase("Hypixel")) {
            double y;
            double y1;
            mc.thePlayer.motionY = 0;
            if(mc.thePlayer.ticksExisted % 3 == 0) {
                y = mc.thePlayer.posY - 1.0E-10D;
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true));
            }
            y1 = mc.thePlayer.posY + 1.0E-10D;
            mc.thePlayer.setPosition(mc.thePlayer.posX, y1, mc.thePlayer.posZ);
        }

        if(mode.equalsIgnoreCase("Vanilla"))
            mc.thePlayer.capabilities.isFlying = true;
        if(mode.equalsIgnoreCase("Cubecraft")) {
            mc.thePlayer.capabilities.isFlying = true;
            setTimerSpeed(0.29F);
        }
    }
    public static void setTimerSpeed(float timerSpeed) {
        try {
            Class<?> mcClass = Minecraft.class;
            Field timerField = mcClass.getDeclaredField("timer");
            timerField.setAccessible(true);
            try {
                Object timer = timerField.get(Minecraft.getMinecraft());
                Class<?> timerClass = timer.getClass();
                Field timerSpeedField = timerClass.getDeclaredField("timerSpeed");
                timerSpeedField.setAccessible(true);
                timerSpeedField.setFloat(timer, timerSpeed);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private boolean isBlockValid(Block block) { return block instanceof BlockSnow; }

    @Override
    public void onDisable() {
        super.onDisable();
        String mode = Main.instance.settingsManager.getSettingByName("Fly Mode").getValString();
        if(mode.equalsIgnoreCase("Vanilla"))
            mc.thePlayer.capabilities.isFlying = false;
        if(mode.equalsIgnoreCase("Cubecraft"))
            mc.thePlayer.capabilities.isFlying = false;
            setTimerSpeed(1.0F);
    }


}
