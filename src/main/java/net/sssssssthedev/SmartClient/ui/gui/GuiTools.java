package net.sssssssthedev.SmartClient.ui.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.sssssssthedev.SmartClient.utils.ColorUtils;

import java.io.IOException;

public class GuiTools extends GuiScreen {
    protected GuiMultiplayer prevScreen;

    public GuiTools(GuiMultiplayer Tools) {
        this.prevScreen = Tools;
    }

    public void initGui() {
        this.buttonList.add(new GuiButton(0, GuiMultiplayer.width / 2 - 40, 480, 120, 20, ColorUtils.color + "bGo back"));
        this.buttonList.add(new GuiButton(1, GuiMultiplayer.width / 2 - 140, 130, 120, 20, ColorUtils.color + "bSteal Session"));
        this.buttonList.add(new GuiButton(2, GuiMultiplayer.width / 2 - 140, 160, 120, 20, ColorUtils.color + "bFind Servers"));
        this.buttonList.add(new GuiButton(3, GuiMultiplayer.width / 2 + 10, 160, 120, 20, ColorUtils.color + "bFind SubDomains"));
        this.buttonList.add(new GuiButton(4, GuiMultiplayer.width / 2 + 10, 130, 120, 20, ColorUtils.color + "bFind Ports"));
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(this.prevScreen);
        } else if (button.id == 1) {
            mc.displayGuiScreen(new GuiSessionStealer(this));
        } else if (button.id == 2) {
            mc.displayGuiScreen(new GuiServerFinder(this.prevScreen));
        } else if (button.id == 3) {
            mc.displayGuiScreen(new GuiSubDomain(this));
        } else if (button.id == 4) {
            mc.displayGuiScreen(new GuiPortChecker(this));
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1)
            mc.displayGuiScreen(this.prevScreen);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, width, height, width, height, width, height);
        drawCenteredString(this.fontRendererObj, "Tools", width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
