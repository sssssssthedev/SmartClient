package net.sssssssthedev.SmartClient.module.player;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import net.sssssssthedev.SmartClient.utils.ColorUtils;
import net.sssssssthedev.SmartClient.utils.TimeHelper;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ChestStealer extends Module {
    public TimeHelper timeUtil = new TimeHelper();

    public ChestStealer() {
        super("ChestStealer", Keyboard.KEY_0, Category.PLAYER);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Vanilla");
        options.add("NCP");
        options.add("AAC 3.6.4");
        options.add("AAC 3.6.4 Slow");
        Main.instance.settingsManager.rSetting(new Setting("Chest Stealer Mode", this, "Vanilla", options));
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        String mode = Main.instance.settingsManager.getSettingByName("Chest Stealer Mode").getValString();
        this.setDisplayName("ChestStealer " + ColorUtils.color + "7" + mode);
        if (mode.equalsIgnoreCase("Vanilla")) {
            Vanilla();
        }
        if (mode.equalsIgnoreCase("NCP")) {
            NCP();
        }
        if (mode.equalsIgnoreCase("AAC 3.6.4")) {
            AAC364();
        }
        if (mode.equalsIgnoreCase("AAC 3.6.4 Slow")) {
            AAC364Slow();
        }
    }

    public void Vanilla() {
        if (this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest chest = (ContainerChest)this.mc.thePlayer.openContainer;
            for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                if (chest.getLowerChestInventory().getStackInSlot(i) != null && this.timeUtil.hasTimePassed(0L)) {
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

    public void NCP() {
        if (this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest chest = (ContainerChest)this.mc.thePlayer.openContainer;
            for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                if (chest.getLowerChestInventory().getStackInSlot(i) != null && this.timeUtil.hasTimePassed(40L)) {
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

    public void AAC364() {
        if (this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest chest = (ContainerChest)this.mc.thePlayer.openContainer;
            for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                if (chest.getLowerChestInventory().getStackInSlot(i) != null && this.timeUtil.hasTimePassed(70L)) {
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

    public void AAC364Slow() {
        if (this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest chest = (ContainerChest)this.mc.thePlayer.openContainer;
            for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                if (i == chest.getLowerChestInventory().getSizeInventory() && chest
                        .getLowerChestInventory().getStackInSlot(i) != null && this.timeUtil.hasTimePassed(100L)) {
                    this.mc.playerController.windowClick(chest.windowId, i, 0, 1, this.mc.thePlayer);
                    this.timeUtil.reset();
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.mc.thePlayer.openContainer.windowId));
                    this.mc.thePlayer.closeScreenAndDropStack();
                    this.mc.displayGuiScreen(null);
                } else if (chest.getLowerChestInventory().getStackInSlot(i) != null && this.timeUtil.hasTimePassed(100L) && i != chest
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

    public static boolean isChestEmpty(ContainerChest chest) {
        for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
            if (chest.getLowerChestInventory().getStackInSlot(i) != null)
                return false;
        }
        return true;
    }
}
