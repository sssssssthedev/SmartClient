package ro.sssssssthedev.AntiCheat.processor.impl;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.check.type.RotationCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.*;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumEntityUseAction;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumPlayerAction;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;
import ro.sssssssthedev.AntiCheat.update.RotationUpdate;
import us.overflow.anticheat.packet.type.*;
import ro.sssssssthedev.AntiCheat.processor.type.Processor;
import ro.sssssssthedev.AntiCheat.update.box.PlayerPosition;
import ro.sssssssthedev.AntiCheat.update.head.HeadRotation;

public final class PacketProcessor implements Processor<WrappedPacket> {

    @Override
    public void process(final PlayerData playerData, final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            final WrappedPacketPlayInFlying wrapper = (WrappedPacketPlayInFlying) packet;

            final boolean position = wrapper.isHasPos();
            final boolean looked = wrapper.isHasLook();

            if (looked) {
                final float yaw = wrapper.getYaw();
                final float pitch = wrapper.getPitch();

                final float lastYaw = playerData.getLastYaw();
                final float lastPitch = playerData.getLastPitch();

                final HeadRotation from = new HeadRotation(lastYaw, lastPitch);
                final HeadRotation to = new HeadRotation(yaw, pitch);

                final RotationUpdate rotationUpdate = new RotationUpdate(from, to);

                //noinspection unchecked
                playerData.getCheckManager().getChecks().stream().filter(RotationCheck.class::isInstance).forEach(check -> check.process(rotationUpdate));

                playerData.setLastYaw(yaw);
                playerData.setLastPitch(pitch);
            }

            if (position) {
                final double posX = wrapper.getX();
                final double posZ = wrapper.getZ();

                final PlayerPosition playerPosition = new PlayerPosition(posX, posZ);

                playerData.getLocations().add(playerPosition);
                playerData.setPlayerPosition(playerPosition);
                playerData.setStandTicks(0);
            } else {
                final int standTicks = playerData.getStandTicks();

                playerData.setStandTicks(standTicks + 1);
            }

            playerData.setClientTicks(playerData.getClientTicks() + 1);
            playerData.getActionManager().onFlying();

            this.handleMoveEvent(playerData, wrapper);
        } else if (packet instanceof WrappedPacketPlayInUseEntity) {
            final WrappedPacketPlayInUseEntity wrapper = (WrappedPacketPlayInUseEntity) packet;

            if (wrapper.getUseAction() == EnumEntityUseAction.ATTACK) {
                playerData.getActionManager().onAttack();
            }
        } else if (packet instanceof WrappedPacketPlayInBlockDig) {
            final WrappedPacketPlayInBlockDig wrapper = (WrappedPacketPlayInBlockDig) packet;

            switch (wrapper.getDigType()) {
                case START_DESTROY_BLOCK:
                case ABORT_DESTROY_BLOCK:
                case STOP_DESTROY_BLOCK: {
                    playerData.getActionManager().onDig();
                }
            }
        } else if (packet instanceof WrappedPacketPlayOutEntityVelocity) {
            final WrappedPacketPlayOutEntityVelocity wrapper = (WrappedPacketPlayOutEntityVelocity) packet;

            if (wrapper.getEntityId() == playerData.getPlayer().getEntityId()) {
                final double posX = wrapper.getX();
                final double posY = wrapper.getY();
                final double posZ = wrapper.getZ();

                playerData.setVelocityX(posX);
                playerData.setVelocityY(posY);
                playerData.setVelocityZ(posZ);

                playerData.getVelocity().set(true);
                playerData.getVelocityManager().addVelocityEntry(posX, posY, posZ);
            }
        } else if (packet instanceof WrappedPacketPlayInArmAnimation) {
            playerData.getActionManager().onSwing();
        } else if (packet instanceof WrappedPacketPlayOutPosition) {
            playerData.getActionManager().onTeleport();
        } else  if (packet instanceof WrappedPacketPlayInEntityAction) {
            final WrappedPacketPlayInEntityAction wrapper = (WrappedPacketPlayInEntityAction) packet;

            final boolean sprinting = wrapper.getUseAction() == EnumPlayerAction.START_SPRINTING;

            playerData.getSprinting().set(sprinting);
        }

        //noinspection unchecked
        playerData.getCheckManager().getChecks().stream().filter(PacketCheck.class::isInstance).forEach(check -> check.process(packet));
    }

    private void handleMoveEvent(final PlayerData playerData, WrappedPacketPlayInFlying wrapper) {
        final boolean checkMovement = wrapper.isCheckMovement();
        final boolean position = wrapper.isHasPos();

        if (position && checkMovement) {
            final Player player = playerData.getPlayer();

            final double posX = wrapper.getX();
            final double posY = wrapper.getY();
            final double posZ = wrapper.getZ();

            final double lastPosX = playerData.getLastPosX();
            final double lastPosY = playerData.getLastPosY();
            final double lastPosZ = playerData.getLastPosZ();

            final Location to = new Location(player.getWorld(), posX, posY, posZ);
            final Location from = new Location(player.getWorld(), lastPosX, lastPosY, lastPosZ);

            final PositionUpdate positionUpdate = new PositionUpdate(from, to);

            AntiCheatAPI.INSTANCE.getProcessorManager().getProcessor(MovementProcessor.class).process(playerData, positionUpdate);

            playerData.setLastPosX(posX);
            playerData.setLastPosY(posY);
            playerData.setLastPosZ(posZ);
        }
    }
}
