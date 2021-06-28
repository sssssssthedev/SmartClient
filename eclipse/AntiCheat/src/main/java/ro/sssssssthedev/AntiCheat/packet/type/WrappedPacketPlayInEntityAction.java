package ro.sssssssthedev.AntiCheat.packet.type;

import lombok.Getter;
import lombok.Setter;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumPlayerAction;

@Getter @Setter
public final class WrappedPacketPlayInEntityAction extends WrappedPacket {
    private EnumPlayerAction useAction;
}
