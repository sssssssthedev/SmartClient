package ro.sssssssthedev.AntiCheat.check.impl.reach;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.alert.type.ViolationLevel;
import ro.sssssssthedev.AntiCheat.check.CheckData;
import ro.sssssssthedev.AntiCheat.check.type.PacketCheck;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacket;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInFlying;
import ro.sssssssthedev.AntiCheat.packet.type.WrappedPacketPlayInUseEntity;
import ro.sssssssthedev.AntiCheat.packet.type.enums.EnumEntityUseAction;
import ro.sssssssthedev.AntiCheat.update.box.PlayerPosition;

@CheckData(name = "Reach")
public final class Reach extends PacketCheck {
    private boolean attacked = false;
    private Player target = null;

    private double buffer;

    public Reach(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInUseEntity) {
            final WrappedPacketPlayInUseEntity wrapper = (WrappedPacketPlayInUseEntity) packet;

            if (wrapper.getUseAction() == EnumEntityUseAction.ATTACK && wrapper.getEntity() != null && wrapper.getEntity() instanceof Player) {
                final Player entityTarget = (Player) wrapper.getEntity();

                this.attacked = true;
                this.target = entityTarget;
            }
        } else if (packet instanceof WrappedPacketPlayInFlying) {
            final boolean valid = attacked && target != null && playerData.getPlayer().getGameMode() != GameMode.CREATIVE;

            if (valid) {
                final PlayerData targetData = AntiCheatAPI.INSTANCE.getPlayerDataManager().getData(target);
                final PlayerPosition playerPosition = playerData.getPlayerPosition();

                synchronized (targetData.getLocations()) {
                    final double reach = Math.sqrt(targetData.getLocations().stream().mapToDouble(d -> d.getDistanceSquared(playerPosition)).min().orElse(0.0));

                    if (reach > 3.1 && reach < 6.f) {
                        if (++buffer > 2) {
                            this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                        }
                    } else {
                        buffer = Math.max(buffer - 0.5, 0);
                    }
                }

                attacked = false;
                target = null;
            }
        }
    }
}
