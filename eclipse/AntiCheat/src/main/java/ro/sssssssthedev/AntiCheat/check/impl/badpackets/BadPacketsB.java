package ro.sssssssthedev.AntiCheat.check.impl.badpackets;

import net.minecraft.server.v1_7_R4.PacketPlayInSteerVehicle;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Pig;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInSteerVehicle;

import java.util.Arrays;

@CheckData(name = "BadPackets (B)")
public final class BadPacketsB extends PacketCheck {
    private long lastVehicle;

    public BadPacketsB(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInSteerVehicle) {
            final Object[] entityArray = playerData.getPlayer().getNearbyEntities(6.0, 6.0, 6.0).toArray();

            final boolean vehicleNear = Arrays.stream(entityArray).anyMatch(entity -> entity instanceof Pig || entity instanceof Boat || entity instanceof Minecart || entity instanceof Horse);

            if (vehicleNear) {
                lastVehicle = System.currentTimeMillis();
            }

            final boolean invalid = System.currentTimeMillis() - lastVehicle > 1000L;

            if (invalid) {
                this.handleViolation().addViolation(ViolationLevel.HIGH).create();

                playerData.getPlayer().kickPlayer("Timed Out");
            }
        }
    }
}
