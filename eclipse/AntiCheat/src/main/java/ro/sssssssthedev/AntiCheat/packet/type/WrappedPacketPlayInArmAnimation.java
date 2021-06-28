package ro.sssssssthedev.AntiCheat.packet.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public final class WrappedPacketPlayInArmAnimation extends WrappedPacket {
    private final long timestamp;
    private final String packetName = "PacketPlayInArmAnimation";
}
