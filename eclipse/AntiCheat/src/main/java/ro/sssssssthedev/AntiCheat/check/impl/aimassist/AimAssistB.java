package ro.sssssssthedev.AntiCheat.check.impl.aimassist;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.RotationCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.RotationUpdate;
import ro.sssssssthedev.AntiCheat.update.head.HeadRotation;

@CheckData(name = "AimAssist (B)")
public final class AimAssistB extends RotationCheck {
    private int streak;

    public AimAssistB(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        if (deltaYaw > 0.0 && deltaYaw < 30.f) {
            final boolean cinematic = playerData.getCinematic().get();
            final boolean attack = playerData.getActionManager().getAttacking().get();

            if (deltaPitch == 0.0 && !cinematic && attack) {
                this.debug("[AA B]: " + playerData.getPlayer().getName() + " had a pitch delta value of: " + deltaPitch);

                if (++streak > 10.f) {
                    this.handleViolation().addViolation(ViolationLevel.LOW).create();
                }
            } else {
                streak = 0;
            }
        } else {
            streak = 0;
        }
    }
}
