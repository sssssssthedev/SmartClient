package ro.sssssssthedev.AntiCheat.check.impl.invalid;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInEntityAction;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumPlayerAction;

@CheckData(name = "Invalid (B)")
public final class InvalidB extends PacketCheck {
    private long lastStopSprinting;

    public InvalidB(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInEntityAction) {
            final WrappedPacketPlayInEntityAction wrapper = (WrappedPacketPlayInEntityAction) packet;

            final long now = System.currentTimeMillis();

            if (wrapper.getUseAction() == EnumPlayerAction.START_SPRINTING) {
                final long deltaAction = now - lastStopSprinting;
                final boolean touchingAir = playerData.getPositionManager().getTouchingAir().get();

                // Cannot sprint and un sprint in the same tick.
                if (deltaAction < 40L && !touchingAir) {
                    this.handleViolation().addViolation(ViolationLevel.LOW).create();
                }
            }

            if (wrapper.getUseAction() == EnumPlayerAction.STOP_SPRINTING) {
                lastStopSprinting = now;
            }
        }
    }
}
