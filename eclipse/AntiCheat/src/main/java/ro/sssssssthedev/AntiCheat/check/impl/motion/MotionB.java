package ro.sssssssthedev.AntiCheat.check.impl.motion;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffectType;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PositionCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;
import ro.sssssssthedev.AntiCheat.utils.ReflectionUtil;

@CheckData(name = "Motion (B)")
public final class MotionB extends PositionCheck {

    public MotionB(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final double deltaY = to.getY() - from.getY();
        final double motionY = ReflectionUtil.getMotionY(playerData);

        if (getPlayerData().getPositionManager().getTouchingSlab().get() || getPlayerData().getPositionManager().getTouchingStair().get() || getPlayerData().getPositionManager().getTouchingHalfBlocks().get() || (getPlayerData().getClientTicks()) < 100 || playerData.getPlayer().hasPotionEffect(PotionEffectType.JUMP) || playerData.getPositionManager().getTouchingIllegalBlocks().get() || playerData.getVelocityManager().getMaxVertical() > 0.0 || playerData.getVelocityManager().getMaxHorizontal() > 0.0) {
            return;
        }

        if (deltaY > 0.5 && motionY <= 0.0) {
          //  this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
        }
    }
}

