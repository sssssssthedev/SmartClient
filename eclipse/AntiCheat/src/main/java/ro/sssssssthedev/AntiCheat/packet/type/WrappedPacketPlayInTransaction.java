package ro.sssssssthedev.AntiCheat.packet.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public final class WrappedPacketPlayInTransaction extends WrappedPacket {
    private final short id;
    private final int data;
}
