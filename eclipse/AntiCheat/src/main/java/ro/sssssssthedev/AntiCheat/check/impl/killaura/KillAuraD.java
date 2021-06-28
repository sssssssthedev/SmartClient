package ro.sssssssthedev.AntiCheat.check.impl.killaura;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.RotationCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.RotationUpdate;
import ro.sssssssthedev.AntiCheat.update.head.HeadRotation;

import java.util.Deque;
import java.util.LinkedList;

@CheckData(name = "KillAura (D)")
public final class KillAuraD extends RotationCheck {
    private final Deque<Float> yawSamples = new LinkedList<>();
    private final Deque<Float> pitchSamples = new LinkedList<>();

    private int buffer;

    public KillAuraD(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        final boolean cinematic = playerData.getCinematic().get();

        if (deltaYaw > 0.0 && deltaPitch > 0.0 && deltaYaw < 50.f && deltaPitch < 20.f && !cinematic) {
            pitchSamples.add(deltaPitch);
            yawSamples.add(deltaYaw);
        }

        if (pitchSamples.size() == 20 && yawSamples.size() == 20) {
            final long distinctPitch = pitchSamples.stream().distinct().count();
            final long distinctYaw = yawSamples.stream().distinct().count();

            final double duplicatesPitch = pitchSamples.size() - distinctPitch;
            final double duplicatesYaw = yawSamples.size() - distinctYaw;

            if (duplicatesPitch == 0.0 && duplicatesYaw == 0.0) {
                if (++buffer > 6) {
                    this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                }
            } else {
                buffer = 0;
            }

            pitchSamples.clear();
            yawSamples.clear();
        }
    }
}
