package ro.sssssssthedev.AntiCheat.check.impl.badpackets;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInBlockDig;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInFlying;

@CheckData(name = "BadPackets (G)")
public final class BadPacketsG extends PacketCheck {
    private boolean sent;
    private long lastFlying, lastBreak;
    private double buffer;

    public BadPacketsG(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            final long now = System.currentTimeMillis();

            if (sent) {
                final long postDelay = now - lastBreak;

                if (postDelay > 40L && postDelay < 100L) {
                    buffer += 0.25;

                    if (buffer > 0.5) {
                        this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                    }
                } else {
                    buffer = Math.max(buffer - 0.025, 0);
                }

                this.sent = false;
            }

            this.lastFlying = now;
        } else if (packet instanceof WrappedPacketPlayInBlockDig) {
            final long now = System.currentTimeMillis();

            final long flyingDelay = now - lastFlying;

            if (flyingDelay < 10L) {
                this.sent = true;
                this.lastBreak = now;
            } else {
                buffer = Math.max(buffer - 0.025, 0);
            }
        }
    }
}
