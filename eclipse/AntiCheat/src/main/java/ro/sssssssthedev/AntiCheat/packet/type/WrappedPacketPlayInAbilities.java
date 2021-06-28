package ro.sssssssthedev.AntiCheat.packet.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public final class WrappedPacketPlayInAbilities extends WrappedPacket {
    private final boolean canFly;
    private final boolean flying;
}
