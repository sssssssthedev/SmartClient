package ro.sssssssthedev.AntiCheat.check.impl.autoclicker;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInArmAnimation;

import java.util.Deque;
import java.util.LinkedList;

@CheckData(name = "AutoClicker (A)", threshold = 7)
public final class AutoClickerA extends PacketCheck {
    private long lastArmAnimation;
    private double lastAverage;
    private int buffer;
    private final Deque<Long> swingSamples = new LinkedList<>();

    public AutoClickerA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInArmAnimation) {
            final long now = System.currentTimeMillis();
            final long delay = now - lastArmAnimation;

            final boolean digging = playerData.getActionManager().getDigging().get();

            if (delay < 180L && delay > 1L && !digging) {
                swingSamples.add(delay);
            }

            if (swingSamples.size() == 20) {
                // Get the delay average
                final double average = swingSamples.stream().mapToLong(l -> l).average().orElse(0.0);

                // Get the swing deviation
                final double totalSwings = swingSamples.stream().mapToLong(change -> change).asDoubleStream().sum();
                final double mean = totalSwings / swingSamples.size();
                final double deviation = swingSamples.stream().mapToLong(change -> change).mapToDouble(change -> Math.pow(change - mean, 2)).sum();

                final boolean invalidSwing = Math.sqrt(deviation) < 150.0 && average > 100.0 && Math.abs(average - lastAverage) <= 5.0;

                // Impossible (technically)
                if (invalidSwing) {
                    if (++buffer > 1) {
                        this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                    }
                } else {
                    buffer = Math.max(buffer - 1, 0);
                }

                // Pass average and clear list
                lastAverage = average;
                swingSamples.clear();
            }

            this.lastArmAnimation = now;
        }
    }
}
