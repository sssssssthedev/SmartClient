package net.sssssssthedev.SmartClient.ui.gui;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import net.sssssssthedev.SmartClient.utils.ColorUtils;
import net.sssssssthedev.SmartClient.utils.mcping.MinecraftPing;
import net.sssssssthedev.SmartClient.utils.mcping.MinecraftPingReply;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;


public class GuiPortChecker extends GuiScreen {
    protected GuiScreen prevMenu;

    protected static GuiTextField IpToScan;

    protected static GuiTextField FromPort;

    protected static GuiTextField ToPort;

    protected static String Report;

    public GuiPortChecker(GuiScreen par1GuiScreen) {
        this.prevMenu = par1GuiScreen;
    }

    protected static ArrayList<String> Result = new ArrayList<>();

    protected static boolean Started = false;

    protected static Thread CheckPort;

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(4, width / 2 - 100, 190, 200, 20, "Stop"));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, 215, 200, 20, "Scan"));
        this.buttonList.add(new GuiButton(3, width / 2 - 100, 240, 200, 20, "Back"));
        IpToScan = new GuiTextField(1, this.fontRendererObj, GuiScreen.width / 2 - 100, 140, 200, 20);
        if (IpToScan.getText().isEmpty())
            IpToScan.setText("Put an IP or a DNS");
        FromPort = new GuiTextField(1, this.fontRendererObj, GuiScreen.width / 2 - 50, 165, 40, 15);
        FromPort.setMaxStringLength(5);
        if (FromPort.getText().isEmpty())
            FromPort.setText("1");
        ToPort = new GuiTextField(1, this.fontRendererObj, GuiScreen.width / 2 + 15, 165, 40, 15);
        ToPort.setMaxStringLength(5);
        if (ToPort.getText().isEmpty())
            ToPort.setText("65000");
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == 2) {
                ScanForPort(IpToScan.getText());
                FromPort.setText(FromPort.getText());
                IpToScan.setText(IpToScan.getText());
                ToPort.setText(ToPort.getText());
            }
            if (button.id == 3)
                mc.displayGuiScreen(this.prevMenu);
            if (button.id == 4)
                if (Started) {
                    Thread.currentThread().interrupt();
                    Report = ColorUtils.color + "bThreads succesfully stopped";
                    Started = false;
                } else {
                    Report = ColorUtils.color + "cNo current Threads open";
                }
        }
    }

    protected void keyTyped(char par1, int par2) {
        if (par2 == 15) {
            if (IpToScan.isFocused())
                IpToScan.setFocused(false);
            if (FromPort.isFocused())
                FromPort.setFocused(false);
            if (ToPort.isFocused())
                ToPort.setFocused(false);
        }
        if (IpToScan.isFocused())
            IpToScan.textboxKeyTyped(par1, par2);
        if (FromPort.isFocused())
            FromPort.textboxKeyTyped(par1, par2);
        if (ToPort.isFocused())
            ToPort.textboxKeyTyped(par1, par2);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        IpToScan.mouseClicked(mouseX, mouseY, mouseButton);
        FromPort.mouseClicked(mouseX, mouseY, mouseButton);
        ToPort.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void drawScreen(int par1, int par2, float par3) {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, "Port Finder", width / 2, 20, 16777215);
        drawString(this.fontRendererObj, ColorUtils.color + "bFrom", GuiScreen.width / 2 - 80, 169, -1);
                drawString(this.fontRendererObj, ColorUtils.color + "bTo", GuiScreen.width / 2 - 5, 169, -1);
                        IpToScan.drawTextBox();
        FromPort.drawTextBox();
        ToPort.drawTextBox();
        if (Report != null)
            drawCenteredString(this.fontRendererObj, Report, GuiScreen.width / 2, 270, -1);
        super.drawScreen(par1, par2, par3);
    }

    public static void ScanForPort(final String Ip) {
        Started = true;
        Report = ColorUtils.color + "bScanning...";
        CheckPort = new Thread(() -> {
            for (int Port = Integer.parseInt(GuiPortChecker.FromPort.getText()); Port <= Integer.parseInt(GuiPortChecker.ToPort.getText()); Port++) {
                System.out.println(Ip + ":" + Port);
                try {
                    MinecraftPingReply data = (new MinecraftPing()).getPing(Ip, Port);
                    GuiPortChecker.Result.add("Ip: " + data.getIp() + " Port: " + data.getPort() + " MOTD: " + data.getMotd() + " Version: " + data.getProtocolVersion() + " Online: " + data.getMaxPlayers() + "/" + data.getOnlinePlayers());
                } catch (IOException e) {
                    System.out.println("No open port");
                }
            }
            if (!GuiPortChecker.Result.isEmpty()) {
                File dir = new File("C:\\Users\\" + System.getenv("USERNAME") +
                        "\\AppData\\Roaming\\.minecraft\\Smart\\PortChecker");
                if (!dir.exists())
                    dir.mkdir();
                Path file = Paths.get("C:\\Users\\" + System.getenv("USERNAME") +
                        "\\AppData\\Roaming\\.minecraft\\Smart\\PortChecker\\" + GuiPortChecker.IpToScan.getText() + ".txt");
                try {
                    Files.write(file, GuiPortChecker.Result, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GuiPortChecker.Report = ColorUtils.color + "fAll responsive Minecraft Ports were saved in " + ColorUtils.color + "bC:\\Users\\" + ColorUtils.color + "b" + System.getenv("USERNAME") +
                ColorUtils.color + "b\\AppData\\Roaming\\.minecraft\\Smart\\PortChecker " + ColorUtils.color + "f!";
            } else {
                GuiPortChecker.Report = ColorUtils.color + "fThis DNS " + ColorUtils.color + "b'" + GuiPortChecker.IpToScan.getText() + "' " + ColorUtils.color + "fhas no responsive Minecraft Ports!";
            }
            Thread.currentThread().interrupt();
            GuiPortChecker.Started = false;
        });
        CheckPort.start();
    }
}

