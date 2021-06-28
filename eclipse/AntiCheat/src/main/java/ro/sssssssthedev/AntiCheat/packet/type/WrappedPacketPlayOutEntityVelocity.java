package ro.sssssssthedev.AntiCheat.packet.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public final class WrappedPacketPlayOutEntityVelocity extends WrappedPacket {
    private final int entityId;
    private final double x, y, z;
}
