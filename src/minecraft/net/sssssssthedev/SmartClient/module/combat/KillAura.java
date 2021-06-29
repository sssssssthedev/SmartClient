package net.sssssssthedev.SmartClient.module.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.PostMotionUpdateEvent;
import net.sssssssthedev.SmartClient.event.impl.PreMotionUpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import org.lwjgl.input.Keyboard;

public class KillAura extends Module {
    private EntityLivingBase target;
    private long current, last;
    private float yaw, pitch;

    public KillAura() {
        super("KillAura", Keyboard.KEY_R, Category.COMBAT);
    }

    @Override
    public void setup() {
        Main.instance.settingsManager.rSetting(new Setting("Crack Size", this, 5, 0, 15, true));
        Main.instance.settingsManager.rSetting(new Setting("Existed", this, 30, 0, 500, true));
        Main.instance.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, true));
        Main.instance.settingsManager.rSetting(new Setting("AutoBlock", this, true));
        Main.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Players", this, true));
        Main.instance.settingsManager.rSetting(new Setting("Animals", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Monsters", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Villagers", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Teams", this, false));
    }

    @EventTarget
    public void onPre(PreMotionUpdateEvent event) {
        target = getClosest(mc.playerController.getBlockReachDistance());
        if(target == null)
            return;
        updateTime();
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
        boolean block = target != null && Main.instance.settingsManager.getSettingByName("AutoBlock").getValBoolean() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
        if(block && target.getDistanceToEntity(mc.thePlayer) < 8F)
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
        int delay = 8;
        if(current - last > 1000 / delay) {
            attack(target);
            resetTime();
        }
    }

    @EventTarget
    public void onPost(PostMotionUpdateEvent event) {
        if(target == null)
            return;
        mc.thePlayer.rotationYaw = yaw;
        mc.thePlayer.rotationPitch = pitch;
    }

    private void attack(Entity entity) {
        for(int i = 0; i < Main.instance.settingsManager.getSettingByName("Crack Size").getValDouble(); i++)
            mc.thePlayer.onCriticalHit(entity);

        mc.thePlayer.swingItem();
        mc.playerController.attackEntity(mc.thePlayer, entity);
    }

    private void updateTime() {
        current = (System.nanoTime() / 1000000L);
    }

    private void resetTime() {
        last = (System.nanoTime() / 1000000L);
    }

    private EntityLivingBase getClosest(double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (Entity object : mc.theWorld.loadedEntityList) {
            if (object instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) object;
                if (canAttack(player)) {
                    double currentDist = mc.thePlayer.getDistanceToEntity(player);
                    if (currentDist <= dist) {
                        dist = currentDist;
                        target = player;
                    }
                }
            }
        }
        return target;
    }

    private boolean canAttack(EntityLivingBase player) {
        if(player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
            if (player instanceof EntityPlayer && !Main.instance.settingsManager.getSettingByName("Players").getValBoolean())
                return false;
            if (player instanceof EntityAnimal && !Main.instance.settingsManager.getSettingByName("Animals").getValBoolean())
                return false;
            if (player instanceof EntityMob && !Main.instance.settingsManager.getSettingByName("Monsters").getValBoolean())
                return false;
            if (player instanceof EntityVillager && !Main.instance.settingsManager.getSettingByName("Villagers").getValBoolean())
                return false;
        }
        if(player.isOnSameTeam(mc.thePlayer) && Main.instance.settingsManager.getSettingByName("Teams").getValBoolean())
            return false;
        if(player.isInvisible() && !Main.instance.settingsManager.getSettingByName("Invisibles").getValBoolean())
            return false;
        if(!isInFOV(player, Main.instance.settingsManager.getSettingByName("FOV").getValDouble()))
            return false;
        return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= mc.playerController.getBlockReachDistance() && player.ticksExisted > Main.instance.settingsManager.getSettingByName("Existed").getValDouble();
    }

    private boolean isInFOV(EntityLivingBase entity, double angle) {
        angle *= .5D;
        double angleDiff = getAngleDifference(mc.thePlayer.rotationYaw, getRotations(entity.posX, entity.posY, entity.posZ)[0]);
        return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
    }

    private float getAngleDifference(float dir, float yaw) {
        float f = Math.abs(yaw - dir) % 360F;
        return f > 180F ? 360F - f : f;
    }

    private float[] getRotations(double x, double y, double z) {
        double diffX = x + .5D - mc.thePlayer.posX;
        double diffY = (y + .5D) / 2D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double diffZ = z + .5D - mc.thePlayer.posZ;

        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
        float pitch = (float)-(Math.atan2(diffY, dist) * 180D / Math.PI);

        return new float[] { yaw, pitch };
    }
}
