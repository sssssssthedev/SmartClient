package ro.sssssssthedev.AntiCheat.data.type;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import ro.sssssssthedev.AntiCheat.check.Check;
import ro.sssssssthedev.AntiCheat.check.impl.aimassist.*;
import ro.sssssssthedev.AntiCheat.check.impl.autoclicker.*;
import ro.sssssssthedev.AntiCheat.check.impl.badpackets.*;
import ro.sssssssthedev.AntiCheat.check.impl.flight.*;
import ro.sssssssthedev.AntiCheat.check.impl.killaura.*;
import ro.sssssssthedev.AntiCheat.check.impl.aimassist.*;
import ro.sssssssthedev.AntiCheat.check.impl.aimassist.prediction.Cinematic;
import ro.sssssssthedev.AntiCheat.check.impl.aimassist.prediction.Prediction;
import ro.sssssssthedev.AntiCheat.check.impl.autoclicker.*;
import ro.sssssssthedev.AntiCheat.check.impl.badpackets.*;
import ro.sssssssthedev.AntiCheat.check.impl.flight.*;
import ro.sssssssthedev.AntiCheat.check.impl.invalid.InvalidA;
import ro.sssssssthedev.AntiCheat.check.impl.invalid.InvalidB;
import ro.sssssssthedev.AntiCheat.check.impl.killaura.*;
import ro.sssssssthedev.AntiCheat.check.impl.motion.MotionA;
import ro.sssssssthedev.AntiCheat.check.impl.motion.MotionB;
import ro.sssssssthedev.AntiCheat.check.impl.motion.MotionC;
import ro.sssssssthedev.AntiCheat.check.impl.motion.MotionD;
import ro.sssssssthedev.AntiCheat.check.impl.reach.Reach;
import ro.sssssssthedev.AntiCheat.check.impl.scaffold.ScaffoldA;
import ro.sssssssthedev.AntiCheat.check.impl.scaffold.ScaffoldB;
import ro.sssssssthedev.AntiCheat.check.impl.scaffold.ScaffoldC;
import ro.sssssssthedev.AntiCheat.check.impl.speed.SpeedA;
import ro.sssssssthedev.AntiCheat.check.impl.speed.SpeedB;
import ro.sssssssthedev.AntiCheat.check.impl.speed.SpeedC;
import ro.sssssssthedev.AntiCheat.check.impl.speed.SpeedD;
import ro.sssssssthedev.AntiCheat.check.impl.speed.SpeedE;
import ro.sssssssthedev.AntiCheat.check.impl.timer.TimerA;
import ro.sssssssthedev.AntiCheat.check.impl.velocity.VelocityA;
import ro.sssssssthedev.AntiCheat.check.impl.velocity.VelocityB;
import ro.sssssssthedev.AntiCheat.data.PlayerData;

import java.util.Collection;

public final class CheckManager {
    private ClassToInstanceMap<Check> checks;

    public CheckManager(final PlayerData playerData) {

            checks = new ImmutableClassToInstanceMap.Builder<Check>()
                    .put(Cinematic.class, new Cinematic(playerData))
                    .put(Prediction.class, new Prediction(playerData))
                    .put(MotionA.class, new MotionA(playerData))
                    .put(MotionB.class, new MotionB(playerData))
                    .put(MotionC.class, new MotionC(playerData))
                    .put(MotionD.class, new MotionD(playerData))
                    .put(FlightA.class, new FlightA(playerData))
                    .put(FlightB.class, new FlightB(playerData))
                    .put(FlightC.class, new FlightC(playerData))
                    .put(FlightD.class, new FlightD(playerData))
                    .put(FlightE.class, new FlightE(playerData))
                    .put(KillAuraA.class, new KillAuraA(playerData))
                    .put(KillAuraB.class, new KillAuraB(playerData))
                    .put(KillAuraC.class, new KillAuraC(playerData))
                    .put(KillAuraD.class, new KillAuraD(playerData))
                    .put(KillAuraE.class, new KillAuraE(playerData))
                    .put(KillAuraF.class, new KillAuraF(playerData))
                    .put(KillAuraG.class, new KillAuraG(playerData))
                    .put(KillAuraH.class, new KillAuraH(playerData))
                    .put(InvalidA.class, new InvalidA(playerData))
                    .put(InvalidB.class, new InvalidB(playerData))
                    .put(AimAssistA.class, new AimAssistA(playerData))
                    .put(AimAssistB.class, new AimAssistB(playerData))
                    .put(AimAssistC.class, new AimAssistC(playerData))
                    .put(AimAssistD.class, new AimAssistD(playerData))
                    .put(AimAssistE.class, new AimAssistE(playerData))
                    .put(AimAssistF.class, new AimAssistF(playerData))
                    .put(AimAssistG.class, new AimAssistG(playerData))
                    .put(AutoClickerA.class, new AutoClickerA(playerData))
                    .put(AutoClickerB.class, new AutoClickerB(playerData))
                    .put(AutoClickerC.class, new AutoClickerC(playerData))
                    .put(AutoClickerD.class, new AutoClickerD(playerData))
                    .put(AutoClickerE.class, new AutoClickerE(playerData))
                    .put(ScaffoldA.class, new ScaffoldA(playerData))
                    .put(ScaffoldB.class, new ScaffoldB(playerData))
                    .put(ScaffoldC.class, new ScaffoldC(playerData))
                    .put(BadPacketsA.class, new BadPacketsA(playerData))
                    .put(BadPacketsB.class, new BadPacketsB(playerData))
                    .put(BadPacketsC.class, new BadPacketsC(playerData))
                    .put(BadPacketsD.class, new BadPacketsD(playerData))
                    .put(BadPacketsE.class, new BadPacketsE(playerData))
                    .put(BadPacketsF.class, new BadPacketsF(playerData))
                    .put(VelocityA.class, new VelocityA(playerData))
                    .put(VelocityB.class, new VelocityB(playerData))
                    .put(SpeedA.class, new SpeedA(playerData))
                    .put(SpeedB.class, new SpeedB(playerData))
                    .put(TimerA.class, new TimerA(playerData))
                    .put(SpeedC.class, new SpeedC(playerData))
                    .put(FlightF.class, new FlightF(playerData))
                    .put(SpeedD.class, new SpeedD(playerData))
                    .put(SpeedE.class, new SpeedE(playerData))
                    .put(Reach.class, new Reach(playerData))
                    .build();
        
    }

    // Get a specific check for pushing a packet/event, or do whatever action possible within said check class
    public final <T extends Check> T getCheck(final Class<T> clazz) {
        return checks.getInstance(clazz);
    }

    // Get all checks we have stored
    public final Collection<Check> getChecks() {
        return checks.values();
    }
}
