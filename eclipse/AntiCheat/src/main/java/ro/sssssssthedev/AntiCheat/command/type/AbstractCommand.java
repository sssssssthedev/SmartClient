package ro.sssssssthedev.AntiCheat.command.type;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import ro.sssssssthedev.AntiCheat.command.type.annotation.Command;
import ro.sssssssthedev.AntiCheat.command.type.arguments.Arguments;
import ro.sssssssthedev.AntiCheat.command.type.sender.Sender;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractCommand {
    
    private static Map<UUID, Sender> cachedSenders = new HashMap<>();
    private static Sender consoleSender;
    
    private static Sender getSender(final CommandSender sender) {
        if (sender instanceof LivingEntity) {
            final LivingEntity livingEntity = (LivingEntity) sender;
            
            Sender s;
            
            if (cachedSenders.containsKey(livingEntity.getUniqueId())) {
                s = cachedSenders.get(livingEntity.getUniqueId());
                s.refresh(livingEntity);
            } else {
                cachedSenders.put(livingEntity.getUniqueId(), s = new Sender(livingEntity));
            }
            
            return s;
        }
        if (sender instanceof ConsoleCommandSender)
            return consoleSender == null ? consoleSender = new Sender(sender) : consoleSender;
        if (sender instanceof BlockCommandSender)
            return new Sender(sender);
        
        return null;
    }
    
    private Command info;
    private Plugin plugin;
    protected boolean asynchronous = false;
    
    public AbstractCommand(final Plugin plugin) {
        this.plugin = plugin;
        
        if (getClass().isAnnotationPresent(Command.class))
            info = getClass().getAnnotation(Command.class);
        else {
            try {
                final Method executeMethod = getClass().getMethod("execute", Sender.class, Arguments.class);
                
                if (executeMethod.isAnnotationPresent(Command.class)) {
                    info = executeMethod.getAnnotation(Command.class);
                }
            } catch (Exception exception) {
                System.out.println("Failed to register command with class name " + getClass().getName() + "! Stacktrace:");
                exception.printStackTrace();
                return;
            }
            
        }
        
        if (info == null) {
            throw new RuntimeException("Couldn't find Command annotation in Command class " + getClass().getName());
        }
        
        try {
            Field cMap = SimplePluginManager.class.getDeclaredField("commandMap");
            cMap.setAccessible(true);
            CommandMap map = (CommandMap) cMap.get(Bukkit.getPluginManager());
            map.register(plugin.getDescription().getName(), new org.bukkit.command.Command(name(), description(), generateUsage(), Arrays.asList(aliases())) {
                @Override
                public boolean execute(CommandSender sender, String unusedLabel, String[] args) {
                    handle(sender, args);
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String name() {
        return info.name();
    }
    
    public String[] aliases() {
        return info.aliases();
    }
    
    public String permission() {
        return info.permission();
    }
    
    public int requiredArguments() {
        return info.requiredArguments();
    }
    
    public String usage() {
        return info.usage();
    }
    
    public String permissionMessage() {
        return info.permissionMessage();
    }
    
    public String description() {
        return info.description();
    }
    
    public String generateUsage() {
        return ChatColor.RED + "Usage: /" + name() + " " + usage();
    }
    
    public final void handle(CommandSender sender, String[] args) {
        
        boolean canUse = false;
        
        for (Class<? extends CommandSender> clazz : info.canBeUsedBy()) {
            if (clazz.isAssignableFrom(sender.getClass())) {
                canUse = true;
                break;
            }
        }
        
        if (! canUse) {
            sender.sendMessage(ChatColor.RED + "You may not use this command");
            return;
        }
        
        if (! sender.hasPermission(permission())) {
            sender.sendMessage(permissionMessage());
            return;
        }
        
        if (args.length < requiredArguments()) {
            sender.sendMessage(generateUsage());
            return;
        }
        
        try {
            Runnable execute = () -> execute(getSender(sender), new Arguments(args));
            
            if (! asynchronous)
                execute.run();
            else
                Bukkit.getScheduler().runTaskAsynchronously(plugin, execute);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "An Error occured: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public abstract void execute(Sender sender, Arguments arguments);
    
}
