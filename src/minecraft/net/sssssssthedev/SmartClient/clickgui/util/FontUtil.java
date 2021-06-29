package net.sssssssthedev.SmartClient.clickgui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StringUtils;

public class FontUtil {

	public static FontRenderer fontRenderer;

	public static void setupFontUtils() {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;
	}

	public static int getStringWidth(String text) {
		return Minecraft.getMinecraft().fontRendererObj.getStringWidth(StringUtils.stripControlCodes(text));
	}

	public static int getFontHeight() {
		return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}

	public static void drawString(String text, double x, double y, int color) {
		Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color);
	}

	public static void drawStringWithShadow(String text, double x, double y, int color) {
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, (float) x, (float) y, color);
	}

	public static void drawCenteredString(String text, double x, double y, int color) {
		drawString(text, x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2, y, color);
	}

	public static void drawCenteredStringWithShadow(String text, double x, double y, int color) {
		drawStringWithShadow(text, x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2, y, color);
	}

	public static void drawTotalCenteredString(String text, double x, double y, int color) {
		drawString(text, x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2, y - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2, color);
	}

	public static void drawTotalCenteredStringWithShadow(String text, double x, double y, int color) {
		drawStringWithShadow(text, x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2, y - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2F, color);
	}

}
