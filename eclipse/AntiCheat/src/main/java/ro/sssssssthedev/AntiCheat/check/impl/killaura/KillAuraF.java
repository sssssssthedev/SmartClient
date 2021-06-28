package ro.sssssssthedev.AntiCheat.check.impl.killaura;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.RotationCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.RotationUpdate;
import ro.sssssssthedev.AntiCheat.utils.MathUtil;
import ro.sssssssthedev.AntiCheat.utils.Verbose;
import ro.sssssssthedev.AntiCheat.update.head.HeadRotation;

@CheckData(name = "KillAura (F)")
public final class KillAuraF extends RotationCheck {

    public KillAuraF(final PlayerData playerData) {
        super(playerData);
    }

    private final Verbose verbose = new Verbose();

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        final double roundYaw1 = Math.round(deltaYaw), roundPitch1 = Math.round(deltaPitch);
        final double roundYaw2 = MathUtil.preciseRound(deltaYaw, 1), roundPitch2 = MathUtil.preciseRound(deltaPitch, 1);

        if (deltaYaw > 1.0 && deltaPitch > 0.7 && (deltaYaw == roundYaw1 || roundPitch1 == deltaPitch || roundYaw2 == deltaYaw || roundPitch2 == deltaPitch) && verbose.flag(6, 999L)) {
            this.handleViolation().addViolation(ViolationLevel.HIGH).create();
        }
    }
}
