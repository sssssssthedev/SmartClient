package ro.sssssssthedev.AntiCheat.hook;

import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created on 28/04/2020 Package us.overflow.anticheat.hook
 */
@Getter
public final class ClassManager {

    private static ClassManager classManager;

    private List<String> resolvedStrings = new CopyOnWriteArrayList<>();

    public boolean flag = false;

    private String s1, s2;

    public ClassManager() {
        classManager = this;
    }

    public void start() {
          try {
        	
            Class.forName("ro.sssssssthedev.AntiCheat.hook.HookManager").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
