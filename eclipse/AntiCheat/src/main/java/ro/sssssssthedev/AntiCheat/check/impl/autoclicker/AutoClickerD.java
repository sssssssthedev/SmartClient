package ro.sssssssthedev.AntiCheat.check.impl.autoclicker;

import lombok.val;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInArmAnimation;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInBlockDig;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumPlayerDigType;

public final class AutoClickerD extends PacketCheck {
    private boolean dug;
    private int buffer;

    public AutoClickerD(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInBlockDig) {
            final WrappedPacketPlayInBlockDig wrapper = (WrappedPacketPlayInBlockDig) packet;

            // Excuse the val I just got fucking bored
            val digType = wrapper.getDigType();

            if (digType == EnumPlayerDigType.START_DESTROY_BLOCK) {
                this.dug = true;
            } else if (digType == EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                if (dug) {
                    if (++buffer > 9) {
                        this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                    }
                } else {
                    buffer = Math.max(buffer - 3, 0);
                }
            }
        } else if (packet instanceof WrappedPacketPlayInArmAnimation) {
            //sent an armanimation between dig and stopped digging
            this.dug = false;
        }
    }
}
