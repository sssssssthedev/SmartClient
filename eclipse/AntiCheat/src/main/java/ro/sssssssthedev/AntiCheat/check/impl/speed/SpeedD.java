package ro.sssssssthedev.AntiCheat.check.impl.speed;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInFlying;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayOutEntityVelocity;
import ro.sssssssthedev.AntiCheat.utils.CustomLocation;

@CheckData(name = "Speed (D)")
public final class SpeedD extends PacketCheck {

    public SpeedD(final PlayerData playerData) {
        super(playerData);
    }

    /*
        Ripped out of sparky so ignore the mess, this is a replacement for speed A as it loves to false flag
     */

    private double lastX, lastZ, movementSpeed, lastBlockY, offset = Math.pow(0.984, 9);

    private int verbose, airTicks, groundTicks, slabTicks, stairTicks, iceTicks, blockAboveTicks, speedPotionTicks, slimeTicks, invalidJumpPadTicks;

    private long lastBlockJump, lastIce, lastJumpadSet, lastSlime;

    private CustomLocation to, from;

    private boolean jumpPad, clientGround;

    @Override
    public void process(WrappedPacket packet) {

        if (playerData.getPlayer().getAllowFlight()) return;

        if (packet instanceof WrappedPacketPlayOutEntityVelocity) {
            WrappedPacketPlayOutEntityVelocity wrappedPacketPlayOutEntityVelocity = (WrappedPacketPlayOutEntityVelocity) packet;
            if (wrappedPacketPlayOutEntityVelocity.getEntityId() == getPlayerData().getPlayer().getEntityId()) {
                if (!this.jumpPad && (System.currentTimeMillis() - getPlayerData().getLastFallDamage()) > 1000L && this.clientGround && (System.currentTimeMillis() - playerData.getLastAttackDamage()) > 20L) {
                    this.jumpPad = true;
                    this.lastJumpadSet = System.currentTimeMillis();
                }
            }
        }

        if (packet instanceof WrappedPacketPlayInFlying) {
            WrappedPacketPlayInFlying wrappedPacketPlayInFlying = (WrappedPacketPlayInFlying) packet;

            if (wrappedPacketPlayInFlying.isHasPos()) {

                if (this.jumpPad && this.to != null && this.from != null) {

                    if (!getPlayerData().getPositionManager().getTouchingAir().get() && !this.clientGround && Math.abs(this.to.getY() - this.from.getY()) == 0.0f) {

                        if (this.invalidJumpPadTicks > 30) {
                            this.jumpPad = false;
                            this.invalidJumpPadTicks = 0;
                        }

                        this.invalidJumpPadTicks++;
                    } else {
                        this.invalidJumpPadTicks = 0;
                    }

                    if ((System.currentTimeMillis() - this.lastJumpadSet) > 1000L && this.clientGround) {
                        this.jumpPad = false;
                    }
                }

                double x = wrappedPacketPlayInFlying.getX();
                double z = wrappedPacketPlayInFlying.getZ();

                CustomLocation location = new CustomLocation(wrappedPacketPlayInFlying.getX(), wrappedPacketPlayInFlying.getY(), wrappedPacketPlayInFlying.getZ());


                //Using packet even because its better, fuck you

                this.doCalulcations(location);


                this.clientGround = wrappedPacketPlayInFlying.isOnGround();
                this.from = this.to;
                this.to = location;

                this.movementSpeed = getSpeed(x, z);
                this.lastX = x;
                this.lastZ = z;
            }
        }
    }

