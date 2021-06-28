package ro.sssssssthedev.AntiCheat.config.impl;

import org.bukkit.configuration.file.YamlConfiguration;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.AntiCheatPlugin;
import ro.sssssssthedev.AntiCheat.check.Check;
import ro.sssssssthedev.AntiCheat.check.impl.aimassist.*;
import ro.sssssssthedev.AntiCheat.check.impl.autoclicker.*;
import ro.sssssssthedev.AntiCheat.check.impl.badpackets.*;
import ro.sssssssthedev.AntiCheat.check.impl.flight.*;
import ro.sssssssthedev.AntiCheat.check.impl.killaura.*;
import ro.sssssssthedev.AntiCheat.check.impl.aimassist.*;
import ro.sssssssthedev.AntiCheat.check.impl.aimassist.prediction.Prediction;
import ro.sssssssthedev.AntiCheat.check.impl.autoclicker.*;
import ro.sssssssthedev.AntiCheat.check.impl.autotool.AutoTool;
import ro.sssssssthedev.AntiCheat.check.impl.badpackets.*;
import ro.sssssssthedev.AntiCheat.check.impl.flight.*;
import ro.sssssssthedev.AntiCheat.check.impl.invalid.InvalidA;
import ro.sssssssthedev.AntiCheat.check.impl.invalid.InvalidB;
import ro.sssssssthedev.AntiCheat.check.impl.invalidposition.InvalidPosition;
import ro.sssssssthedev.AntiCheat.check.impl.invalidrotation.InvalidRotation;
import ro.sssssssthedev.AntiCheat.check.impl.killaura.*;
import ro.sssssssthedev.AntiCheat.check.impl.motion.MotionA;
import ro.sssssssthedev.AntiCheat.check.impl.motion.MotionB;
import ro.sssssssthedev.AntiCheat.check.impl.motion.MotionC;
import ro.sssssssthedev.AntiCheat.check.impl.motion.MotionD;
import ro.sssssssthedev.AntiCheat.check.impl.scaffold.ScaffoldA;
import ro.sssssssthedev.AntiCheat.check.impl.scaffold.ScaffoldB;
import ro.sssssssthedev.AntiCheat.check.impl.scaffold.ScaffoldC;
import ro.sssssssthedev.AntiCheat.check.impl.speed.SpeedA;
import ro.sssssssthedev.AntiCheat.check.impl.speed.SpeedB;
import ro.sssssssthedev.AntiCheat.check.impl.speed.SpeedC;
import ro.sssssssthedev.AntiCheat.check.impl.speed.SpeedE;
import ro.sssssssthedev.AntiCheat.check.impl.timer.TimerA;
import ro.sssssssthedev.AntiCheat.config.type.Config;

import java.io.File;
import java.util.Arrays;

public final class CheckConfig implements Config {

    private File file;
    private YamlConfiguration config;

    private final Class[] checkClasses = new Class[] {
            MotionA.class, MotionB.class, MotionC.class, MotionD.class,
            FlightA.class, FlightB.class, FlightC.class, FlightD.class, FlightE.class,
            KillAuraA.class, KillAuraB.class, KillAuraC.class, KillAuraD.class, KillAuraE.class, KillAuraF.class, KillAuraG.class,
            AutoClickerA.class, AutoClickerB.class, AutoClickerE.class, AutoClickerD.class, AutoClickerE.class, AutoClickerF.class,
            AimAssistA.class, AimAssistB.class, AimAssistC.class, AimAssistD.class, AimAssistE.class, AimAssistF.class, AimAssistG.class,
            BadPacketsA.class, BadPacketsB.class, BadPacketsC.class, BadPacketsD.class, BadPacketsE.class, BadPacketsF.class, BadPacketsG.class,
            ScaffoldA.class, ScaffoldB.class, ScaffoldC.class,
            InvalidA.class, InvalidB.class,
            SpeedA.class, SpeedB.class, SpeedC.class,
            TimerA.class,
            AutoTool.class,
            InvalidPosition.class, InvalidRotation.class,
            Prediction.class, SpeedE.class,
    };

    @Override
    public void generate() {
        this.create();

        Arrays.stream(checkClasses).filter(check -> !config.contains("check." + check.getSimpleName().toLowerCase())).forEach(check -> {
            config.set("check." + check.getSimpleName().toLowerCase() + ".enabled", true);
            config.set("check." + check.getSimpleName().toLowerCase() + ".autoban", true);
            config.set("check." + check.getSimpleName().toLowerCase() + ".threshold", 15);
            config.set("check." + check.getSimpleName().toLowerCase() + ".punishment", "ban %player% [OverFlow] Unfair Advantage -s");
        });

        try {
            config.save(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void create() {
        final AntiCheatPlugin plugin = AntiCheatAPI.INSTANCE.getPlugin();

        this.file = new File(plugin.getDataFolder(), "checks.yml");

        if (!file.exists()) {
            try {
                file.getParentFile().mkdir();

                plugin.saveResource("checks.yml", false);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.config = new YamlConfiguration();

        try {
            config.load(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getCheckEnabled(final Check check) {
        return config.getBoolean("check." + check.getClass().getSimpleName().toLowerCase() + ".enabled");
    }

    public boolean getCheckAutoban(final Check check) {
        return config.getBoolean("check." + check.getClass().getSimpleName().toLowerCase() + ".autoban");
    }

    public String getPunishment(final Check check) {
        return config.getString("check." + check.getClass().getSimpleName().toLowerCase() + ".punishment");
    }

    public int getThreshold(final Check check) {
        return config.getInt("check." + check.getClass().getSimpleName().toLowerCase() + ".threshold");
    }
}
