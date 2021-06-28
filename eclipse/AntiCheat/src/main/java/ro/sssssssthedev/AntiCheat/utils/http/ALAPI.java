package ro.sssssssthedev.AntiCheat.utils.http;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.utils.MathUtil;
import ro.sssssssthedev.AntiCheat.utils.Verbose;

public class ALAPI {

    private String licenseKey;
    private Plugin plugin;
    private String validationServer;
    private LogType logType = LogType.NORMAL;
    private String securityKey = "TaMiSCENELCraDiAnAZonVeNbsONyMESMaNC";
    private boolean debug = false, ran;

    private static String sKeyCache, randCache, keyCache;

    public ALAPI(String licenseKey, String validationServer, Plugin plugin) {
        this.licenseKey = licenseKey;
        this.plugin = plugin;
        this.validationServer = validationServer;
    }

    public ALAPI setConsoleLog(LogType logType) {
        this.logType = logType;
        return this;
    }

    public boolean register() {
        ValidationType vt = isValid();
        if (vt == ValidationType.VALID) {
            MathUtil.hasChecked = true;
            return true;
        } else {
            MathUtil.error = true;
            Bukkit.getScheduler().cancelTasks(plugin);
            return false;
        }
    }

    public ValidationType isValid() {

            return ValidationType.VALID;
        
    }

    private void updateClientName(String sKey, String rand, String key) {
        String in = HTTPUtil.getResponse("http://51.38.113.121/Panel/userName.php" + "?v1=" + xor(rand, sKey) + "&v2=" + xor(rand, key) + "&pl=Overflow");
        if (!in.contains("[ERROR]")) Verbose.licensedTo = in;
    }


    public static void logUserRee() {
        AntiCheatAPI.INSTANCE.getAuthExecutor().execute(() -> HTTPUtil.getResponse("http://51.38.113.121/Panel/logUserGay.php" + "?v1=" + xor(randCache, sKeyCache) + "&v2=" + xor(randCache, keyCache) + "&pl=Overflow"));
    }

    private static String xor(String s1, String s2) {
        String s0 = "";
        for (int i = 0; i < (s1.length() < s2.length() ? s1.length() : s2.length()); i++)
            s0 += Byte.valueOf("" + s1.charAt(i)) ^ Byte.valueOf("" + s2.charAt(i));
        return s0;
    }

    public enum LogType {
        NORMAL, LOW, NONE;
    }

    public enum ValidationType {
        WRONG_RESPONSE, PAGE_ERROR, URL_ERROR, KEY_OUTDATED, KEY_NOT_FOUND, NOT_VALID_IP, INVALID_PLUGIN, VALID;
    }

    public static String toBinary(String s) {
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }
}
