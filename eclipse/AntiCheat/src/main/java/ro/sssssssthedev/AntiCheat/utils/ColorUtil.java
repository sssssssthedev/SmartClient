package ro.sssssssthedev.AntiCheat.utils;

import org.bukkit.ChatColor;

public final class ColorUtil {

    public ColorUtil() throws Exception {
        throw new Exception("You cannot register utility classes");
    }

    public static String format(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
