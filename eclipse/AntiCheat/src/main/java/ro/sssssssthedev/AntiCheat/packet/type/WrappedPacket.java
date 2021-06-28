package ro.sssssssthedev.AntiCheat.packet.type;

import lombok.Getter;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.processor.impl.PacketProcessor;

@Getter
public abstract class WrappedPacket {
    private final String simpleName = this.getClass().getSimpleName();

    public synchronized void parse(final PlayerData playerData) {
        AntiCheatAPI.INSTANCE.getProcessorManager().getProcessor(PacketProcessor.class).process(playerData, this);
    }
}
