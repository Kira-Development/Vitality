package xyz.kiradev.vitality;

import dev.demeng.sentinel.wrapper.SentinelClient;
import dev.demeng.sentinel.wrapper.exception.ApiException;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import xyz.kiradev.clash.Clash;
import xyz.kiradev.clash.utils.C;
import xyz.kiradev.clash.utils.ConfigFile;
import xyz.kiradev.vitality.api.model.server.Server;
import xyz.kiradev.vitality.api.model.server.enums.ServerType;
import xyz.kiradev.vitality.api.util.mongo.MongoCredentials;
import xyz.kiradev.vitality.api.util.redis.credentials.RedisCredentials;
import xyz.kiradev.vitality.model.profile.adapter.NameTagAdapter;
import xyz.kiradev.vitality.model.profile.listeners.ProfileListener;
import xyz.kiradev.vitality.model.profile.staff.StaffHandler;
import xyz.kiradev.vitality.model.rank.commands.RankCommands;
import xyz.kiradev.vitality.model.rank.menus.edit.main.listener.RankEditMenuListener;
import xyz.kiradev.vitality.shared.VitalityShared;
import xyz.kiradev.vitality.shared.model.server.ServerManager;
import xyz.kiradev.vitality.util.file.LanguageLocale;
import xyz.kiradev.vitality.model.server.tasks.ServerTask;

import java.io.File;

@Getter
public final class Vitality extends Clash {

    public Vitality(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    public Vitality() {
    }

    @Getter private static Vitality instance;

    private ConfigFile languageFile, config, licenseFile;
    private VitalityShared api;
    private Server currentServer;
    private StaffHandler staffHandler;

    @Override
    public void onEnable() {
        instance = this;
        setupLicenseFile();
        if(!authenticate()) {
            C.logger("&cLicense invalid, Disabling Plugin.");
            C.logger("&7&oTo get a license join: &bhttps://discord.gg/kiradev&7&o.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        setupFiles();
        loadApi();
        setupServers();
        loadDependencies();
    }

    private void setupFiles() {
        config = new ConfigFile(this, "settings.yml");
        languageFile = new ConfigFile(this, "language.yml");
        LanguageLocale.init();
    }

    private boolean authenticate() {

        SentinelClient client = new SentinelClient(
                "YOUR_URL",
                "YOUR_API_KEY",
                "YOUR_ENCRYPTION_SECRET_KEY");

        boolean authenticated = false;

        try {
            client.getLicenseController().auth(
                    licenseFile.getString("AUTH.License"), "Vitality", null, null, SentinelClient.getCurrentHwid(), SentinelClient.getCurrentIp());
            authenticated = true;
        } catch (ApiException e) {
            System.out.println("Failed to verify license: " + e.getResponse().getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

        if (authenticated) {
            System.out.println("Successfully authenticated.");
        }
        return authenticated;
    }
    private void setupLicenseFile() {
        licenseFile = new ConfigFile(this, "license.yml");
    }

    private void loadApi() {
        this.api = new VitalityShared(this, getMongoCredentials(), getRedisCredentials());
    }

    private void setupServers() {
        this.currentServer = new Server(getConfig().getString("server.id"), getConfig().getString("server.name"), ServerType.valueOf(getConfig().getString("server.type")));
        this.api.getApi().getServerManager().start();
        this.api.getApi().getServerManager().keepAlive(currentServer);
        this.api.setServerUpdateConsumer(server -> {
            if (server.getId().equals(currentServer.getId())) {
                currentServer = server;
            }
        });
        new ServerTask(this);
    }

    private void loadDependencies() {
        setupMenuAPI();
        setupCommands("Vitality");
        registerListeners(new ProfileListener(this), new RankEditMenuListener(this));
        getCommandAPI().registerCommand(new RankCommands(this));

        new NameTagAdapter(this);

        this.staffHandler = new StaffHandler(this);
    }

    private RedisCredentials getRedisCredentials() {
        return new RedisCredentials(getConfig().getString("redis.host"), "Vitality:ALL", getConfig().getString("redis.password"), getConfig().getInt("redis.port"), getConfig().getBoolean("redis.auth"));
    }

    private MongoCredentials getMongoCredentials() {
        return getConfig().getBoolean("mongo.is-uri") ?
                new MongoCredentials(getConfig().getString("mongo.uri"), getConfig().getString("mongo.database"), getConfig().getBoolean("mongo.is-uri"))
                :
                new MongoCredentials(getConfig().getString("mongo.host"),
                        getConfig().getString("mongo.database"),
                        getConfig().getInt("mongo.port"),
                        getConfig().getBoolean("mongo.auth"),
                        getConfig().getString("mongo.user"),
                        getConfig().getString("mongo.password"));
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
