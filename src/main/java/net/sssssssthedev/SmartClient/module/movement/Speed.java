package net.sssssssthedev.SmartClient.module.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.MoveEvent;
import net.sssssssthedev.SmartClient.event.impl.PreMotionUpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import net.sssssssthedev.SmartClient.utils.ColorUtils;
import net.sssssssthedev.SmartClient.utils.TimeHelper;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Speed extends Module {
    public Speed() {
        super("Speed", Keyboard.KEY_U, Category.MOVEMENT);
    }

    public TimeHelper timeUtil = new TimeHelper();

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Y-Port");
        options.add("Intave");
        options.add("Cubecraft TP");
        options.add("Cubecraft Bhop");
        Main.instance.settingsManager.rSetting(new Setting("Speed Mode", this, "Y-Port", options));
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        String mode = Main.instance.settingsManager.getSettingByName("Speed Mode").getValString();
        if (mode.equalsIgnoreCase("Cubecraft TP") && this.mc.thePlayer.isMoving() && this.timeUtil.hasTimePassed(1L)) {
            double yaw = this.mc.thePlayer.getDirection();
            event.setX(-Math.sin(yaw) * 2.0D);
            event.setZ(Math.cos(yaw) * 2.0D);
            this.timeUtil.reset();
        }
    }
    @EventTarget
    public void onPre(PreMotionUpdateEvent event) {
        String mode = Main.instance.settingsManager.getSettingByName("Speed Mode").getValString();
        this.setDisplayName("Speed " + ColorUtils.color + "7" + mode);
        if (mode.equalsIgnoreCase("Y-Port")) {
            if (isOnLiquid())
                return;
            if (mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
                if (mc.thePlayer.ticksExisted % 2 != 0)
                    event.y += .4;
                mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? .45F : .2F);
                mc.timer.timerSpeed = 1.095F;
            }
        }
        if (mode.equalsIgnoreCase("Intave")) {
            if (this.mc.thePlayer.onGround) {
                if (this.mc.thePlayer.isMoving())
                    this.mc.thePlayer.jump();
            } else {
                this.mc.thePlayer.isMoving();
            }
            if (this.mc.thePlayer.moveForward > 0.0F)
                this.mc.thePlayer.setSprinting(true);
        }
        if (mode.equalsIgnoreCase("Cubecraft TP")) {
            this.mc.thePlayer.motionY = 0.0D;
            if (this.mc.gameSettings.keyBindJump.isKeyDown())
                this.mc.thePlayer.motionY = 1.0D;
            if (this.mc.gameSettings.keyBindSneak.isKeyDown())
                this.mc.thePlayer.motionY = -1.0D;
            if (this.mc.thePlayer.isMoving())
                this.mc.thePlayer.setSprinting(true);
        }
        if (mode.equalsIgnoreCase("Cubecraft Bhop")) {
            if (this.mc.thePlayer.onGround) {
                if (this.mc.thePlayer.isMoving()) {
                    this.mc.thePlayer.setSpeed(0.3F);
                    this.mc.thePlayer.jump();
                }
            } else if (this.mc.thePlayer.isMoving()) {
                this.mc.thePlayer.setSpeed(this.mc.thePlayer.getSpeed());
                EntityPlayerSP thePlayer = this.mc.thePlayer;
                thePlayer.motionY -= 0.01D;
            }
            if (this.mc.thePlayer.moveForward > 0.0F)
                this.mc.thePlayer.setSprinting(true);
        }
    }

    private boolean isOnLiquid() {
        boolean onLiquid = false;
        final int y = (int)(mc.thePlayer.boundingBox.minY - .01);
        for(int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for(int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if(block != null && !(block instanceof BlockAir)) {
                    if(!(block instanceof BlockLiquid))
                        return false;
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
}
