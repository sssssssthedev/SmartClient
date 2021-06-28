package ro.sssssssthedev.AntiCheat.check.impl.badpackets;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInBlockDig;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumPlayerDigType;

@CheckData(name = "BadPackets (C)")
public final class BadPacketsC extends PacketCheck {
    private long lastReleaseUseItem;

    public BadPacketsC(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInBlockDig) {
            final WrappedPacketPlayInBlockDig wrapper = (WrappedPacketPlayInBlockDig) packet;

            final EnumPlayerDigType digType = wrapper.getDigType();

            if (digType == EnumPlayerDigType.RELEASE_USE_ITEM) {
                final long now = System.currentTimeMillis();

                if (playerData.getConnectionManager().getDelayed().get()) {
                    return;
                }

                if (now - lastReleaseUseItem < 40L) {
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }

                this.lastReleaseUseItem = now;
            }
        }
    }
}
