package ro.sssssssthedev.AntiCheat.check.impl.killaura;

import net.minecraft.server.v1_7_R4.PacketPlayInUseEntity;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInFlying;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInUseEntity;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumEntityUseAction;

@CheckData(name = "KillAura (C)")
public final class KillAuraC extends PacketCheck {
    private int ticks, lastTicks, attacks, streak, buffer;

    public KillAuraC(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInUseEntity) {
            final WrappedPacketPlayInUseEntity wrapper = (WrappedPacketPlayInUseEntity) packet;

            if (wrapper.getUseAction() == EnumEntityUseAction.ATTACK) {
                final boolean duplicate = ticks < 8 && ticks == lastTicks;

                if (duplicate) {
                    ++buffer;
                }

                if (++attacks == 25) {
                    final boolean invalid = buffer > 22;
                    final boolean suspicious = buffer > 15;

                    if (invalid) {
                        this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                    }

                    if (suspicious) {
                        if (++streak > 1) {
                            this.handleViolation().addViolation(ViolationLevel.LOW).create();
                        }
                    } else {
                        streak = 0;
                    }

                    attacks = 0;
                    buffer = 0;
                }

                lastTicks = ticks;
                ticks = 0;
            }
        } else if (packet instanceof WrappedPacketPlayInFlying) {
            ++ticks;
        }
    }
}
