package ro.sssssssthedev.AntiCheat.check.impl.killaura;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.RotationCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.RotationUpdate;
import ro.sssssssthedev.AntiCheat.utils.MathUtil;
import ro.sssssssthedev.AntiCheat.update.head.HeadRotation;

import java.util.Deque;
import java.util.LinkedList;

@CheckData(name = "KillAura (B)")
public final class KillAuraB extends RotationCheck {
    private final Deque<Float> pitchSamples = new LinkedList<>();

    private double buffer;
    private double lastAverage;

    public KillAuraB(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final long now = System.currentTimeMillis();

        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        final boolean attacked = now - playerData.getActionManager().getLastAttack() < 500L;

        if (deltaYaw > 0.0 && deltaPitch > 0.0 && deltaPitch < 30.f && attacked) {
            pitchSamples.add(deltaPitch);
        }

        if (pitchSamples.size() == 20) {
            final double averagePitch = pitchSamples.stream().mapToDouble(d -> d).average().orElse(0.0);
            final double deviationPitch = MathUtil.deviationSquared(pitchSamples);

            final double averageDelta = Math.abs(averagePitch - lastAverage);

            if (deviationPitch > 6.d && averageDelta > 1.d && deviationPitch < 300.f) {
                if (++buffer > 6) {
                    this.handleViolation().addViolation(ViolationLevel.LOW).create();
                }
            } else {
                buffer = Math.max(buffer - 0.25, 0);
            }

            lastAverage = averagePitch;
            pitchSamples.clear();
        }
    }
}
