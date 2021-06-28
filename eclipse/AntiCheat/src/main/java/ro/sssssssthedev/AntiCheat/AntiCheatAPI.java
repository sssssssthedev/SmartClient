package ro.sssssssthedev.AntiCheat;

import lombok.Getter;

import org.bukkit.plugin.EventExecutor;
import ro.sssssssthedev.AntiCheat.alert.Alert;
import ro.sssssssthedev.AntiCheat.command.CommandManager;
import ro.sssssssthedev.AntiCheat.command.type.AbstractCommand;
import ro.sssssssthedev.AntiCheat.config.ConfigManager;
import ro.sssssssthedev.AntiCheat.config.impl.WebConfig;
import ro.sssssssthedev.AntiCheat.data.Observable;
import ro.sssssssthedev.AntiCheat.data.manager.PlayerDataManager;
import ro.sssssssthedev.AntiCheat.hook.ClassManager;
import ro.sssssssthedev.AntiCheat.hook.DiscordManager;
import ro.sssssssthedev.AntiCheat.judgement.JudgementManager;
import ro.sssssssthedev.AntiCheat.packet.VersionHandler;
import ro.sssssssthedev.AntiCheat.processor.ProcessorManager;
import ro.sssssssthedev.AntiCheat.trait.Startable;
import sun.misc.Version;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
public enum AntiCheatAPI {
    INSTANCE;

    private AntiCheatPlugin plugin;
    
    private ScheduledExecutorService judgementExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledExecutorService alertExecutor = Executors.newSingleThreadScheduledExecutor();
    private final ScheduledExecutorService packetExecutor = Executors.newSingleThreadScheduledExecutor();
    private final ScheduledExecutorService positionExecutor = Executors.newSingleThreadScheduledExecutor();

    private final ScheduledExecutorService authExecutor = Executors.newSingleThreadScheduledExecutor();

    private Observable<Object> debug = new Observable<>(true);

    private ProcessorManager processorManager = new ProcessorManager();
    private PlayerDataManager playerDataManager = new PlayerDataManager();
    private ConfigManager configManager = new ConfigManager();
    private CommandManager commandManager = new CommandManager();
    private JudgementManager judgementManager = new JudgementManager();

    private DiscordManager discordManager = null;

    private VersionHandler versionHandler = new VersionHandler();
    public final List<Startable> startables = new ArrayList<>();

    public String key;

    public boolean disabled = false;
    
    public ClassManager classManager = new ClassManager();

    public void start(final AntiCheatPlugin plugin) {
        this.plugin = plugin;

            try {
                if (configManager.getConfig(WebConfig.class).getEnabled()) {
                    discordManager = (DiscordManager) Class.forName("ro.sssssssthedev.AntiCheat.hook.DiscordManager").getConstructor().newInstance();
                } else {
                    discordManager = null;
                }
            }
            catch (Exception e) {
                discordManager = null;
            }

            classManager.start();

         
    }

    public void shutdown() {
        this.plugin = null;
        authExecutor.shutdownNow();
        judgementExecutor.shutdownNow();
        alertExecutor.shutdownNow();
        packetExecutor.shutdownNow();
        positionExecutor.shutdownNow();
    }

    public AntiCheatPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(AntiCheatPlugin plugin) {
        this.plugin = plugin;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public DiscordManager getDiscordManager() {
        return discordManager;
    }

    public void setDiscordManager(DiscordManager discordManager) {
        this.discordManager = discordManager;
    }

    public Observable<Object> getDebug() {
        return debug;
    }

    public void setDebug(Observable<Object> debug) {
        this.debug = debug;
    }

    public ScheduledExecutorService getAlertExecutor() {
        return alertExecutor;
    }

    public void setAlertExecutor(ScheduledExecutorService  alertExecutor) {
        this.alertExecutor = alertExecutor;
    }

    public ProcessorManager getProcessorManager() {
        return processorManager;
    }

    public void setProcessorManager(ProcessorManager processorManager) {
        this.processorManager = processorManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public void setPlayerDataManager(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    public JudgementManager getJudgementManager() {
        return judgementManager;
    }

    public void setJudgementManager(JudgementManager judgementManager) {
        this.judgementManager = judgementManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public ScheduledExecutorService getJudgementExecutor() {
        return judgementExecutor;
    }

    public void setJudgementExecutor(ScheduledExecutorService judgementExecutor) {
        this.judgementExecutor = judgementExecutor;
    }

    public VersionHandler getVersionHandler() {
        return versionHandler;
    }

    public void setVersionHandler(VersionHandler versionHandler) {
        this.versionHandler = versionHandler;
    }
}
