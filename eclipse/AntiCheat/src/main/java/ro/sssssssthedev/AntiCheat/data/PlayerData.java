package ro.sssssssthedev.AntiCheat.data;

import com.google.common.util.concurrent.AtomicDoubleArray;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ro.sssssssthedev.AntiCheat.data.type.*;
import ro.sssssssthedev.AntiCheat.utils.EvictingList;
import ro.sssssssthedev.AntiCheat.check.impl.aimassist.prediction.Prediction;
import ro.sssssssthedev.AntiCheat.data.type.*;
import ro.sssssssthedev.AntiCheat.update.box.PlayerPosition;

import java.util.List;

@Getter @Setter
public final class PlayerData {
    private Player player;

    @Setter(AccessLevel.NONE)
    private CheckManager checkManager = new CheckManager(this);

    @Setter(AccessLevel.NONE)
    private ActionManager actionManager = new ActionManager();

    @Setter(AccessLevel.NONE)
    private final ConnectionManager connectionManager = new ConnectionManager();

    @Setter(AccessLevel.NONE)
    private final VelocityManager velocityManager = new VelocityManager();

    @Setter(AccessLevel.NONE)
    private final PositionManager positionManager = new PositionManager();

    private Observable<Boolean> alerts = new Observable<>(true);
    private Observable<Boolean> cinematic = new Observable<>(false);
    private final Observable<Boolean> sprinting =  new Observable<>(false);
    private final Observable<Boolean> velocity = new Observable<>(false);

    private final EvictingList<PlayerPosition> locations = new EvictingList<>(10);

    private PlayerPosition playerPosition = new PlayerPosition(0, 0);

    private int standTicks, clientTicks;
    private float lastYaw, lastPitch;

    private double velocityX;
    private double velocityY;
    private double velocityZ;

    private double lastPosX, lastPosY, lastPosZ;

    private double sensitivityX = checkManager.getCheck(Prediction.class).sensitivityX;
    private double sensitivityY = checkManager.getCheck(Prediction.class).sensitivityY;

    private double mouseDeltaX = checkManager.getCheck(Prediction.class).deltaX;
    private double mouseDeltaY = checkManager.getCheck(Prediction.class).deltaY;

    private long lastJoin, lastAttackDamage, lastFallDamage, lastTeleport, lastUnknownTeleport;

    public PlayerData(final Player player) {
        this.player = player;
        this.lastJoin = System.currentTimeMillis();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Observable<Boolean> getCinematic() {
        return cinematic;
    }

    public void setCinematic(Observable<Boolean> cinematic) {
        this.cinematic = cinematic;
    }

    public Observable<Boolean> getAlerts() {
        return alerts;
    }

    public void setAlerts(Observable<Boolean> alerts) {
        this.alerts = alerts;
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }

    public void setCheckManager(CheckManager checkManager) {
        this.checkManager = checkManager;
    }

    public void setLastTeleport(long lastTeleport) {
        this.lastTeleport = lastTeleport;
    }

    public long getLastTeleport() {
        return lastTeleport;
    }

    public void setLastUnknownTeleport(long lastUnknownTeleport) {
        this.lastUnknownTeleport = lastUnknownTeleport;
    }

    public long getLastUnknownTeleport() {
        return lastUnknownTeleport;
    }

    public void setLastAttackDamage(long lastAttackDamage) {
        this.lastAttackDamage = lastAttackDamage;
    }

    public long getLastAttackDamage() {
        return lastAttackDamage;
    }

    public void setLastFallDamage(long lastFallDamage) {
        this.lastFallDamage = lastFallDamage;
    }

    public long getLastFallDamage() {
        return lastFallDamage;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public void setActionManager(ActionManager actionManager) {
        this.actionManager = actionManager;
    }
}
