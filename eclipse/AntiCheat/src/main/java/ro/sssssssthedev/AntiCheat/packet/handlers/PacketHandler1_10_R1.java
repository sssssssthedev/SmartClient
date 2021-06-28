package ro.sssssssthedev.AntiCheat.packet.handlers;

import net.minecraft.server.v1_10_R1.*;
import org.bukkit.entity.Entity;
import ro.sssssssthedev.AntiCheat.packet.type.*;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import us.overflow.anticheat.packet.type.*;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumEntityUseAction;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumPlayerAction;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumPlayerDigType;
import ro.sssssssthedev.AntiCheat.utils.ReflectionUtil;

public final class PacketHandler1_10_R1 extends PlayerConnection {
    private final PlayerData playerData;

    public PacketHandler1_10_R1(final MinecraftServer minecraftserver, final NetworkManager networkmanager, final EntityPlayer entityplayer, final PlayerData playerData) {
        super(minecraftserver, networkmanager, entityplayer);

        this.playerData = playerData;
    }

    @Override
    public void a(final PacketPlayInFlying packet) {
        super.a(packet);

        final Class clazz = PacketPlayInFlying.class;

        final double posX = ReflectionUtil.getFieldValue(clazz, "x", double.class, packet);
        final double posY = ReflectionUtil.getFieldValue(clazz, "y", double.class, packet);
        final double posZ = ReflectionUtil.getFieldValue(clazz, "z", double.class, packet);

        final float yaw = ReflectionUtil.getFieldValue(clazz, "yaw", float.class, packet);
        final float pitch = ReflectionUtil.getFieldValue(clazz, "pitch", float.class, packet);

        final boolean look = ReflectionUtil.getFieldValue(clazz, "hasLook", boolean.class, packet);
        final boolean position = ReflectionUtil.getFieldValue(clazz, "hasPos", boolean.class, packet);
        final boolean ground = ReflectionUtil.getFieldValue(clazz, "f", boolean.class, packet);
        final boolean checkMovement = ReflectionUtil.getFieldValue(PacketPlayInFlying.class, "hasMoved", boolean.class, packet);

        final WrappedPacketPlayInFlying wrapper = new WrappedPacketPlayInFlying(posX, posY, posZ, position, look, ground, checkMovement, yaw, pitch);

        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInUseEntity packet) {
        super.a(packet);

        EnumEntityUseAction entityUseAction = EnumEntityUseAction.NONE;
        WrappedPacketPlayInUseEntity wrapper = new WrappedPacketPlayInUseEntity();

        switch (packet.a()) {
            case ATTACK: {
                entityUseAction = EnumEntityUseAction.ATTACK;
                final Entity entity = packet.a(player.world).getBukkitEntity();

                wrapper.setEntity(entity);
                break;
            }

            case INTERACT_AT: {
                entityUseAction = EnumEntityUseAction.INTERACT_AT;
                break;
            }

            case INTERACT: {
                entityUseAction = EnumEntityUseAction.INTERACT;
                break;
            }
        }

        wrapper.setUseAction(entityUseAction);
        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInArmAnimation packet) {
        super.a(packet);

        final long timestamp = System.currentTimeMillis();

        final WrappedPacketPlayInArmAnimation wrapper = new WrappedPacketPlayInArmAnimation(timestamp);

        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInEntityAction packet) {
        super.a(packet);

        EnumPlayerAction enumPlayerAction = EnumPlayerAction.NONE;

        switch (packet.b()) {
            case START_SNEAKING: {
                enumPlayerAction = EnumPlayerAction.START_SNEAKING;
                break;
            }

            case STOP_SNEAKING: {
                enumPlayerAction = EnumPlayerAction.STOP_SNEAKING;
                break;
            }

            case START_SPRINTING: {
                enumPlayerAction = EnumPlayerAction.START_SPRINTING;
                break;
            }

            case STOP_SPRINTING: {
                enumPlayerAction = EnumPlayerAction.STOP_SPRINTING;
                break;
            }
        }

        final WrappedPacketPlayInEntityAction wrapper = new WrappedPacketPlayInEntityAction();

        wrapper.setUseAction(enumPlayerAction);
        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInAbilities packet) {
        super.a(packet);

        final boolean flying = packet.isFlying();
        final boolean canFly = packet.c();

        final WrappedPacketPlayInAbilities wrapper = new WrappedPacketPlayInAbilities(flying, canFly);

        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInTransaction packet) {
        super.a(packet);

        final short id = packet.b();
        final int data = packet.a();

        final WrappedPacketPlayInTransaction wrapper = new WrappedPacketPlayInTransaction(id, data);

        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInBlockDig packet) {
        super.a(packet);

        EnumPlayerDigType enumPlayerDigType = EnumPlayerDigType.NONE;

        switch (packet.c()) {
            case START_DESTROY_BLOCK: {
                enumPlayerDigType = EnumPlayerDigType.START_DESTROY_BLOCK;
                break;
            }

            case STOP_DESTROY_BLOCK: {
                enumPlayerDigType = EnumPlayerDigType.STOP_DESTROY_BLOCK;
                break;
            }

            case ABORT_DESTROY_BLOCK: {
                enumPlayerDigType = EnumPlayerDigType.ABORT_DESTROY_BLOCK;
                break;
            }

            case DROP_ALL_ITEMS: {
                enumPlayerDigType = EnumPlayerDigType.DROP_ALL_ITEMS;
                break;
            }

            case DROP_ITEM: {
                enumPlayerDigType = EnumPlayerDigType.DROP_ITEM;
                break;
            }

            case RELEASE_USE_ITEM: {
                enumPlayerDigType = EnumPlayerDigType.RELEASE_USE_ITEM;
                break;
            }
        }

        final WrappedPacketPlayInBlockDig wrapper = new WrappedPacketPlayInBlockDig(enumPlayerDigType);

        wrapper.parse(playerData);
    }


