package net.sssssssthedev.SmartClient.utils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    private static final Value<Boolean> TRACER_ENTITY = new Value<>("render_tracer_entity", true);
    private static final ClampedValue<Float> LINE_WIDTH = new ClampedValue<>("render_line_width", 1.0F, 0.5F, 4.0F);
    private static final Value<Boolean> ANTI_ALIASING = new Value<>("render_anti_aliasing", false);
    public static Value<Boolean> getTracerEntity() {
        return TRACER_ENTITY;
    }

    public static void beginGl() {
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        if (ANTI_ALIASING.getValue())
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(LINE_WIDTH.getValue());
    }

    public static void endGl() {
        GL11.glLineWidth(2.0F);
        if (ANTI_ALIASING.getValue())
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }


}
