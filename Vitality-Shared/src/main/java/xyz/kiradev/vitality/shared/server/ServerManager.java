package xyz.kiradev.vitality.shared.server;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import lombok.Getter;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.server.IServerManager;
import xyz.kiradev.vitality.api.model.server.Server;
import xyz.kiradev.vitality.shared.server.packets.ServerKeepAlivePacket;
import xyz.kiradev.vitality.shared.server.packets.ServerUpdatePacket;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ServerManager implements IServerManager {

    private final VitalityAPI api;
    @Getter
    private final Map<String, Server> servers;

    public ServerManager(VitalityAPI api) {
        this.api = api;
        this.servers = new ConcurrentHashMap<>();
        start();
    }

    private void start() {
        api.runRedisCommand(redis -> {
            for (Map.Entry<String, String> serverEntry : redis.hgetAll("servers").entrySet()) {
                String serverId = serverEntry.getKey();
                String json = serverEntry.getValue();
                servers.put(serverId, api.getGson().fromJson(json, Server.class));
            }
            return null;
        });
    }

    @Override
    public Server getByID(String id) {
        return servers.get(id);
    }

    @Override
    public Server getByName(String name) {
        for (Server server : servers.values()) {
            if(server.getName().equalsIgnoreCase(name)) return server;
        }
        return null;
    }

    @Override
    public void keepAlive(Server server) {
        api.getRedisAPI().send(new ServerKeepAlivePacket(server));
        CompletableFuture.runAsync(() -> api.runRedisCommand(redis -> {
            redis.hset("servers", server.getId(), api.getGson().toJson(server));
            return null;
        }));
    }

    @Override
    public void sendUpdate(Server server) {
        server.setLastKeepAlive(System.currentTimeMillis());
        api.getRedisAPI().send(new ServerUpdatePacket(server));
        CompletableFuture.runAsync(() -> api.runRedisCommand(redis -> {
            redis.hset("servers", server.getId(), api.getGson().toJson(server));
            return null;
        }));
    }
}