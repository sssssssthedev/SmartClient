package net.sssssssthedev.SmartClient.module.movement;

import net.minecraft.block.BlockAir;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.PostMotionUpdateEvent;
import net.sssssssthedev.SmartClient.event.impl.PreMotionUpdateEvent;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import net.sssssssthedev.SmartClient.utils.BlockUtil;
import net.sssssssthedev.SmartClient.utils.TimeHelper;

import java.lang.reflect.Field;

@IModule(
        name = "Scaffold",
        key = 0,
        category = Category.MOVEMENT
)
public class Scaffold extends Module {

    private BlockPos currentPos;
    private EnumFacing currentFacing;
    private boolean rotated = false;
    private final TimeHelper timer = new TimeHelper();

    @Override
    public void setup() {
        Main.instance.settingsManager.rSetting(new Setting("Delay", this, 0, 0, 500, true));
        Main.instance.settingsManager.rSetting(new Setting("Eagle", this, false));
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        if (Main.instance.settingsManager.getSettingByName("Eagle").getValBoolean()) {
            setSneaking(rotated);
        }
    }

    @EventTarget
    public void onPost(PostMotionUpdateEvent e) {
        if (currentPos != null) {
            if (timer.hasTimePassed(Main.instance.settingsManager.getSettingByName("Delay").getValDouble())) {
                if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
                    if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem(), currentPos, currentFacing, new Vec3(currentPos.getX(), currentPos.getY(), currentPos.getZ()))) {
                        timer.setLastMS();
                        mc.thePlayer.swingItem();
                    }
                }
            }
        }
    }

    @EventTarget
    public void onPre(PreMotionUpdateEvent e) {
        rotated = false;
        currentPos = null;
        currentFacing = null;
        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
        if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
            setBlockAndFacing(pos);
            if (currentPos != null) {
                float[] facing = BlockUtil.getDirectionToBlock(currentPos.getX(), currentPos.getY(), currentPos.getZ(), currentFacing);

                float yaw = facing[0];
                float pitch = Math.min(90, facing[1] + 9);

                rotated = true;
                e.setYaw(yaw);
                e.setPitch(pitch);
            }
        }
    }

    private void setBlockAndFacing(BlockPos var1) {
        if (mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;
        } else {
            currentPos = null;
            currentFacing = null;
        }
    }

    private void setSneaking(boolean b) {
        KeyBinding sneakBinding = mc.gameSettings.keyBindSneak;
        try {
            Field field = sneakBinding.getClass().getDeclaredField("pressed");
            field.setAccessible(true);
            field.setBoolean(sneakBinding, b);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        setSneaking(false);
        timer.reset();
    }

}
