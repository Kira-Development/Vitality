package xyz.kiradev.vitality;

import lombok.Getter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import xyz.kiradev.clash.Clash;
import xyz.kiradev.clash.utils.C;
import xyz.kiradev.clash.utils.ConfigFile;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.rank.Rank;
import xyz.kiradev.vitality.api.model.server.Server;
import xyz.kiradev.vitality.api.model.server.enums.ServerType;
import xyz.kiradev.vitality.api.util.mongo.MongoCredentials;
import xyz.kiradev.vitality.api.util.redis.credentials.RedisCredentials;
import xyz.kiradev.vitality.model.essentials.commands.message.MessageCommand;
import xyz.kiradev.vitality.model.essentials.commands.message.ReplyCommand;
import xyz.kiradev.vitality.model.essentials.handler.MessageHandler;
import xyz.kiradev.vitality.model.profile.adapter.NameTagAdapter;
import xyz.kiradev.vitality.model.profile.listeners.ProfileListener;
import xyz.kiradev.vitality.model.profile.staff.StaffHandler;
import xyz.kiradev.vitality.model.profile.staff.command.StaffModeCommand;
import xyz.kiradev.vitality.model.profile.staff.command.VanishCommand;
import xyz.kiradev.vitality.model.profile.staff.command.onjoin.ToggleAutoVanishCommand;
import xyz.kiradev.vitality.model.rank.commands.RankCommands;
import xyz.kiradev.vitality.model.rank.menus.edit.main.listener.RankEditMenuListener;
import xyz.kiradev.vitality.model.server.tasks.ServerTask;
import xyz.kiradev.vitality.punishment.commands.*;
import xyz.kiradev.vitality.shared.VitalityShared;
import xyz.kiradev.vitality.shared.model.rank.RankManager;
import xyz.kiradev.vitality.util.file.LanguageLocale;

import java.io.File;
import java.util.UUID;

@Getter
public final class Vitality extends Clash {

    public Vitality(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    public Vitality() {
    }

    @Getter
    private static Vitality instance;
    private ConfigFile languageFile, configFile;
    private VitalityShared api;
    private Server currentServer;
    private MessageHandler messageHandler = new MessageHandler();

    private StaffHandler staffHandler;

    @Override
    public void onEnable() {
        instance = this;
        setupFiles();
        loadApi();
        setupServers();
        loadDependencies();
        C.logger(C.NORMAL_LINE);
        C.logger("&bPlugin&7: &fVitality");
        C.logger("&bVersion&7: &f" + getDescription().getVersion());
        C.logger("&aThank you for purchasing &3Vitality&a...");
        C.logger(C.NORMAL_LINE);
    }
    private void setupFiles() {
        configFile = new ConfigFile(this, "settings.yml");
        languageFile = new ConfigFile(this, "language.yml");
        LanguageLocale.init();
    }

    private void loadApi() {
        this.api = new VitalityShared(this, getMongoCredentials(), getRedisCredentials());
    }

    private void setupServers() {
        this.currentServer = new Server(getConfigFile().getString("server.id"), getConfigFile().getString("server.name"), ServerType.valueOf(getConfigFile().getString("server.type")));
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
        setupCommands("vitality");
        registerListeners(new ProfileListener(this), new RankEditMenuListener(this));
        getCommandAPI().registerCommand(new RankCommands(this));
        getCommandAPI().registerCommand(new BanCommand(this));
        // Staff Commands:
        getCommandAPI().registerCommand(new VanishCommand(this));
        getCommandAPI().registerCommand(new ToggleAutoVanishCommand(this));
        getCommandAPI().registerCommand(new StaffModeCommand(this));



        getCommandAPI().registerCommand(new MessageCommand(this));
        getCommandAPI().registerCommand(new ReplyCommand(this));

        // Punishment Commands:

        getCommandAPI().registerCommand(new TempWarnCommand(this));
        getCommandAPI().registerCommand(new TempBanCommand(this));
        getCommandAPI().registerCommand(new WarnCommand(this));
        getCommandAPI().registerCommand(new MuteCommand(this));
        getCommandAPI().registerCommand(new KickCommand(this));
        getCommandAPI().registerCommand(new BlacklistCommand(this));
        new NameTagAdapter(this);

        this.staffHandler = new StaffHandler(this);
    }

    private RedisCredentials getRedisCredentials() {
        return new RedisCredentials(getConfigFile().getString("redis.host"), "Vitality:ALL", getConfigFile().getString("redis.password"), getConfigFile().getInt("redis.port"), getConfigFile().getBoolean("redis.auth"));
    }

    private MongoCredentials getMongoCredentials() {
        return getConfigFile().getBoolean("mongo.is-uri") ?
                new MongoCredentials(getConfigFile().getString("mongo.uri"), getConfigFile().getString("mongo.database"), getConfigFile().getBoolean("mongo.is-uri"))
                :
                new MongoCredentials(getConfigFile().getString("mongo.host"),
                        getConfigFile().getString("mongo.database"),
                        getConfigFile().getInt("mongo.port"),
                        getConfigFile().getBoolean("mongo.auth"),
                        getConfigFile().getString("mongo.user"),
                        getConfigFile().getString("mongo.password"));
}

    @Override
    public void onDisable() {
        instance = null;
    }
}