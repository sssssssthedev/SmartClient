package ro.sssssssthedev.AntiCheat.packet.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public final class WrappedPacketPlayInFlying extends WrappedPacket {
    private final double x, y, z;
    private final boolean hasPos, hasLook, onGround, checkMovement;
    private final float yaw, pitch;
    private final String packetName = "PacketPlayInFlying";
}
