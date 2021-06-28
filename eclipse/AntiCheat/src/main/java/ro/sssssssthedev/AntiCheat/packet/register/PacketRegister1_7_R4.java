package ro.sssssssthedev.AntiCheat.packet.register;

import lombok.Getter;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_7_R4.PacketPlayOutTransaction;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.handlers.PacketHandler1_7_R4;

public final class PacketRegister1_7_R4 {

    public PacketRegister1_7_R4(final PlayerData playerData) {
        final EntityPlayer entityPlayer = ((CraftPlayer) playerData.getPlayer()).getHandle();

        AntiCheatAPI.INSTANCE.getPacketExecutor().execute(() -> new PacketHandler1_7_R4(entityPlayer.server, entityPlayer.playerConnection.networkManager, entityPlayer, playerData));
    }
}
