package ro.sssssssthedev.AntiCheat.check.impl.autoclicker;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInArmAnimation;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

@CheckData(name = "AutoClicker (E)")
public final class AutoClickerE extends PacketCheck {
    private long lastAnimation;
    private final Deque<Long> swingDeque = new LinkedList<>();

    public AutoClickerE(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInArmAnimation) {
            final long now = System.currentTimeMillis();
            final long delay = now - lastAnimation;

            if (delay < 100L && !playerData.getActionManager().getDigging().get()) {
                swingDeque.add(delay);
            }

            if (swingDeque.size() == 5) {
                final AtomicInteger doubles = new AtomicInteger();
                final AtomicInteger triples = new AtomicInteger();

                swingDeque.stream().filter(d -> d == 1L).forEach(d -> doubles.incrementAndGet());
                swingDeque.stream().filter(d -> d == 0L).forEach(d -> triples.incrementAndGet());

                if (doubles.get() == 5 || triples.get() == 5) {
                    this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                }

                swingDeque.clear();
            }

            this.lastAnimation = now;
        }
    }
}
