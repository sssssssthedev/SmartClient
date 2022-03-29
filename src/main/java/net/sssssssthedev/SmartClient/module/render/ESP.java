package net.sssssssthedev.SmartClient.module.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.Event3D;
import net.sssssssthedev.SmartClient.event.impl.PostRenderEntityEvent;
import net.sssssssthedev.SmartClient.event.impl.RenderEntityEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import net.sssssssthedev.SmartClient.utils.EntityUtils;
import net.sssssssthedev.SmartClient.utils.RenderUtils;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

@IModule(
        name = "ESP",
        key = 0,
        category = Category.RENDER
)
public class ESP extends Module {

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Box");
        Main.instance.settingsManager.rSetting(new Setting("ESP Mode", this, "Box", options));
        Main.instance.settingsManager.rSetting(new Setting("Width", this, 25, 1, 100, true));
        Main.instance.settingsManager.rSetting(new Setting("Players", this, true));
        Main.instance.settingsManager.rSetting(new Setting("Animals", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Monsters", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Invisible", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Wallhack", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Items", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Tracer", this, false));
    }

    @EventTarget
    public void onEntityNormalRender(RenderEntityEvent e) {
        if (!Main.instance.settingsManager.getSettingByName("Wallhack").getValBoolean() || !isValidEntity(e.getEntity()))
            return;
    }
    @EventTarget
    public void onPreEntityRenderer(RenderEntityEvent e) {
        GL11.glEnable(GL_POLYGON_OFFSET_FILL);
        GL11.glPolygonOffset(1.0F, -2000000F);
    }
    @EventTarget
    public void onPostEntityRenderer(PostRenderEntityEvent e) {
        GL11.glPolygonOffset(1.0F, 2000000F);
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
    }

    @EventTarget
    public void onRender3D(Event3D event) {
        String mode = Main.instance.settingsManager.getSettingByName("ESP Mode").getValString();;
        if (!Minecraft.isGuiEnabled())
            return;
        RenderUtils.beginGl();
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (!isValidEntity(entity))
                continue;

            float partialTicks = event.getPartialTicks();
            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().renderPosX;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().renderPosY;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().renderPosZ;

            if (mode.equalsIgnoreCase("Box")) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, z);
                GlStateManager.rotate(-entity.rotationYaw, 0.0F, entity.height, 0.0F);
                GlStateManager.translate(-x, -y, -z);
                drawBoxes(entity, x, y, z);
                GlStateManager.popMatrix();
            }

            if (Main.instance.settingsManager.getSettingByName("Tracer").getValBoolean()) {
                double x2 = EntityUtils.isReferenceSet() ? EntityUtils.getReference().lastTickPosX + (EntityUtils.getReference().posX - EntityUtils.getReference().lastTickPosX) * partialTicks - mc.getRenderManager().renderPosX : 0;
                double y2 = EntityUtils.isReferenceSet() ? EntityUtils.getReference().lastTickPosY + (EntityUtils.getReference().posY - EntityUtils.getReference().lastTickPosY) * partialTicks - mc.getRenderManager().renderPosY : 0;
                double z2 = EntityUtils.isReferenceSet() ? EntityUtils.getReference().lastTickPosZ + (EntityUtils.getReference().posZ - EntityUtils.getReference().lastTickPosZ) * partialTicks - mc.getRenderManager().renderPosZ : 0;

                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                mc.entityRenderer.orientCamera(partialTicks);
                drawTracerLines(entity, x, y, z, x2, y2, z2);
                GlStateManager.popMatrix();
            }
        }
        RenderUtils.endGl();
    }


    public boolean isValidEntity(Entity entity) {
        if (entity == null)
            return false;
        if (entity == mc.thePlayer || entity == EntityUtils.getReference())
            return false;
        if (!entity.isEntityAlive())
            return false;
        if (entity instanceof EntityLivingBase) {
            if (entity.ticksExisted < 20)
                return false;
            if (entity instanceof EntityPlayer) {
                return Main.instance.settingsManager.getSettingByName("Players").getValBoolean();
            } else if (entity instanceof IAnimals && !(entity instanceof IMob)) {
                return Main.instance.settingsManager.getSettingByName("Animals").getValBoolean();
            } else if (entity instanceof IMob) {
                return Main.instance.settingsManager.getSettingByName("Monsters").getValBoolean();
            }
        } else if (entity instanceof EntityItem) {
            return Main.instance.settingsManager.getSettingByName("Players").getValBoolean();
        }
        return false;
    }

    private void drawBoxes(Entity entity, double x, double y, double z) {
        AxisAlignedBB box = AxisAlignedBB.fromBounds(x - entity.width, y, z - entity.width, x + entity.width, y + entity.height + 0.2D, z + entity.width);
        if (entity instanceof EntityLivingBase) {
            box = AxisAlignedBB.fromBounds(x - entity.width + 0.2D, y, z - entity.width + 0.2D, x + entity.width - 0.2D, y + entity.height + (entity.isSneaking() ? 0.02D : 0.2D), z + entity.width - 0.2D);
        }

        final float distance = EntityUtils.getReference().getDistanceToEntity(entity);
        float[] color = new float[]{0.0F, 0.9F, 0.0F};
        if (entity.isInvisibleToPlayer(mc.thePlayer)) {
            color = new float[]{1.0F, 0.9F, 0.0F};
        } else if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).hurtTime > 0) {
            color = new float[]{1.0F, 0.66F, 0.0F};
        } else if (distance <= 3.9F) {
            color = new float[]{0.9F, 0.0F, 0.0F};
        }

        GlStateManager.color(color[0], color[1], color[2], 0.6F);
        //RenderUtils.drawLines(box);
        GlStateManager.color(color[0], color[1], color[2], 0.6F);
        //RenderGlobal.drawOutlinedBoundingBox(box, -1);
    }

    private void drawTracerLines(Entity entity, double x, double y, double z, double x2, double y2, double z2) {
        final float distance = EntityUtils.getReference().getDistanceToEntity(entity);
        float[] color = new float[]{0.0F, 0.90F, 0.0F};
        if (entity.isInvisibleToPlayer(mc.thePlayer)) {
            color = new float[]{1.0F, 0.9F, 0.0F};
        } else if (distance <= 64.0F) {
            color = new float[]{0.9F, distance / 64.0F, 0.0F};
        }

        GlStateManager.color(color[0], color[1], color[2], 1.0F);
        Tessellator var2 = Tessellator.getInstance();
        WorldRenderer var3 = var2.getWorldRenderer();
    //    var3.startDrawing(2);
        var3.pos(x2, y2 + mc.thePlayer.getEyeHeight(), z2);
        var3.pos(x, y, z);
        var2.draw();
    }

}