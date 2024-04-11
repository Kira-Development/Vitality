package xyz.kiradev.vitality.util;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 18/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigUtil {

    private final Plugin plugin;
    private final String fileName;
    private final File configFile;
    private Configuration configuration;

    public ConfigUtil(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.configFile = new File(plugin.getDataFolder(), fileName);

        try {
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
            this.configuration = null;
        }
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig() {
        return configuration;
    }

    public void reloadConfig() {
        try {
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
            this.configuration = null;
        }
    }

    private void loadConfig() throws IOException {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try (InputStream defaultConfigStream = plugin.getResourceAsStream(fileName)) {
                if (defaultConfigStream != null) {
                    Files.copy(defaultConfigStream, configFile.toPath());
                } else {
                    plugin.getLogger().warning("Default config file not found in resources: " + fileName);
                }
            }
        }

        this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
    }
}