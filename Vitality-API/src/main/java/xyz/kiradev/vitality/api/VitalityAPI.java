package xyz.kiradev.vitality.api;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.model.UpdateOptions;
import com.sun.corba.se.spi.activation.ServerManager;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import redis.clients.jedis.Jedis;
import xyz.kiradev.vitality.api.model.grant.IGrantManager;
import xyz.kiradev.vitality.api.model.profile.IProfileManager;
import xyz.kiradev.vitality.api.model.punishment.IPunishmentManager;
import xyz.kiradev.vitality.api.model.rank.IRankManager;
import xyz.kiradev.vitality.api.model.server.IServerManager;
import xyz.kiradev.vitality.api.util.json.other.ChatColorTypeAdapter;
import xyz.kiradev.vitality.api.util.mongo.MongoAPI;
import xyz.kiradev.vitality.api.util.mongo.MongoCredentials;
import xyz.kiradev.vitality.api.util.redis.RedisAPI;
import xyz.kiradev.vitality.api.util.redis.credentials.RedisCredentials;
import xyz.kiradev.vitality.api.util.redis.interfaces.RedisCommand;

@Getter
public class VitalityAPI {

    @Getter private static VitalityAPI instance;

    // Dependencies
    private final MongoAPI mongoAPI;
    private final RedisAPI redisAPI;
    private final Gson gson;
    private final UpdateOptions updateOptions;

    // Models
    @Setter private IProfileManager profileManager;
    @Setter private IRankManager rankManager;
    @Setter private IPunishmentManager punishmentManager;
    @Setter private IGrantManager grantManager;
    @Setter private IServerManager serverManager;

    public VitalityAPI(MongoCredentials mongoCredentials, RedisCredentials redisCredentials) {
        instance = this;

        this.mongoAPI = new MongoAPI(mongoCredentials);
        this.redisAPI = new RedisAPI(redisCredentials, this);
        this.gson = new GsonBuilder()
                .registerTypeAdapter(ChatColor.class, new ChatColorTypeAdapter())
                .setPrettyPrinting().create();
        this.updateOptions = new UpdateOptions().upsert(true);
    }

    public <T> T runRedisCommand(RedisCommand<T> redisCommand) {
        Jedis jedis = redisAPI.getJedisPool().getResource();
        if (redisAPI.getRedisCredentials().isAuth()) {
            jedis.auth(redisAPI.getRedisCredentials().getPassword());
        }
        T result = null;
        try {
            result = redisCommand.execute(jedis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return result;
    }
}