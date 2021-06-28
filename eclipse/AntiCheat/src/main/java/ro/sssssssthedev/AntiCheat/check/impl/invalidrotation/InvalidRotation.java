package ro.sssssssthedev.AntiCheat.check.impl.invalidrotation;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.RotationCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.RotationUpdate;
import ro.sssssssthedev.AntiCheat.update.head.HeadRotation;

@CheckData(name = "InvalidRotation")
public final class InvalidRotation extends RotationCheck {

    public InvalidRotation(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float rotationPitch = Math.max(from.getPitch(), to.getPitch());

        if (Math.abs(rotationPitch) > 0.0 && !Double.isNaN(rotationPitch)) {
            final double threshold = playerData.getPositionManager().getTouchingClimbable().get() ? 90.11f : 90.f;

            if (Math.abs(rotationPitch) > threshold) {
                this.handleViolation().addViolation(ViolationLevel.HIGH).create();
            }
        }
    }
}
