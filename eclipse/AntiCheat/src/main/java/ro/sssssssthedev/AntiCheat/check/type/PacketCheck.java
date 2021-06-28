package ro.sssssssthedev.AntiCheat.check.type;

import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.check.Check;
import ro.sssssssthedev.AntiCheat.data.PlayerData;

public class PacketCheck extends Check<WrappedPacket> {

    public PacketCheck(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(WrappedPacket packet) {

    }
}
