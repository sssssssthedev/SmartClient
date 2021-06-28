package ro.sssssssthedev.AntiCheat.check.impl.flight;

import org.bukkit.GameMode;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInFlying;
import ro.sssssssthedev.AntiCheat.utils.BlockUtil;

@CheckData(name = "Flight (F)")
public final class FlightF extends PacketCheck {

    private double lastY;

    private int airTicks, stairTicks, boatTicks, slimeTicks;

    private long lastBoat;

    public FlightF(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            WrappedPacketPlayInFlying wrappedPacketPlayInFlying = (WrappedPacketPlayInFlying) packet;

            if (wrappedPacketPlayInFlying.isHasPos() && (System.currentTimeMillis() - playerData.getLastJoin()) > 1000L) {

                if (getPlayerData().getPositionManager().getTouchingSlime().get()) {
                    if (slimeTicks < 100) slimeTicks+=20;
                } else {
                    if (slimeTicks > 0) slimeTicks--;
                }

                if (getPlayerData().getPlayer().getVehicle() != null) {
                    if (boatTicks < 20) boatTicks++;
                    lastBoat = System.currentTimeMillis();
                } else {
                    if (boatTicks > 0) boatTicks--;
                }

                if (getPlayerData().getPositionManager().getTouchingStair().get() || getPlayerData().getPositionManager().getTouchingSlab().get() || getPlayerData().getPositionManager().getTouchingHalfBlocks().get()) {
                    if (stairTicks < 20) stairTicks += 20;
                } else {
                    if (stairTicks > 0) stairTicks--;
                }

                if (slimeTicks > 0 || (System.currentTimeMillis() - lastBoat) < 1000L || boatTicks > 0 || stairTicks > 0 || getPlayerData().getClientTicks() < 100 || playerData.getPlayer().isFlying() || playerData.getPlayer().getAllowFlight() || playerData.getPlayer().getGameMode() == GameMode.CREATIVE) {
                    airTicks = 0;
                    return;
                }

                boolean yGround = wrappedPacketPlayInFlying.getY() % 0.015625 == 0.0;

                boolean clientGround = wrappedPacketPlayInFlying.isOnGround();

                if (!clientGround) {
                    if (airTicks < 20) airTicks++;
                } else {
                    airTicks = 0;
                }

                double diff = (lastY - wrappedPacketPlayInFlying.getY());


                if (!clientGround && Math.abs(diff) < 0.0009f && airTicks > 15 && !playerData.getPositionManager().getBelowBlocks().get() && !playerData.getPositionManager().getTouchingLiquid().get() && !playerData.getPositionManager().getTouchingHalfBlocks().get() && !playerData.getPositionManager().getTouchingSlime().get() && !playerData.getPositionManager().getTouchingClimbable().get()) {
            //        this.handleViolation().addViolation(ViolationLevel.LOW).create();
                }

                if ((yGround && !clientGround) && !playerData.getPositionManager().getTouchingSlab().get() && !BlockUtil.isSlab(playerData.getPlayer()) && !playerData.getPositionManager().getBelowBlocks().get() && !playerData.getPositionManager().getTouchingLiquid().get() && !playerData.getPositionManager().getTouchingHalfBlocks().get() && !playerData.getPositionManager().getTouchingSlime().get() && !playerData.getPositionManager().getTouchingClimbable().get()) {
          //          this.handleViolation().addViolation(ViolationLevel.LOW).create();
                }

                lastY = wrappedPacketPlayInFlying.getY();
            }
        }
    }
}
