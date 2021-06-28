package ro.sssssssthedev.AntiCheat.command.type.annotation;

import org.bukkit.command.CommandSender;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Command {
    
    String name();
    
    String[] aliases() default { };
    
    String description()
            
            default "";
    
    String permission()
            
            default "";
    
    int requiredArguments()
            
            default 0;
    
    String permissionMessage()
            
            default "\u00A7cYou do not have permission to execute this command";
    
    String usage() default "";
    
    Class<? extends CommandSender>[] canBeUsedBy() default { CommandSender.class };
    
}
