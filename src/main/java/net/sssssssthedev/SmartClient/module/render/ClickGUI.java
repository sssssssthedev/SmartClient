package net.sssssssthedev.SmartClient.module.render;

import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

@IModule(
        name = "ClickGUI",
        key = Keyboard.KEY_RSHIFT,
        category = Category.RENDER
)
public class ClickGUI extends Module {

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("New");
        options.add("JellyLike");
        Main.instance.settingsManager.rSetting(new Setting("Design", this, "New", options));
        Main.instance.settingsManager.rSetting(new Setting("Sound", this, false));
        Main.instance.settingsManager.rSetting(new Setting("GuiRed", this, 255, 0, 255, true));
        Main.instance.settingsManager.rSetting(new Setting("GuiGreen", this, 26, 0, 255, true));
        Main.instance.settingsManager.rSetting(new Setting("GuiBlue", this, 42, 0, 255, true));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        mc.displayGuiScreen(Main.instance.clickGUI);
        toggle();
    }
}
