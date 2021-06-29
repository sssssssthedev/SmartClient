package net.sssssssthedev.SmartClient.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.sssssssthedev.SmartClient.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private String name;

    private String description;

    public Minecraft mc;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void execute(String[] args) {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<String> autocomplete(int arg, String[] args) {
        return null;
    }

    List<String> getNames() {
        List<String> l = new ArrayList<>();
        l.add(this.name);
        return l;
    }

    boolean match(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    public static void ingameguimessage(String msg) {
        IChatComponent chat = new ChatComponentText(msg);
        if (msg != null) {
            (Minecraft.getMinecraft()).ingameGUI.getChatGUI().printChatMessage(chat);
        }
    }

    public static void clearchat() {
        (Minecraft.getMinecraft()).ingameGUI.getChatGUI().clearChatMessages();
    }

    public static void msg(String msg) {
        ingameguimessage(ColorUtils.color + "bSmart Client " + ColorUtils.color + "8\u25AA" + ColorUtils.color + "f " + msg);
    }
}
