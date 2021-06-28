package ro.sssssssthedev.AntiCheat.check.impl.scaffold;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInBlockPlace;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInFlying;
import ro.sssssssthedev.AntiCheat.utils.ReflectionUtil;

@CheckData(name = "Scaffold (C)")
public final class ScaffoldC extends PacketCheck {
    private boolean placed;
    private double lastPosX, lastPosZ;
    private float lastYaw, lastPitch;
    private double buffer;

    public ScaffoldC(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            final WrappedPacketPlayInFlying wrapper = (WrappedPacketPlayInFlying) packet;

            if (wrapper.isHasPos() && wrapper.isHasLook()) {
                final double posX = wrapper.getX();
                final double posZ = wrapper.getZ();

                final double deltaX = posX - lastPosX;
                final double deltaZ = posZ - lastPosZ;

                final float yaw = wrapper.getYaw();
                final float pitch = wrapper.getPitch();

                if (placed) {
                    final double deltaXZ = Math.hypot(deltaX, deltaZ);
                    final double motionY = ReflectionUtil.getMotionY(playerData);

                    if (deltaXZ > 0.19 && motionY == 0.0) {
                        final float deltaYaw = Math.abs(yaw - lastYaw);
                        final float deltaPitch = Math.abs(pitch - lastPitch);

                        if (deltaYaw > 30.f && deltaPitch > 5.f) {
                            if (++buffer > 1) {
                                this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                            }
                        } else {
                            buffer = Math.max(buffer - 0.25, 0);
                        }
                    }
                }

                this.lastYaw = yaw;
                this.lastPitch = pitch;
                this.lastPosX = posX;
                this.lastPosZ = posZ;
            }

            placed = false;
        } else if (packet instanceof WrappedPacketPlayInBlockPlace) {
            placed = true;
        }
    }
}
