package ro.sssssssthedev.AntiCheat.check.impl.speed;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PositionCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;
import ro.sssssssthedev.AntiCheat.utils.ReflectionUtil;

@CheckData(name = "Speed (A)")
public final class SpeedA extends PositionCheck {
    private double previousHorizontal, blockSlipperiness;

    public SpeedA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final double deltaY = to.getY() - from.getY();

        double horizontalSpeed = 1.f;
        double blockSlipperiness = this.blockSlipperiness;

        final boolean onGround = ReflectionUtil.onGround(playerData);
        final double speedLimit = playerData.getPositionManager().getBelowBlocks().get() ? 3.6 : 1.0f;

        if (onGround) {
            blockSlipperiness *= 0.91f;

            horizontalSpeed *= blockSlipperiness > 0.708 ? 1.3 : 0.23315;
            horizontalSpeed *= 0.16277136 / Math.pow(blockSlipperiness, 3);

            if (deltaY > 0.4199) {
                final double var1 = to.getYaw() * 0.017453292F;
                final double extraSpeedJumpX = Math.sin(var1) * 0.2F;
                final double extraSpeedJumpZ = Math.cos(var1) * 0.2F;

                final double extraJumpSpeed = Math.sqrt(Math.pow(extraSpeedJumpX, 2) + Math.pow(extraSpeedJumpZ, 2));

                horizontalSpeed += extraJumpSpeed;
            } else {
                horizontalSpeed -= .1053;
            }
        } else {
            horizontalSpeed = 0.026;
            blockSlipperiness = 0.91f;
        }

        final double deltaX = to.getX() - from.getX();
        final double deltaZ = to.getZ() - from.getZ();

        final double previousHorizontal = this.previousHorizontal;
        final double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        if (horizontalDistance > 0.27 && horizontalDistance - previousHorizontal > horizontalSpeed * 0.99) {
            if (horizontalDistance - previousHorizontal > horizontalSpeed) {
                horizontalSpeed += playerData.getVelocityManager().getMaxHorizontal();

                double moveSpeed = (horizontalDistance - previousHorizontal) / horizontalSpeed;
                moveSpeed *= 0.98;

                if (playerData.getPlayer().hasPotionEffect(PotionEffectType.SPEED)) {
                    moveSpeed -= (this.getSpeedBoostAmplifier(playerData.getPlayer()) + 1) * .17; //from minecraft src
                }

                moveSpeed -= Math.sqrt(playerData.getVelocityX() * playerData.getVelocityX() + playerData.getVelocityZ() * playerData.getVelocityZ());

          //      Bukkit.broadcastMessage(""+moveSpeed + " " + speedLimit);

                if (moveSpeed > speedLimit) {
                    //fix idk whats wrong just falses when moving
                  //  this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }
            }
        }

        this.previousHorizontal = horizontalDistance * blockSlipperiness;
        this.blockSlipperiness = this.getBlockSlipperiness(to);
    }

    private double getBlockSlipperiness(final Location to) {
        //Refer to Block.class#195. Friction factor is 0.6 unless otherwise said by slippery objects such as ice, or bouncing objects such as slimes.
        return (to.getWorld().getBlockAt(to.clone().subtract(0.0, 1.0, 0.0)).getType()) == Material.PACKED_ICE
                || to.getWorld().getBlockAt(to.clone().subtract(0.0, 1.0, 0.0)).getType()  == Material.ICE ? 0.9800000190734863
                : (to.getWorld().getBlockAt(to.clone().subtract(0.0, 1.0, 0.0)).getType()).name().equals("SLIME_BLOCK") ? 0.800000011920929
                : 0.6000000238418579;
    }

    private int getSpeedBoostAmplifier(final Player player) {
        if (player.hasPotionEffect(PotionEffectType.SPEED)) {
            return player.getActivePotionEffects().stream().filter(effect -> effect.getType().equals(PotionEffectType.SPEED)).findFirst().map(effect -> effect.getAmplifier() + 1).orElse(0);
        }
        return 0;
    }
}
