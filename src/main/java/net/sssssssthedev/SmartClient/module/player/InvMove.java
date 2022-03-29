package net.sssssssthedev.SmartClient.module.player;

import net.minecraft.client.settings.KeyBinding;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import org.lwjgl.input.Keyboard;

@IModule(
        name = "InvMove",
        key = 0,
        category = Category.PLAYER
)
public class InvMove extends Module {

    public void setup() {
        Main.instance.settingsManager.rSetting(new Setting("OpenInv", this, true));
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        if (Main.instance.settingsManager.getSettingByName("OpenInv").getValBoolean()) {
            if (this.mc.currentScreen != null && !(this.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat) && this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory) {
                KeyBinding[] moveKeys = { this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindJump };
                byte b;
                int i;
                KeyBinding[] arrayOfKeyBinding1;
                for (i = (arrayOfKeyBinding1 = moveKeys).length, b = 0; b < i; ) {
                    KeyBinding bind = arrayOfKeyBinding1[b];
                    KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
                    b = (byte)(b + 1);
                }
            }
        } else {
            if (this.mc.currentScreen != null && !(this.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)) {
                KeyBinding[] moveKeys = { this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindJump };
                byte b;
                int i;
                KeyBinding[] arrayOfKeyBinding1;
                for (i = (arrayOfKeyBinding1 = moveKeys).length, b = 0; b < i; ) {
                    KeyBinding bind = arrayOfKeyBinding1[b];
                    KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
                    b = (byte)(b + 1);
                }
            }
        }
    }
}
