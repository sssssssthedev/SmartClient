package ro.sssssssthedev.AntiCheat.packet;

import org.bukkit.Bukkit;
import ro.sssssssthedev.AntiCheat.data.PlayerData;

public final class VersionHandler {
    private String version = Bukkit.getServer().getClass().getName().split("org.bukkit.craftbukkit.")[1];
    private Class registerClass = null;

    public VersionHandler() {
        if (version.endsWith(".CraftServer")) {
            version = version.replace(".CraftServer", "");
        }
        version = version.substring(1);
    }

    public void create(final PlayerData playerData) {
        try {
            final Object object = Class.forName("ro.sssssssthedev.AntiCheat.packet.register.PacketRegister" + version).getConstructor(PlayerData.class).newInstance(playerData);

            registerClass = object.getClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
