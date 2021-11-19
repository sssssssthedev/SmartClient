package net.sssssssthedev.SmartClient.ui.gui;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.ui.login.AltLoginThread;
import net.sssssssthedev.SmartClient.ui.login.GuiAltLogin;
import net.sssssssthedev.SmartClient.utils.ColorUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GuiUUIDSpoof extends GuiScreen {
    protected GuiTextField fakeNickField;

    protected GuiTextField realNickField;

    protected GuiScreen prevScreen;

    private AltLoginThread thread;

    private String Report;

    public GuiUUIDSpoof(GuiScreen screen) {
        this.prevScreen = screen;
    }

    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 130 + 20 + 8, 200,
                20, "Spoof"));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 130 + 20 + 30, 200,
                20, "Back"));
        this.buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 130, 200, 20,
                PremiumUUID()));
        this.fakeNickField = new GuiTextField(1, this.fontRendererObj, width / 2 - 100, height / 4 + 60,
                200, 20);
        this.realNickField = new GuiTextField(2, this.fontRendererObj, width / 2 - 100, height / 4 + 20,
                200, 20);
        this.fakeNickField.setText(Main.getFakeNick());
        GameProfile profile = Minecraft.getMinecraft().getSession().getProfile();
        this.realNickField.setText(profile.getName());
    }

    private String PremiumUUID() {
        return Main.PremiumUUID ? "Premium UUID: " + ColorUtils.color + "aYes" : "Premium UUID: " + ColorUtils.color + "cNo";
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            if (!Main.PremiumUUID) {
                Session realSession = mc.getSession();
                Session copiedSession = new Session(realNickField.getText(), realSession.getPlayerID(), realSession.getToken(), Session.Type.LEGACY.name());
                Main.setSession(copiedSession);
                Main.setFakeNick(fakeNickField.getText());
                thread = new AltLoginThread(realNickField.getText(), "");
                thread.start();
                Main.SessionPremium = false;
                mc.displayGuiScreen(prevScreen);
            } else {
                try {
                    Main.PreUUID = fetchUUID(fakeNickField.getText());
                    Session realSession = mc.getSession();
                    Session copiedSession = new Session(realNickField.getText(), realSession.getPlayerID(), realSession.getToken(), Session.Type.LEGACY.name());
                    Main.setSession(copiedSession);
                    Main.setFakeNick(fakeNickField.getText());
                    thread = new AltLoginThread(realNickField.getText(), "");
                    thread.start();
                    Main.SessionPremium = false;
                    this.Report = ColorUtils.color + "fSucessfully spoofed Premium UUID of " + ColorUtils.color + "b" + this.fakeNickField.getText();
                } catch (Exception ex) {
                    this.Report = ColorUtils.color + "fNick " + ColorUtils.color + "b" + this.fakeNickField.getText() + ColorUtils.color + "f isn't premium!";
                }
            }
        } else if (button.id == 2) {
            mc.displayGuiScreen(this.prevScreen);
        }
        if (button.id == 4) {
            Main.PremiumUUID = !Main.PremiumUUID;
            button.displayString = PremiumUUID();
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.fakeNickField.mouseClicked(mouseX, mouseY, mouseButton);
        this.realNickField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            mc.displayGuiScreen(this.prevScreen);
            return;
        }
        if (keyCode == 15)
            if (this.realNickField.isFocused()) {
                this.realNickField.setFocused(false);
                this.fakeNickField.setFocused(true);
            } else if (this.fakeNickField.isFocused()) {
                this.fakeNickField.setFocused(false);
            }
        if (this.fakeNickField.isFocused())
            this.fakeNickField.textboxKeyTyped(typedChar, keyCode);
        if (this.realNickField.isFocused())
            this.realNickField.textboxKeyTyped(typedChar, keyCode);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, this.Report, width / 2, 50, 16777215);
        drawCenteredString(this.fontRendererObj, "Your Nickname", width / 2, this.realNickField.yPosition - 15, 16777215);
        drawCenteredString(this.fontRendererObj, "Fake Nick", width / 2, this.fakeNickField.yPosition - 15, 16777215);
        this.fakeNickField.drawTextBox();
        this.realNickField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public String fetchUUID(String s) throws Exception {
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + s);
        return (String)((JSONObject)(new JSONParser()).parse(new InputStreamReader(url.openStream()))).get("id");
    }
}

