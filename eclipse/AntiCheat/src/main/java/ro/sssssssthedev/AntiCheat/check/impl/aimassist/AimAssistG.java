package ro.sssssssthedev.AntiCheat.check.impl.aimassist;

import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.RotationCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.RotationUpdate;
import ro.sssssssthedev.AntiCheat.update.head.HeadRotation;

import java.util.Deque;
import java.util.LinkedList;

@CheckData(name = "AimAssist (G)")
public final class AimAssistG extends RotationCheck {
    private final Deque<Double> deltaXSamples = new LinkedList<>();
    private final Deque<Double> deltaYSamples = new LinkedList<>();

    private int buffer;

    public AimAssistG(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        final double sensitivityX = playerData.getSensitivityX();
        final double sensitivityY = playerData.getSensitivityY();

        final boolean rotationValid = deltaYaw < 100.f && deltaPitch < 100.f;
        final boolean sensitivityValid = Math.abs(sensitivityX - sensitivityY) < 1e-06;

        if (sensitivityValid && rotationValid) {
            final double deltaX = playerData.getMouseDeltaX();
            final double deltaY = playerData.getMouseDeltaY();

            deltaXSamples.add(deltaX);
            deltaYSamples.add(deltaY);
        }

        if (deltaXSamples.size() == 5 && deltaYSamples.size() == 5) {
            final double averageDeltaX = deltaXSamples.stream().mapToDouble(d -> d).average().orElse(0.0);
            final double averageDeltaY = deltaYSamples.stream().mapToDouble(d -> d).average().orElse(0.0);

            if (averageDeltaX == 0.0 && averageDeltaY == 0.0) {
                if (++buffer > 8) {
                    //this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                }
            } else {
                buffer = 0;
            }

            deltaXSamples.clear();
            deltaYSamples.clear();
        }
    }
}
