package xyz.kiradev.vitality;

import lombok.Getter;
import xyz.kiradev.clash.Clash;
import xyz.kiradev.vitality.api.model.server.Server;
import xyz.kiradev.vitality.api.model.server.enums.ServerType;
import xyz.kiradev.vitality.api.util.mongo.MongoCredentials;
import xyz.kiradev.vitality.api.util.redis.credentials.RedisCredentials;
import xyz.kiradev.vitality.model.profile.listeners.ProfileListener;
import xyz.kiradev.vitality.shared.VitalityShared;
import xyz.kiradev.vitality.shared.server.ServerManager;
import xyz.kiradev.vitality.util.tasks.ServerTask;

@Getter
public final class Vitality extends Clash {

    @Getter private static Vitality instance;

    private VitalityShared api;
    private Server currentServer;

    @Override
    public void onEnable() {
        instance = this;
        loadFiles();
        loadApi();
        loadDependencies();
    }

    private void loadFiles() {
        setupConfig();
    }

    private void loadApi() {
        this.api = new VitalityShared(this, getMongoCredentials(), getRedisCredentials());
        this.currentServer = new Server(getConfig().getString("server.id"), getConfig().getString("server.name"), ServerType.valueOf(getConfig().getString("server.type")));
        this.api.getApi().setServerManager(new ServerManager(api.getApi()));
        this.api.getApi().getServerManager().keepAlive(currentServer);
        this.api.setServerUpdateConsumer(server -> {
            if (server.getId().equals(currentServer.getId())) {
                currentServer = server;
            }
        });
        new ServerTask(this);
    }

    private void loadDependencies() {
        registerListeners(new ProfileListener(this));
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
