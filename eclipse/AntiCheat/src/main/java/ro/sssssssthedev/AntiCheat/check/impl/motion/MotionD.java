package ro.sssssssthedev.AntiCheat.check.impl.motion;

import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInFlying;

public final class MotionD extends PacketCheck {
    private double lastPosX, lastPosZ, lastDeltaXZ, flyingMotionX, flyingMotionZ;
    private double buffer;

    public MotionD(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            final WrappedPacketPlayInFlying wrapper = (WrappedPacketPlayInFlying) packet;

            if (wrapper.isHasPos() && wrapper.isHasLook()) {
                final double posX = wrapper.getX();
                final double posZ = wrapper.getZ();

                final double motionX = Math.abs(posX - lastPosX);
                final double motionZ = Math.abs(posZ - lastPosZ);

                final boolean attacked = playerData.getActionManager().getAttacking().get();

                if (attacked) {
                    final double predictedX = flyingMotionX * 0.6;
                    final double predictedZ = flyingMotionZ * 0.6;

                    final double deltaX = Math.abs(predictedX - motionX);
                    final double deltaZ = Math.abs(predictedZ - motionZ);

                    final double deltaXZ = deltaX + deltaZ;
                    final double acceleration = Math.abs(deltaXZ - lastDeltaXZ);

                    if (acceleration < 0.01) {
                        if (deltaX > 0.089 || deltaZ > 0.089) {
                            if (++buffer > 8) {
                                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                            }
                        } else {
                            buffer = Math.max(buffer - 1, 0);
                        }
                    } else {
                        buffer = Math.max(buffer - 0.5, 0);
                    }

                    this.lastDeltaXZ = deltaXZ;
                }

                this.lastPosX = posX;
                this.lastPosZ = posZ;
                this.flyingMotionX = motionX;
                this.flyingMotionZ = motionZ;
            }
        }
    }
}
