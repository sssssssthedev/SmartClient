package net.sssssssthedev.SmartClient.ui.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Session;
import net.sssssssthedev.SmartClient.utils.JsonUtils;
import org.lwjgl.input.Keyboard;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class GuiSessionStealer extends GuiScreen {
    protected GuiScreen prevMenu;

    protected GuiTextField tokenBox;

    protected String errorText;

    protected String helpText;

    public GuiSessionStealer(GuiScreen par1GuiScreen) {
        this.prevMenu = par1GuiScreen;
    }

    public void updateScreen() {
        this.tokenBox.updateCursorCounter();
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 90 + 12, "Steal Session"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, "Back"));
        (this.tokenBox = new GuiTextField(1, this.fontRendererObj, width / 2 - 100, 60, 200, 20)).setMaxStringLength(65);
        this.tokenBox.setFocused(true);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled)
            if (button.id == 1) {
                mc.displayGuiScreen(this.prevMenu);
            } else if (button.id == 0) {
                JsonElement rawJson;
                String input = this.tokenBox.getText();
                if (input.length() != 65 || input.charAt(32) != ':' || (input.split(":")).length != 2) {
                    this.errorText = "That is not a session token!";
                    return;
                }
                String uuid = input.split(":")[1];
                if (uuid.contains("-")) {
                    this.errorText = "That is not a session token!";
                    this.helpText = "Try without the dashes (-).";
                    return;
                }
                try {
                    rawJson = JsonUtils.jsonParser.parse(new InputStreamReader((new URL("https://api.mojang.com/user/profiles/" + uuid + "/names")).openConnection().getInputStream()));
                } catch (JsonIOException |com.google.gson.JsonSyntaxException| IOException ex2) {
                    ex2.printStackTrace();
                    this.errorText = "An error occurred";
                    this.helpText = "Mojang servers might be down.";
                    return;
                }
                if (!rawJson.isJsonArray()) {
                    this.errorText = "Invalid UUID";
                    this.helpText = "This session is fake. Try a different one.";
                    return;
                }
                JsonArray json = rawJson.getAsJsonArray();
                String name = json.get(json.size() - 1).getAsJsonObject().get("name").getAsString();
                try {
                    Proxy proxy = (MinecraftServer.getServer() == null) ? null :
                            MinecraftServer.getServer().getServerProxy();
                    if (proxy == null)
                        proxy = Proxy.NO_PROXY;
                    HttpURLConnection connection = (HttpURLConnection)(new URL(
                            "https://authserver.mojang.com/validate")).openConnection(proxy);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    String content = "{\"accessToken\":\"" + input.split(":")[0] + "\"}";
                    connection.setRequestProperty("Content-Length", String.valueOf((content.getBytes()).length));
                    connection.setRequestProperty("Content-Language", "en-US");
                    connection.setUseCaches(false);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                    output.writeBytes(content);
                    output.flush();
                    output.close();
                    if (connection.getResponseCode() != 204)
                        throw new IOException();
                } catch (IOException e2) {
                    this.errorText = "Invalid Session";
                    this.helpText = "This token doesn't work anymore. Try a different one.";
                    return;
                }
                Minecraft.session = new Session(name, uuid, input.split(":")[0], "mojang");
                mc.displayGuiScreen(this.prevMenu);
            }
    }

    protected void keyTyped(char par1, int par2) {
        this.tokenBox.textboxKeyTyped(par1, par2);
        if (par2 == 28 || par2 == 156)
            actionPerformed(this.buttonList.get(0));
    }

    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
        this.tokenBox.mouseClicked(par1, par2, par3);
    }

    public void drawScreen(int par1, int par2, float par3) {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, "Session Stealer", width / 2, 20, 16777215);
        drawString(this.fontRendererObj, "Session ID is token:", width / 2 - 100, 47, 10526880);
        this.tokenBox.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}

