package ro.sssssssthedev.AntiCheat.check.impl.autotool;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInHeldItemSlot;

@CheckData(name = "AutoTool", threshold = 3)
public final class AutoTool extends PacketCheck {
    private int lastSlot;

    public AutoTool(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInHeldItemSlot) {
            final WrappedPacketPlayInHeldItemSlot wrapper = (WrappedPacketPlayInHeldItemSlot) packet;

            final int slot = wrapper.getSlot();

            if (slot == lastSlot) {
                this.handleViolation().addViolation(ViolationLevel.HIGH).create();
            }

            this.lastSlot = slot;
        }
    }
}
