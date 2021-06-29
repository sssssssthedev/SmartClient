package net.sssssssthedev.SmartClient.module;

import net.sssssssthedev.SmartClient.module.combat.*;
import net.sssssssthedev.SmartClient.module.crasher.*;
import net.sssssssthedev.SmartClient.module.movement.*;
import net.sssssssthedev.SmartClient.module.movement.Fly;
import net.sssssssthedev.SmartClient.module.player.*;
import net.sssssssthedev.SmartClient.module.render.*;
import net.sssssssthedev.SmartClient.module.world.*;

import java.util.ArrayList;

public class ModuleManager {

    private final ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // COMBAT
        modules.add(new AntiBot());
        modules.add(new AutoArmor());
        modules.add(new Criticals());
        modules.add(new KillAura());
        modules.add(new Velocity());

        // MOVEMENT
        modules.add(new Fly());
        modules.add(new LongJump());
        modules.add(new Phase());
        modules.add(new Scaffold());
        modules.add(new Speed());
        modules.add(new Sprint());
        modules.add(new Step());

        // RENDER
        modules.add(new ClickGUI());
        modules.add(new Fullbright());
        //modules.add(new ESP());

        // PLAYER
        modules.add(new ChestStealer());
        modules.add(new InstantRespawn());
        modules.add(new InvCleaner());
        modules.add(new InvMove());
        modules.add(new NoFall());

        // MISC

        // CRASHER
        modules.add(new Censored());
        modules.add(new CustomPayload());
        modules.add(new FlyCrash());
        modules.add(new Food());
        modules.add(new Netty());
        modules.add(new NettyOnePacket());
        modules.add(new NullPointCrasher());
        modules.add(new NullSmasher());
        modules.add(new SinglePacket());

        // WORLD
        modules.add(new AutoMine());
        modules.add(new BedFucker());
        modules.add(new FakeCreative());
        modules.add(new FastBreak());
        modules.add(new FastPlace());
        modules.add(new Nuker());
    }
    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
