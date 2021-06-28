package ro.sssssssthedev.AntiCheat.processor.type;

import ro.sssssssthedev.AntiCheat.data.PlayerData;

public interface Processor<T> {
    // This will handle the processing of any inbound/outbound packets as well as events
    void process(final PlayerData playerData, final T t);
}
