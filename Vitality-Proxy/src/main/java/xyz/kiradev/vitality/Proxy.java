package xyz.kiradev.vitality;

import co.aikar.commands.BungeeCommandManager;
import com.mongodb.Block;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.bson.Document;
import xyz.kiradev.vitality.api.util.mongo.MongoCredentials;
import xyz.kiradev.vitality.api.util.redis.credentials.RedisCredentials;
import xyz.kiradev.vitality.commands.MaintenanceCommand;
import xyz.kiradev.vitality.listeners.NetworkListener;
import xyz.kiradev.vitality.listeners.PermissionsListener;
import xyz.kiradev.vitality.listeners.StaffListener;
import xyz.kiradev.vitality.shared.VitalityShared;
import xyz.kiradev.vitality.util.ConfigUtil;

@Getter
public final class Proxy extends Plugin {

    @Getter private static Proxy instance;

    private VitalityShared api;
    private ConfigUtil languageFile;
    private ConfigUtil configFile;
    private Configuration config;
    private BungeeCommandManager bungeeCommandManager;

    @Override
    public void onEnable() {
        instance = this;

        setupConfig();
        this.api = new VitalityShared(getMongoCredentials(), getRedisCredentials());
        setupListeners();
        setupCommands();
        api.getApi().getMongoAPI().getMongoDatabase().getCollection("profiles").find().forEach((Block<? super Document>) document -> System.out.println(document.toJson()));
    }

    private void setupConfig() {
        this.languageFile = new ConfigUtil(this, "language.yml");
        this.configFile = new ConfigUtil(this, "config.yml");
        this.config = configFile.getConfig();
    }

    private void setupListeners() {
        this.getProxy().getPluginManager().registerListener(this, new PermissionsListener(this));
        this.getProxy().getPluginManager().registerListener(this, new StaffListener(this));
        this.getProxy().getPluginManager().registerListener(this, new NetworkListener(this));
    }

    private void setupCommands() {
        this.bungeeCommandManager = new BungeeCommandManager(this);
        this.bungeeCommandManager.registerCommand(new MaintenanceCommand(this));
    }

    private RedisCredentials getRedisCredentials() {
        return new RedisCredentials(config.getString("redis.host"), "Vitality:PROXY", config.getString("redis.password"), config.getInt("redis.port"), config.getBoolean("redis.auth"));
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
}
