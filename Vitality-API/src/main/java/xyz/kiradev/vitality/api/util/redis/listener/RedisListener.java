package xyz.kiradev.vitality.api.util.redis.listener;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import xyz.kiradev.vitality.api.util.redis.RedisAPI;

import java.util.concurrent.Executor;
import redis.clients.jedis.JedisPubSub;
import xyz.kiradev.vitality.api.util.redis.interfaces.Packet;

public class RedisListener extends JedisPubSub {

    private final Executor executor;
    private final RedisAPI jedisAPI;

    public RedisListener(Executor executor, RedisAPI jedisAPI) {
        this.executor = executor;
        this.jedisAPI = jedisAPI;
    }

    @Override
    public void onMessage(String channel, String message) {
        if (jedisAPI.getRedisCredentials().isAuth()) jedisAPI.getJedisPool().getResource().auth(jedisAPI.getRedisCredentials().getPassword());
        if (!channel.equalsIgnoreCase(jedisAPI.getRedisCredentials().getChannel())) return;

        executor.execute(() -> {
            String[] strings = message.split("///");

            Object jsonObject = null;
            try {
                jsonObject = jedisAPI.getGson().fromJson(strings[0], Class.forName(strings[1]));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Packet redisPacket = (Packet) jsonObject;

            if (redisPacket == null) {
                System.out.println("The redis packet received seems to be null!");
                return;
            }

            redisPacket.onReceived();
        });
    }
}
