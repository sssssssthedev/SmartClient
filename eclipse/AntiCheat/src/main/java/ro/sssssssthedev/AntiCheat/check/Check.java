package ro.sssssssthedev.AntiCheat.check;

import lombok.Getter;
import ro.sssssssthedev.AntiCheat.alert.Alert;
import ro.sssssssthedev.AntiCheat.config.impl.CheckConfig;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.data.PlayerData;

import java.util.Deque;
import java.util.LinkedList;

@Getter
public abstract class Check<T> {
    protected PlayerData playerData;

    private final Deque<String> debugs = new LinkedList<>();

    private String checkName;
    private int threshold;

    private boolean enabled, autobans;

    private Alert alert = new Alert();

    public Check(final PlayerData playerData) {
        this.playerData = playerData;

        final CheckConfig checkConfig = AntiCheatAPI.INSTANCE.getConfigManager().getConfig(CheckConfig.class);
        final Class clazz = this.getClass();

        if (clazz.isAnnotationPresent(CheckData.class)) {
            final CheckData checkData = (CheckData) clazz.getAnnotation(CheckData.class);

            this.checkName = checkData.name();
            this.threshold = checkData.threshold();
            this.threshold = checkConfig.getThreshold(this);
            this.autobans = checkConfig.getCheckAutoban(this);
            this.enabled = checkConfig.getCheckEnabled(this);
        }
    }

    protected Alert handleViolation() {
        return alert;
    }

    protected void debug(final String log) {
        final boolean enabledDebug = (boolean) AntiCheatAPI.INSTANCE.getDebug().get();

        if (enabledDebug) {
            debugs.add(log);
        }
    }

    public abstract void process(T t);

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public boolean isAutobans() {
        return autobans;
    }

    public void setAutobans(boolean autobans) {
        this.autobans = autobans;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }
}
