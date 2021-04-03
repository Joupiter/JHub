package fr.joupi.jhub.utils.config.impl;

import fr.joupi.jhub.utils.config.IConfig;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Getter
public abstract class AbstractConfig implements IConfig {

    private final Plugin plugin;

    private final String configName;
    private final File file;

    private Configuration configuration;

    public AbstractConfig(Plugin plugin, String configName) {
        this.plugin = plugin;
        this.configName = configName;
        this.file = new File(plugin.getDataFolder(), configName);
        this.createConfig();
    }

    private void createConfig() {
        saveDefaultConfig();

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void saveDefaultConfig() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();

            try (InputStream input = getPlugin().getResourceAsStream(configName)) {
                Files.copy(input, file.toPath());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void reloadConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
