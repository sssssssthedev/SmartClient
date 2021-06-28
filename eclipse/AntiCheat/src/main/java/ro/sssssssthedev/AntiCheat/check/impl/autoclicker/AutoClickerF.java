package ro.sssssssthedev.AntiCheat.check.impl.autoclicker;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInArmAnimation;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInFlying;

@CheckData(name = "AutoClicker (F)", threshold = 2)
public final class AutoClickerF extends PacketCheck {
    private int swings, ticks;

    public AutoClickerF(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            if (++ticks == 20) {
                final boolean invalid = swings > 20;

                if (invalid) {
                    this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                }

                ticks = swings = 0;
            }
        } else if (packet instanceof WrappedPacketPlayInArmAnimation) {
            final boolean digging = playerData.getActionManager().getSwinging().get();

            if (digging) {
                ++swings;
            }
        }
    }
}
