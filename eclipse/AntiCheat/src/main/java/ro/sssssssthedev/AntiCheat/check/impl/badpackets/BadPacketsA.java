package ro.sssssssthedev.AntiCheat.check.impl.badpackets;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.RotationCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.RotationUpdate;
import ro.sssssssthedev.AntiCheat.update.head.HeadRotation;

@CheckData(name = "BadPackets (A)", threshold = 7)
public final class BadPacketsA extends RotationCheck {

    public BadPacketsA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation to = rotationUpdate.getTo();
        final HeadRotation from = rotationUpdate.getFrom();

        if (Math.abs(to.getYaw() - from.getYaw()) > 1000.0) {
            this.handleViolation().addViolation(ViolationLevel.HIGH).create();
        }
    }
}
