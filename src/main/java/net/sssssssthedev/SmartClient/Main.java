package net.sssssssthedev.SmartClient;


// Copyright sssssss.dev (C) 2020-2021
// You are not allowed to redistribute this source
// without proper crediting or sell it for money

import de.enzaxd.viaforge.ViaForge;
import net.minecraft.util.Session;
import net.sssssssthedev.SmartClient.clickgui.ClickGUI;
import net.sssssssthedev.SmartClient.command.CommandManager;
import net.sssssssthedev.SmartClient.event.EventManager;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.KeyEvent;
import net.sssssssthedev.SmartClient.module.Module;
import net.sssssssthedev.SmartClient.module.ModuleManager;
import net.sssssssthedev.SmartClient.settings.SettingsManager;
import net.sssssssthedev.SmartClient.utils.CommitHelper;
import net.sssssssthedev.SmartClient.utils.HWID;
import net.sssssssthedev.SmartClient.utils.ValueManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * Main class
 * @author sssssssthedev
 */
public class Main {

    public static Main instance = new Main();

    public static String build = "1.2.1";
    public static String commit;

    static {
        try {
            commit = CommitHelper.instance.getCommitID();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String version = "Production";
    public static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    public static Date date = new Date(System.currentTimeMillis());
    public static boolean BungeeHack;
    public static boolean PremiumUUID;
    public static boolean SessionPremium;
    public static boolean AutoReconnect = false;
    public static String IpBungeeHack;
    public static String fakenick;
    public static Session session;
    public static String PreUUID;
    public static String IpToScan;
    public SettingsManager settingsManager;
    public ModuleManager moduleManager;
    public ClickGUI clickGUI;
    public EventManager eventManager;
    public CommandManager commandManager;
    public ValueManager valueManager;

    /***
     * loadClient -> verify hwid -> loads client in development / production
     * @throws UnsupportedEncodingException some unsupported exception
     * @throws NoSuchAlgorithmException some no algorithm exception
     */
    public void loadClient() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String s = HWID.getHWID();
        System.out.println("[" + formatter.format(date) + "] " + "[Smart thread/INFO]:" +" ! HWID VERIFICATION");
        System.out.println("[" + formatter.format(date) + "] " + "[Smart thread/INFO]:" +" ! Your hwid: " + s);
        SessionPremium = false;
        PremiumUUID = false;
        BungeeHack = false;
        IpBungeeHack = "127.0.0.1";
        fakenick = "";
        PreUUID = "";
        IpToScan = "Put a DNS like example.com";
        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        clickGUI = new ClickGUI();
        eventManager = new EventManager();
        commandManager = new CommandManager();
        valueManager = new ValueManager();
        ViaForge.getInstance().start();
        eventManager.register(this);

        if (s.equalsIgnoreCase("379-303-3fe-3d6-3cf-389-3e6-330-3d9-3cf-3a3-31d-30d-3b8-39a-3b1")) {
            System.out.println("[" + formatter.format(date) + "] " + "[Smart thread/INFO]:" +" ! VERIFICATION DONE");
            System.out.println("[" + formatter.format(date) + "] " + "[Smart thread/INFO]:" +" ! Version: Development");
            version = "Development";
        }
        
    }


    @EventTarget
    public void onKey(KeyEvent event) {
        moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(Module::toggle);
    }
    public static String getFakeNick() {
        return fakenick;
    }

    public static void setFakeNick(String name) {
        fakenick = name;
    }

    public static void setSession(Session copiedSession) {
        session = copiedSession;
    }

    public static boolean isAutoReconnect() {
        return AutoReconnect;
    }

    public static void setAutoReconnect(Boolean autoReconnect) {
        AutoReconnect = autoReconnect;
    }
}
