package net.minecraft.client.gui;

import de.enzaxd.viaforge.ViaForge;
import de.enzaxd.viaforge.protocols.ProtocolCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.sssssssthedev.SmartClient.Main;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiProtocolSelector extends GuiScreen {
    public SlotList list;

    private GuiScreen parent;

    public GuiProtocolSelector(GuiScreen parent) {
        this.parent = parent;
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 27, 200, 20, "Back"));
        this.list = new SlotList(this.mc, this.width, this.height, 32, this.height - 32, 10);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        this.list.actionPerformed(button);
        if (button.id == 1)
            this.mc.displayGuiScreen(this.parent);
    }

    public void handleMouseInput() throws IOException {
        this.list.handleMouseInput();
        super.handleMouseInput();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        drawCenteredString(this.fontRendererObj, EnumChatFormatting.GOLD.toString() + "SmartVia", this.width / 4, 6, 16777215);
        GL11.glPopMatrix();
        drawString(this.fontRendererObj, "by sssssssthedev", 1, 1, -1);
        drawString(this.fontRendererObj, "Discord: sssssss ﾉ SsS#6134", 1, 11, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    class SlotList extends GuiSlot {
        public SlotList(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
            super(mcIn, width, height, topIn, bottomIn, slotHeightIn);
        }

        protected int getSize() {
            return (ProtocolCollection.values()).length;
        }

        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
            ViaForge.getInstance().setVersion(ProtocolCollection.values()[slotIndex].getVersion().getVersion());
        }

        protected boolean isSelected(int slotIndex) {
            return false;
        }

        protected void drawBackground() {
            GuiProtocolSelector.this.drawDefaultBackground();
        }

        protected void drawSlot(int entryID, int insideLeft, int yPos, int insideSlotHeight, int mouseXIn, int mouseYIn) {
            GuiProtocolSelector.this.drawCenteredString(this.mc.fontRendererObj, (
                    (ViaForge.getInstance().getVersion() == ProtocolCollection.values()[entryID].getVersion().getVersion()) ? EnumChatFormatting.GREEN.toString() : EnumChatFormatting.DARK_RED
                            .toString()) + ProtocolCollection.getProtocolById(
                    ProtocolCollection.values()[entryID].getVersion().getVersion()).getName(), this.width / 2, yPos + 2, -1);
        }
    }
}
