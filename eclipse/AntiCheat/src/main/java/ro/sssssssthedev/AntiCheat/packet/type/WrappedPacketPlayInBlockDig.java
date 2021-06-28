package ro.sssssssthedev.AntiCheat.packet.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumPlayerDigType;

@AllArgsConstructor @Getter
public final class WrappedPacketPlayInBlockDig extends WrappedPacket {
    private final EnumPlayerDigType digType;
    private final String packetName = "PacketPlayInBlockDig";
}
