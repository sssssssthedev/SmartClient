package ro.sssssssthedev.AntiCheat.packet.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public final class WrappedPacketPlayOutPosition extends WrappedPacket {
    private final double x;
    private final double y;
    private final double z;
}
