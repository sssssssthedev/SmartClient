package ro.sssssssthedev.AntiCheat.check.impl.autoclicker;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInArmAnimation;
import ro.sssssssthedev.AntiCheat.utils.MathUtil;

import java.util.Deque;
import java.util.LinkedList;

@CheckData(name = "AutoClicker (C)", threshold = 9)
public final class AutoClickerC extends PacketCheck {
    private long lastArmAnimation;
    private double lastDeviation, lastAverage;
    private final Deque<Long> samples = new LinkedList<>();

    public AutoClickerC(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInArmAnimation) {
            final long now = System.currentTimeMillis();
            final long delay = now - lastArmAnimation;

            if (delay > 1L && delay <= 150L && !playerData.getActionManager().getDigging().get()) {
                samples.add(delay);
            }

            if (samples.size() == 20) {
                final double average = samples.stream().mapToDouble(d -> d).average().orElse(0.0);
                final double stdDeviation = MathUtil.deviationSquared(samples);

                final double averageDelta = Math.abs(average - lastAverage);

                if (stdDeviation < 16.d && average > 8.d && stdDeviation == lastDeviation && averageDelta <= 1.5) {
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }

                lastAverage = average;
                lastDeviation = stdDeviation;
                samples.clear();
            }

            this.lastArmAnimation = now;
        }
    }
}