    private void doCalulcations(CustomLocation location) {
        //Fucking cancer.

        boolean ground = !getPlayerData().getPositionManager().getTouchingAir().get();

        boolean stair = playerData.getPositionManager().getTouchingStair().get();

        boolean slab = playerData.getPositionManager().getTouchingStair().get();

        boolean ice = playerData.getPositionManager().getTouchingIce().get();

        boolean blockAbove = playerData.getPositionManager().getBelowBlocks().get();

        boolean slime = playerData.getPositionManager().getTouchingSlime().get();

        boolean hasSpeed = getPlayerData().getPlayer().hasPotionEffect(PotionEffectType.SPEED);

        if (location != null && this.to != null && this.from != null) {
            Location playerLocation = getPlayerData().getPlayer().getLocation();

            double current = playerLocation.clone().add(0, -1, 0).getBlockY();

            if (ground) {

                if ((current - this.lastBlockY) > 0.0) {
                    this.lastBlockJump = System.currentTimeMillis();
                }

                this.lastBlockY = playerLocation.clone().add(0, -1, 0).getBlockY();
            }
        }

        if (stair) {
            if (stairTicks < 20) stairTicks++;
        } else {
            if (stairTicks > 0) stairTicks--;
        }

        if (slab) {
            if (slabTicks < 20) slabTicks++;
        } else {
            if (slabTicks > 0) slabTicks--;
        }

        if (slime) {
            if (slimeTicks < 100) slabTicks+=20;
        } else {
            if (slimeTicks > 0) slimeTicks--;
        }

        if (slimeTicks > 0) lastSlime = System.currentTimeMillis();

        if (ice) {
            if (iceTicks < 20) iceTicks++;
        } else {
            if (iceTicks > 0) iceTicks--;
        }

        if (iceTicks > 0) {
            this.lastIce = System.currentTimeMillis();
        }


        if (hasSpeed) {
            if (speedPotionTicks < 20) speedPotionTicks++;
        } else {
            if (speedPotionTicks > 0) speedPotionTicks--;
        }

        if (blockAbove) {
            if (blockAboveTicks < 20) blockAboveTicks++;
        } else {
            if (blockAboveTicks > 0) blockAboveTicks--;
        }


        if (ground) {
            if (groundTicks < 20) groundTicks++;
            airTicks = 0;
        } else {
            if (airTicks < 20) airTicks++;
            groundTicks = 0;
        }

        this.doCheck();
    }

    private void doCheck() {

        float threshold = (float) (this.airTicks > 0 ? 0.4163 * this.offset : this.groundTicks > 24 ? 0.291 : 0.375);

        if (slabTicks > 0 || stairTicks > 0) {
            threshold += .3f;
        }

        if (blockAboveTicks > 0 && iceTicks < 1) {
            threshold += .4f;
        }

        if (iceTicks > 0 && blockAboveTicks > 0) {
            threshold += 1.1f;
        }

        if ((System.currentTimeMillis() - getPlayerData().getLastAttackDamage()) < 1000L || (System.currentTimeMillis() - this.lastBlockJump) < 1000L) threshold += .8f;

       if (speedPotionTicks > 0) threshold += getPotionEffectLevel() * 0.2;

       if (slimeTicks > 0 || (System.currentTimeMillis() - lastSlime) < 1000L || this.jumpPad || (!getPlayerData().getPlayer().hasPotionEffect(PotionEffectType.SPEED) && speedPotionTicks > 0) || this.slimeTicks > 0 || this.iceTicks > 0 || (System.currentTimeMillis() - this.lastIce) < 1000L) {
           this.verbose = 0;
           return;
       }

        if (this.movementSpeed > threshold) {
            if (this.verbose++ > 2) {
                this.verbose = 0;
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        } else {
            this.verbose -= (this.verbose > 0 ? 1 : 0);
        }
    }

    private int getPotionEffectLevel() {
        for (PotionEffect pe : getPlayerData().getPlayer().getActivePotionEffects()) {
            if (pe.getType().getName().equalsIgnoreCase(PotionEffectType.SPEED.getName())) {
                return pe.getAmplifier() + 1;
            }
        }
        return 0;
    }

    private double getSpeed(double cx, double cz) {
        double x = Math.abs(Math.abs(cx) - Math.abs(this.lastX));
        double z = Math.abs(Math.abs(cz) - Math.abs(this.lastZ));
        return Math.sqrt(x * x + z * z);
    }
}
