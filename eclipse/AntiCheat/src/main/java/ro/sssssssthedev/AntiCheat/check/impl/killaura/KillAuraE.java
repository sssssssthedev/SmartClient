package ro.sssssssthedev.AntiCheat.check.impl.killaura;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.RotationCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.RotationUpdate;
import ro.sssssssthedev.AntiCheat.utils.MathUtil;
import ro.sssssssthedev.AntiCheat.update.head.HeadRotation;

@CheckData(name = "KillAura (E)")
public final class KillAuraE extends RotationCheck {

    public KillAuraE(final PlayerData playerData) {
        super(playerData);
    }

    private float lastPitchDifference;

    private final double offset = Math.pow(2.0, 24.0);;

    private int verbose;

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

       final long gcd = MathUtil.gcd((long) (deltaPitch * offset), (long) (this.lastPitchDifference * offset));

       if (playerData.getCinematic().get()) {
           if (verbose > 0) verbose-=7;
       }

       if (to != from &&  Math.abs(to.getPitch() - from.getPitch()) > 0.0 && Math.abs(to.getPitch()) != 90.0f) {
           if (gcd < 131072L) {
               if (verbose > 9) {
                   this.handleViolation().addViolation(ViolationLevel.HIGH).create();
               }

               if (verbose < 20) verbose++;
           } else {
               if (verbose > 0) verbose--;
           }
       }

        this.lastPitchDifference = deltaPitch;
    }
}
