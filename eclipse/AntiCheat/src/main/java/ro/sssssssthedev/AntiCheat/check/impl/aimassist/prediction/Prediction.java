package ro.sssssssthedev.AntiCheat.check.impl.aimassist.prediction;

import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.RotationCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.RotationUpdate;
import ro.sssssssthedev.AntiCheat.utils.EvictingList;
import ro.sssssssthedev.AntiCheat.utils.MathUtil;
import ro.sssssssthedev.AntiCheat.update.head.HeadRotation;


@CheckData(name = "Prediction")
public final class Prediction extends RotationCheck {
    private float lastDeltaYaw, lastDeltaPitch;
    public double sensitivityX, sensitivityY, deltaX, deltaY;

    private final EvictingList<Double> yawSamples = new EvictingList<>(50);
    private final EvictingList<Double> pitchSamples = new EvictingList<>(50);

    public Prediction(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        final double gcdYaw = MathUtil.getGcd((long) (deltaYaw * MathUtil.EXPANDER), (long) (lastDeltaYaw * MathUtil.EXPANDER));
        final double gcdPitch = MathUtil.getGcd((long) (deltaPitch * MathUtil.EXPANDER), (long) (lastDeltaPitch * MathUtil.EXPANDER));

        final double dividedYawGcd = gcdYaw / MathUtil.EXPANDER;
        final double dividedPitchGcd = gcdPitch / MathUtil.EXPANDER;

        if (gcdYaw > 90000 && gcdYaw < 2E7 && dividedYawGcd > 0.01f && deltaYaw < 8) {
            yawSamples.add(dividedYawGcd);
        }

        if (gcdPitch > 90000 && gcdPitch < 2E7 && deltaPitch < 8) {
            pitchSamples.add(dividedPitchGcd);
        }

        double modeYaw = 0.0;
        double modePitch = 0.0;

        if (pitchSamples.size() > 3 && yawSamples.size() > 3) {
            modeYaw = MathUtil.getMode(yawSamples);
            modePitch = MathUtil.getMode(pitchSamples);
        }

        final double deltaX = deltaYaw / modeYaw;
        final double deltaY = deltaPitch / modePitch;

        final double sensitivityX = getSensitivityFromYawGCD(modeYaw);
        final double sensitivityY = getSensitivityFromPitchGCD(modePitch);

        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.sensitivityX = sensitivityX;
        this.sensitivityY = sensitivityY;
        this.lastDeltaYaw = deltaYaw;
        this.lastDeltaPitch = deltaPitch;
    }

    private static double yawToF2(double yawDelta) {
        return yawDelta / .15;
    }

    private static double pitchToF3(double pitchDelta) {
        int b0 = pitchDelta >= 0 ? 1 : -1; //Checking for inverted mouse.
        return pitchDelta / .15 / b0;
    }

    private static double getSensitivityFromPitchGCD(double gcd) {
        double stepOne = pitchToF3(gcd) / 8;
        double stepTwo = Math.cbrt(stepOne);
        double stepThree = stepTwo - .2f;
        return stepThree / .6f;
    }

    private static double getSensitivityFromYawGCD(double gcd) {
        double stepOne = yawToF2(gcd) / 8;
        double stepTwo = Math.cbrt(stepOne);
        double stepThree = stepTwo - .2f;
        return stepThree / .6f;
    }

    private static double getFFromYaw(double gcd) {
        double sens = getSensitivityFromYawGCD(gcd);
        return sens * .6f + .2;
    }
}
