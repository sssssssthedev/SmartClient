
package ro.sssssssthedev.AntiCheat.check.impl.speed;

import org.bukkit.potion.PotionEffectType;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInFlying;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayOutPosition;
import ro.sssssssthedev.AntiCheat.utils.CustomLocation;

@CheckData(name = "Speed (E)")
public final class SpeedE extends PacketCheck {

	public SpeedE(final PlayerData playerData) {
		super(playerData);
	}

	private double lastX, lastZ, movementSpeed;

	private CustomLocation to;

	private boolean jumpPad, clientGround;

	private int vl, tpTicks;

	@Override
	public void process(WrappedPacket packet) {

		if (playerData.getPlayer().getAllowFlight())
			return;

		if (packet instanceof WrappedPacketPlayOutPosition) {
			tpTicks = 20;
		}

		if (packet instanceof WrappedPacketPlayInFlying) {
			WrappedPacketPlayInFlying wrappedPacketPlayInFlying = (WrappedPacketPlayInFlying) packet;
			if (!playerData.getPlayer().hasPotionEffect(PotionEffectType.SPEED)) {
				if (wrappedPacketPlayInFlying.isHasPos()) {

					if (tpTicks > 0) {
						tpTicks--;
					}

					double x = wrappedPacketPlayInFlying.getX();
					double z = wrappedPacketPlayInFlying.getZ();

					CustomLocation location = new CustomLocation(wrappedPacketPlayInFlying.getX(),
							wrappedPacketPlayInFlying.getY(), wrappedPacketPlayInFlying.getZ());

					// Using packet even because its better, fuck you

					this.clientGround = wrappedPacketPlayInFlying.isOnGround();
					this.to = location;

					this.movementSpeed = getSpeed(x, z);
					this.lastX = x;
					this.lastZ = z;

					this.doCheck();
				}
			}
		}
	}

	private void doCheck() {
		if (this.movementSpeed > .622 && tpTicks < 1) {
			this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
		}
	}

	private double getSpeed(double cx, double cz) {
		double x = Math.abs(Math.abs(cx) - Math.abs(this.lastX));
		double z = Math.abs(Math.abs(cz) - Math.abs(this.lastZ));
		return Math.sqrt(x * x + z * z);
	}
}