package xyz.kiradev.vitality.api.model.server;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import java.util.Map;

public interface IServerManager {
    Server getByID(String id);
    Server getByName(String name);
    Map<String, Server> getServers();

    void start();
    void keepAlive(Server server);
    void sendUpdate(Server server);
}