package ro.sssssssthedev.AntiCheat.check.impl.killaura;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInFlying;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInUseEntity;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumEntityUseAction;

@CheckData(name = "KillAura (A)", threshold = 7)
public final class KillAuraA extends PacketCheck {
    private boolean sent;
    private long lastFlying, lastAttack;
    private double buffer;

    public KillAuraA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            final long now = System.currentTimeMillis();

            if (sent) {
                final long postDelay = now - lastAttack;

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
        } else if (packet instanceof WrappedPacketPlayInUseEntity) {
            final WrappedPacketPlayInUseEntity wrapper = (WrappedPacketPlayInUseEntity) packet;

            final long now = System.currentTimeMillis();

            if (wrapper.getUseAction() == EnumEntityUseAction.ATTACK) {
                final long flyingDelay = now - lastFlying;

                if (flyingDelay < 10L) {
                    this.sent = true;
                    this.lastAttack = now;
                } else {
                    buffer = Math.max(buffer - 0.025, 0);
                }
            }
        }
    }
}
