package ro.sssssssthedev.AntiCheat.check.impl.killaura;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInBlockDig;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInUseEntity;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumEntityUseAction;

@CheckData(name = "KillAura (G)")
public final class KillAuraG extends PacketCheck {

    public KillAuraG(final PlayerData playerData) {
        super(playerData);
    }

    private int lastBlockDig = -1;

    @Override
    public void process(WrappedPacket packet) {

        if (packet instanceof WrappedPacketPlayInBlockDig) {
            lastBlockDig = playerData.getClientTicks();
        }

        if (packet instanceof WrappedPacketPlayInUseEntity) {
            WrappedPacketPlayInUseEntity wrappedPacketPlayInUseEntity = (WrappedPacketPlayInUseEntity) packet;

            if (wrappedPacketPlayInUseEntity.getEntity() != null && wrappedPacketPlayInUseEntity.getUseAction() == EnumEntityUseAction.ATTACK && playerData.getClientTicks() == lastBlockDig) {
                this.handleViolation().addViolation(ViolationLevel.HIGH).create();
            }
        }
    }
}
