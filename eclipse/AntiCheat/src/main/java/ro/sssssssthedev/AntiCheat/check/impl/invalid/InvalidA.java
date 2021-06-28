package ro.sssssssthedev.AntiCheat.check.impl.invalid;

import org.bukkit.Location;
import org.bukkit.Material;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInFlying;
import ro.sssssssthedev.AntiCheat.utils.Cuboid;
import ro.sssssssthedev.AntiCheat.utils.MathUtil;

@CheckData(name = "Invalid (A)")
public final class InvalidA extends PacketCheck {
    private int standTicks;
    private Location lastLocation = null;

    public InvalidA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            if (lastLocation != null && !playerData.getPositionManager().getTouchingClimbable().get()) {
                final WrappedPacketPlayInFlying wrapper = (WrappedPacketPlayInFlying) packet;

                if (wrapper.isHasPos()) {
                    standTicks = 0;

                    final Location playerLocation = playerData.getPlayer().getLocation();

                    if (playerLocation != null && lastLocation != null) {
                        final double horizontalDistance = MathUtil.vectorDistance(playerLocation, lastLocation);
                        final double velocityDistance = playerData.getVelocityManager().getMaxVertical() + playerData.getVelocityManager().getMaxHorizontal();

                        final boolean horizontallyAir = new Cuboid(playerLocation).expand(1.0, 0.0, 1.0).checkBlocks(playerLocation.getWorld(), material -> material == Material.AIR) && !playerData.getPositionManager().getTouchingClimbable().get();

                        if (horizontallyAir && horizontalDistance > 0.0 && horizontalDistance < 0.00089 && velocityDistance == 0.0 && standTicks == 0.0) {
                            //this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                        }
                    }

                    this.lastLocation = playerLocation;
                } else {
                    final boolean invalid = standTicks > 20;

                    if (invalid) {
                        this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                    }
                }
            } else {
                lastLocation = new Location(playerData.getPlayer().getWorld(), 0, 0, 0);
            }
        }
    }
}