    @Override
    public void a(final PacketPlayInHeldItemSlot packet) {
        super.a(packet);

        final int slot = packet.a();

        final WrappedPacketPlayInHeldItemSlot wrapper = new WrappedPacketPlayInHeldItemSlot(slot);

        wrapper.parse(playerData);
    }

    @Override
    public void a(PacketPlayInBlockPlace packet) {
        super.a(packet);

        final int face = 0;

        final WrappedPacketPlayInBlockPlace wrapper = new WrappedPacketPlayInBlockPlace(face);

        wrapper.parse(playerData);
    }

    @Override
    public void a(PacketPlayInSteerVehicle packet) {
        super.a(packet);

        final WrappedPacketPlayInSteerVehicle wrapper = new WrappedPacketPlayInSteerVehicle();

        wrapper.parse(playerData);
    }

    @Override
    public void sendPacket(final Packet packet) {
        super.sendPacket(packet);

        if (packet instanceof PacketPlayOutEntityVelocity) {
            final Class clazz = PacketPlayOutEntityVelocity.class;

            final int entityId = ReflectionUtil.getFieldValue(clazz, "a", int.class, packet);

            final double posX = Math.abs(ReflectionUtil.getFieldValue(clazz, "b", int.class, packet)) / 8000.0;
            final double posY = ((int) ReflectionUtil.getFieldValue(clazz, "c", int.class, packet)) / 8000.0;
            final double posZ = Math.abs(ReflectionUtil.getFieldValue(clazz, "d", int.class, packet)) / 8000.0;

            final WrappedPacketPlayOutEntityVelocity wrapper = new WrappedPacketPlayOutEntityVelocity(entityId, posX, posY, posZ);

            wrapper.parse(playerData);
        } else if (packet instanceof PacketPlayOutPosition) {
            final Class clazz = PacketPlayOutPosition.class;

            final double posX = (double) ReflectionUtil.getFieldValue(clazz, "a", double.class, packet) / 32;
            final double posY = (double) ReflectionUtil.getFieldValue(clazz, "b", double.class, packet) / 32;
            final double posZ = (double) ReflectionUtil.getFieldValue(clazz, "c", double.class, packet) / 32;

            final WrappedPacketPlayOutPosition wrapper = new WrappedPacketPlayOutPosition(posX, posY, posZ);

            wrapper.parse(playerData);
        }
    }
}
