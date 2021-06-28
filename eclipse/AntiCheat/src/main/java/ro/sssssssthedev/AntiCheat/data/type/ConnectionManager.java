package ro.sssssssthedev.AntiCheat.data.type;

import lombok.Getter;
import ro.sssssssthedev.AntiCheat.data.Observable;
import ro.sssssssthedev.AntiCheat.utils.EvictingMap;

public final class ConnectionManager {
    private final EvictingMap<Long, Long> sentKeepAlives = new EvictingMap<>(20);
    private final EvictingMap<Short, Long> sentTransactions = new EvictingMap<>(20);

    @Getter
    private final Observable<Boolean> delayed = new Observable<>(false);

    private long packets, lastFlying, lastDelayedPacket;

    public void onFlying() {
        final long now = System.currentTimeMillis();

        final long packetNext = ++packets;
        final short packetId = (short) (packetNext & Short.MAX_VALUE);

        if (now - lastFlying > 120L) {
            lastDelayedPacket = now;
        }

        delayed.set(now - lastDelayedPacket < 120L);

        sentKeepAlives.put(packetNext, System.currentTimeMillis());
        sentTransactions.put(packetId, System.currentTimeMillis());
        lastFlying = now;
    }

    public void onTransaction(final short action) {
        final boolean found = sentTransactions.containsKey(action);

        // No need to account for spoofed transactions
        if (found) {

        }
    }

    public void onKeepAlive(final long action) {
        final boolean found = sentKeepAlives.containsKey(action);

        // No need to account for spoofed keep alives
        if (found) {

        }
    }
}
