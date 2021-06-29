package net.sssssssthedev.SmartClient.module;

import net.minecraft.client.Minecraft;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.ui.notification.Notification;
import net.sssssssthedev.SmartClient.ui.notification.NotificationManager;
import net.sssssssthedev.SmartClient.ui.notification.NotificationType;
import net.sssssssthedev.SmartClient.utils.Wrapper;

public class Module {

    protected Minecraft mc = Minecraft.getMinecraft();

    private String name, displayname;
    private int key;
    private Category category;
    private boolean toggled;

    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
        toggled = false;

        setup();
    }

    public void onEnable() {
        Main.instance.eventManager.register(this);
    }

    public void onDisable() {
        Main.instance.eventManager.unregister(this);
    }

    public void onToggle() {
    }

    public void toggle() {
        toggled = !toggled;
        onToggle();
        if(toggled) {
            onEnable();
            if (!getDisplayName().equals("ClickGUI"))
                NotificationManager.show(new Notification(NotificationType.INFO, getDisplayName(), "Turned On", 1));
        } else {
            onDisable();
            if (!getDisplayName().equals("ClickGUI")) {
                NotificationManager.show(new Notification(NotificationType.INFO, getDisplayName(), "Turned Off", 1));
            }
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isToggled() {
        return toggled;
    }

    public String getDisplayName() {
        return displayname == null ? name : displayname;
    }

    public void setDisplayName(String displayName) {
        this.displayname = displayName;
    }

    public void setup() {

    }

    public String getKeyName() {
        return Wrapper.getKeyName(key);
    }
}
