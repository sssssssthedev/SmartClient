package net.sssssssthedev.SmartClient.module.player;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.annotations.modules.IModule;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.annotations.modules.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import net.sssssssthedev.SmartClient.utils.ColorUtils;
import net.sssssssthedev.SmartClient.utils.TimeHelper;

import java.util.ArrayList;

@IModule(
        name = "ChestStealer",
        key = 0,
        category = Category.PLAYER
)
public class ChestStealer extends Module {
    public TimeHelper timeUtil = new TimeHelper();

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Vanilla");
        options.add("AAC 3.6.4");
        options.add("AAC 3.6.4 Slow");
        Main.instance.settingsManager.rSetting(new Setting("Chest Stealer Mode", this, "Vanilla", options));
        Main.instance.settingsManager.rSetting(new Setting("Delay", this, 0, 0, 100, true));
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        String mode = Main.instance.settingsManager.getSettingByName("Chest Stealer Mode").getValString();
        this.setDisplayName("ChestStealer " + ColorUtils.color + "7" + mode);
        if (mode.equalsIgnoreCase("Vanilla")) {
            if (this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
                ContainerChest chest = (ContainerChest)this.mc.thePlayer.openContainer;
                for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                    if (chest.getLowerChestInventory().getStackInSlot(i) != null && this.timeUtil.hasTimePassed(Main.instance.settingsManager.getSettingByName("Delay").getValDouble())) {
                        this.mc.playerController.windowClick(chest.windowId, i, 0, 1, this.mc.thePlayer);
                        this.timeUtil.reset();
                    }
                    if (isChestEmpty(chest)) {
                        this.mc.thePlayer.closeScreen();
                        this.mc.updateDisplay();
                        this.mc.displayGuiScreen(null);
                    }
                }
            }
        }
        if (mode.equalsIgnoreCase("AAC 3.6.4")) {
            if (this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
                ContainerChest chest = (ContainerChest)this.mc.thePlayer.openContainer;
                for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                    if (chest.getLowerChestInventory().getStackInSlot(i) != null && this.timeUtil.hasTimePassed(Main.instance.settingsManager.getSettingByName("Delay").getValDouble())) {
                        this.mc.playerController.windowClick(chest.windowId, i, 0, 1, this.mc.thePlayer);
                        this.timeUtil.reset();
                    }
                    if (chest.getInventory().isEmpty()) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.mc.thePlayer.openContainer.windowId));
                        this.mc.thePlayer.closeScreenAndDropStack();
                        this.mc.updateDisplay();
                    }
                }
                if (isChestEmpty(chest)) {
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.mc.thePlayer.openContainer.windowId));
                    this.mc.thePlayer.closeScreenAndDropStack();
                    this.mc.updateDisplay();
                    this.mc.displayGuiScreen(null);
                }
            }
        }
        if (mode.equalsIgnoreCase("AAC 3.6.4 Slow")) {
            if (this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
                ContainerChest chest = (ContainerChest)this.mc.thePlayer.openContainer;
                for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                    if (i == chest.getLowerChestInventory().getSizeInventory() && chest
                            .getLowerChestInventory().getStackInSlot(i) != null && this.timeUtil.hasTimePassed(Main.instance.settingsManager.getSettingByName("Delay").getValDouble())) {
                        this.mc.playerController.windowClick(chest.windowId, i, 0, 1, this.mc.thePlayer);
                        this.timeUtil.reset();
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.mc.thePlayer.openContainer.windowId));
                        this.mc.thePlayer.closeScreenAndDropStack();
                        this.mc.displayGuiScreen(null);
                    } else if (chest.getLowerChestInventory().getStackInSlot(i) != null && this.timeUtil.hasTimePassed(Main.instance.settingsManager.getSettingByName("Delay").getValDouble()) && i != chest
                            .getLowerChestInventory().getSizeInventory()) {
                        this.mc.playerController.windowClick(chest.windowId, i, 0, 1, this.mc.thePlayer);
                        this.timeUtil.reset();
                    } else if (i == chest.getLowerChestInventory().getSizeInventory()) {
                        this.timeUtil.reset();
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.mc.thePlayer.openContainer.windowId));
                        this.mc.thePlayer.closeScreenAndDropStack();
                        this.mc.displayGuiScreen(null);
                    }
                    if (isChestEmpty(chest)) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.mc.thePlayer.openContainer.windowId));
                        this.mc.thePlayer.closeScreenAndDropStack();
                        this.mc.displayGuiScreen(null);
                    }
                }
            }
        }
    }


    public static boolean isChestEmpty(ContainerChest chest) {
        for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
            if (chest.getLowerChestInventory().getStackInSlot(i) != null)
                return false;
        }
        return true;
    }
}
