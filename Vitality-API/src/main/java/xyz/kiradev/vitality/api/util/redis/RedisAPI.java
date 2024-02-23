package xyz.kiradev.vitality.api.util.redis;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import com.google.gson.Gson;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.util.redis.credentials.RedisCredentials;
import xyz.kiradev.vitality.api.util.redis.interfaces.Packet;
import xyz.kiradev.vitality.api.util.redis.listener.RedisListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
public class RedisAPI {
    private final RedisCredentials redisCredentials;
    private final Executor executor;
    private final RedisListener redisListener;
    private VitalityAPI api;

    private JedisPool jedisPool;
    private Jedis jedis;

    public RedisAPI(RedisCredentials redisCredentials, VitalityAPI api) {
        this.api = api;
        this.redisCredentials = redisCredentials;
        this.executor = Executors.newFixedThreadPool(2);

        this.redisListener = new RedisListener(executor, this);
        this.connect(redisCredentials);
    }

    private void connect(RedisCredentials redisCredentials) {
        try {
            this.jedisPool = new JedisPool(new JedisPoolConfig(), redisCredentials.getHostname(), redisCredentials.getPort());
            this.jedis = jedisPool.getResource();
            if (redisCredentials.isAuth()) jedis.auth(redisCredentials.getPassword());
            new Thread(() -> jedis.subscribe(new RedisListener(executor, this), redisCredentials.getChannel())).start();
            jedis.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(Packet redisPacket) {
        redisPacket.onSend();
        new Thread(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                this.jedis = jedis;
                if (redisCredentials.isAuth()) jedis.auth(redisCredentials.getPassword());

                jedis.publish(redisCredentials.getChannel(), api.getGson().toJson(redisPacket) + "///" + redisPacket.getClass().getName());
            }
        }).start();
    }

    public String getPing() {
        return jedis.ping();
    }

    public boolean isConnected() {
        return jedis.isConnected();
    }

    public void shutdown() {
        jedisPool.destroy();
        redisListener.unsubscribe();
    }
}