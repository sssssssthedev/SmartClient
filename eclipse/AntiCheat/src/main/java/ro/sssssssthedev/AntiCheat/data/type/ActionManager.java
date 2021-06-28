package ro.sssssssthedev.AntiCheat.data.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ro.sssssssthedev.AntiCheat.data.Observable;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.utils.ReflectionUtil;

@Getter @RequiredArgsConstructor
public final class ActionManager {
    public PlayerData playerData;
    /*
    We are using observables because they are less laggy than normal booleans. (Note: DON'T ABUSE THEM)
     */
    private final Observable<Boolean> attacking = new Observable<>(false);
    private final Observable<Boolean> swinging = new Observable<>(false);
    private final Observable<Boolean> digging = new Observable<>(false);
    private final Observable<Boolean> ground = new Observable<>(false);
    private final Observable<Boolean> teleported = new Observable<>(false);

    private long lastBukkitDig, lastTeleport, lastAttack;

    public void onFlying() {
        final long now = System.currentTimeMillis();

        final boolean digging = now - lastBukkitDig < 100L;
        final boolean ground = ReflectionUtil.onGround(playerData);

        this.attacking.set(false);
        this.swinging.set(false);

        this.ground.set(ground);
        this.digging.set(digging);
    }

    public void onAttack() {
        final long now = System.currentTimeMillis();

        this.attacking.set(true);
        this.lastAttack = now;
    }

    public void onSwing() {
        this.swinging.set(true);
    }

    public void onDig() {
        this.digging.set(true);
    }

    public void onBukkitDig() {
        final long now = System.currentTimeMillis();

        if (now - lastBukkitDig < 100L) {
            this.digging.set(true);
        }

        this.lastBukkitDig = now;
    }

    public void onTeleport() {
        final long now = System.currentTimeMillis();

        this.teleported.set(now - lastTeleport < 100L);

        this.lastTeleport = now;
    }
}
