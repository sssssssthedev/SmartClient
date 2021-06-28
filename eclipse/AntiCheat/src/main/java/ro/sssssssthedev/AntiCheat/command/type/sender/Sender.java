package ro.sssssssthedev.AntiCheat.command.type.sender;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.UUID;

public final class Sender implements CommandSender {
    
    private CommandSender sender;
    
    public Sender(CommandSender sender) {
        this.sender = sender;
    }
    
    public CommandSender getSender() {
        return sender;
    }
    
    public boolean isPlayer() {
        return sender instanceof Player;
    }
    
    public Player castPlayer() {
        if (isPlayer())
            return (Player) sender;
        return null;
    }
    
    public void refresh(CommandSender sender) {
        this.sender = sender;
    }
    
    public UUID getUniqueId() {
        return isPlayer() ? castPlayer().getUniqueId() : null;
    }
    
    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return sender.addAttachment(plugin);
    }
    
    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return sender.addAttachment(plugin, ticks);
    }
    
    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return sender.addAttachment(plugin, name, value);
    }
    
    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return sender.addAttachment(plugin, name, value, ticks);
    }
    
    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return sender.getEffectivePermissions();
    }
    
    @Override
    public boolean hasPermission(String node) {
        return sender.hasPermission(node);
    }
    
    @Override
    public boolean hasPermission(Permission permission) {
        return sender.hasPermission(permission);
    }
    
    @Override
    public boolean isPermissionSet(String node) {
        return sender.isPermissionSet(node);
    }
    
    @Override
    public boolean isPermissionSet(Permission permission) {
        return sender.isPermissionSet(permission);
    }
    
    @Override
    public void recalculatePermissions() {
        sender.recalculatePermissions();
    }
    
    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        sender.removeAttachment(attachment);
    }
    
    @Override
    public boolean isOp() {
        return sender.isOp();
    }
    
    @Override
    public void setOp(boolean value) {
        sender.setOp(value);
    }
    
    @Override
    public String getName() {
        return sender.getName();
    }
    
    @Override
    public Server getServer() {
        return sender.getServer();
    }
    
    @Override
    public void sendMessage(String message) {
        sender.sendMessage(message);
    }
    
    @Override
    public void sendMessage(String... messages) {
        sender.sendMessage(messages);
    }
    
}
