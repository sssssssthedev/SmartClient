package ro.sssssssthedev.AntiCheat.check.impl.badpackets;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInEntityAction;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInFlying;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumPlayerAction;

@CheckData(name = "BadPackets (D)")
public final class BadPacketsD extends PacketCheck {
    private int count;

    public BadPacketsD(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInEntityAction) {
            final WrappedPacketPlayInEntityAction wrapper = (WrappedPacketPlayInEntityAction) packet;

            if (wrapper.getUseAction() == EnumPlayerAction.START_SPRINTING) {
                count++;
            } else if (wrapper.getUseAction() == EnumPlayerAction.STOP_SPRINTING) {
                count++;
            }
        } else if (packet instanceof WrappedPacketPlayInFlying) {
            final boolean invalid = count > 1 && !playerData.getConnectionManager().getDelayed().get();

            if (invalid) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }

            this.count = 0;
        }
    }
}
