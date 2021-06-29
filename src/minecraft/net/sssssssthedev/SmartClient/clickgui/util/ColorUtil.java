package net.sssssssthedev.SmartClient.clickgui.util;

import net.sssssssthedev.SmartClient.Main;

import java.awt.Color;

public class ColorUtil {

	public static Color getClickGUIColor(){
		return new Color((int) Main.instance.settingsManager.getSettingByName("GuiRed").getValDouble(), (int)Main.instance.settingsManager.getSettingByName("GuiGreen").getValDouble(), (int)Main.instance.settingsManager.getSettingByName("GuiBlue").getValDouble());
	}
}
