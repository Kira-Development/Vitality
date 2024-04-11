package xyz.kiradev.vitality.shared;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.rank.Rank;
import xyz.kiradev.vitality.api.model.server.Server;
import xyz.kiradev.vitality.api.util.mongo.MongoCredentials;
import xyz.kiradev.vitality.api.util.redis.credentials.RedisCredentials;
import xyz.kiradev.vitality.shared.model.grant.GrantManager;
import xyz.kiradev.vitality.shared.model.profile.ProfileManager;
import xyz.kiradev.vitality.shared.model.punishment.PunishmentManager;
import xyz.kiradev.vitality.shared.model.rank.RankManager;
import xyz.kiradev.vitality.shared.model.server.ServerManager;

import java.util.function.Consumer;

@Getter
public class VitalityShared {

    @Getter private static VitalityShared instance;

    private JavaPlugin plugin;
    private final VitalityAPI api;
    @Setter
    private Consumer<Server> serverUpdateConsumer;

    public VitalityShared(MongoCredentials credentials, RedisCredentials redisCredentials) {
        instance = this;

        this.api = new VitalityAPI(credentials, redisCredentials);
        this.api.setServerManager(new ServerManager(api));
        loadRanks();
        loadGrants();
        loadPunishments();
        loadProfiles();
    }

    public VitalityShared(JavaPlugin plugin, MongoCredentials credentials, RedisCredentials redisCredentials) {
        instance = this;
        this.plugin = plugin;
        this.api = new VitalityAPI(credentials, redisCredentials);
        this.api.setServerManager(new ServerManager(api));
        loadRanks();
        loadGrants();
        loadPunishments();
        loadProfiles();
    }

    private void loadRanks() {
        System.out.println("Loading ranks system...");
        api.setRankManager(new RankManager(api));
        if(api.getRankManager().getDefaultRank() == null) {
            System.out.println("Default rank was not found! Creating new default rank...");
            Rank rank = new Rank("Default");
            rank.setDefault(true);
            api.getRankManager().update(rank);
            System.out.println("Successfully created Default rank.");
            return;
        }
        System.out.println("Successfully loaded ranks system!");
    }

    private void loadGrants() {
        System.out.println("Loading grants system...");
        api.setGrantManager(new GrantManager(api));
        System.out.println("Successfully loaded grants system!");
    }

    private void loadPunishments() {
        System.out.println("Loading punishments system...");
        api.setPunishmentManager(new PunishmentManager(api));
        System.out.println("Successfully loaded punishments system!");
    }

    private void loadProfiles() {
        System.out.println("Loading profiles system...");
        api.setProfileManager(new ProfileManager(this.getApi()));
        System.out.println("Successfully loaded profiles system!");
    }
}