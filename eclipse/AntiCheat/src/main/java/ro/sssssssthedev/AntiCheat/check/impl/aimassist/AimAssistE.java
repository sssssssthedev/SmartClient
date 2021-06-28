package ro.sssssssthedev.AntiCheat.check.impl.aimassist;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.RotationCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.RotationUpdate;
import ro.sssssssthedev.AntiCheat.update.head.HeadRotation;

@CheckData(name = "AimAssist (E)")
public final class AimAssistE extends RotationCheck {
    private int buffer;

    public AimAssistE(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        if (deltaYaw > 1.0 && deltaPitch > 0.0 && deltaPitch % 1.0 == 0.0) {
            buffer += 5;

            if (buffer > 15) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        } else {
            buffer = 0;
        }

        if (deltaYaw > 1.0 && deltaPitch > 0.0 && deltaYaw % 1.0 == 0.0) {
            buffer += 5;

            if (buffer > 15) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        } else {
            buffer = 0;
        }

        if (deltaYaw > 1.0 && deltaPitch > 0.0 && deltaPitch % 10.0 == 0.0) {
            buffer += 5;

            if (buffer > 15) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        }

        if (deltaYaw > 1.0 && deltaPitch > 0.0 && deltaYaw % 10.0 == 0.0) {
            buffer += 5;

            if (buffer > 15) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        }
    }
}
