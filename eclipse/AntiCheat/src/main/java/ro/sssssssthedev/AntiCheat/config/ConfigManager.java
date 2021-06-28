package ro.sssssssthedev.AntiCheat.config;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import ro.sssssssthedev.AntiCheat.config.impl.CheckConfig;
import ro.sssssssthedev.AntiCheat.config.impl.MessageConfig;
import ro.sssssssthedev.AntiCheat.config.impl.WebConfig;
import ro.sssssssthedev.AntiCheat.config.type.Config;
import ro.sssssssthedev.AntiCheat.trait.Startable;

import java.util.Collection;
import java.util.Set;

public final class ConfigManager implements Startable {
    private ClassToInstanceMap<Config> configs;

    @Override
    public void start() {
        configs = new ImmutableClassToInstanceMap.Builder<Config>()
                .put(CheckConfig.class, new CheckConfig())
                .put(WebConfig.class, new WebConfig())
                .put(MessageConfig.class, new MessageConfig())
                .build();

        configs.values().forEach(Config::generate);
    }

    public Collection<Config> getConfigs() {
        return configs.values();
    }

    public Set<Class<? extends Config>> getConfigClasses() {
        return configs.keySet();
    }

    public final <T extends Config> T getConfig(final Class<T> clazz) {
        return configs.getInstance(clazz);
    }
}
