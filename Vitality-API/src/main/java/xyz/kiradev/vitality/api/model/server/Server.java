package xyz.kiradev.vitality.api.model.server;

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
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.server.enums.ServerStatus;
import xyz.kiradev.vitality.api.model.server.enums.ServerType;

@Getter @Setter
public class Server {

    private String id, name;
    private ServerType type;
    private ServerStatus status;
    private final long startupTime;
    private long lastKeepAlive = System.currentTimeMillis();

    public Server(String id, String name, ServerType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = ServerStatus.ONLINE;
        this.startupTime = System.currentTimeMillis();
    }

    public boolean isOnline() {
        return status == ServerStatus.ONLINE;
    }

    public boolean isOffline() {
        return status == ServerStatus.OFFLINE;
    }

    public boolean isWhitelisted() {
        return status == ServerStatus.WHITELISTED;
    }

    public void keepAlive() {
        this.lastKeepAlive = System.currentTimeMillis();
        VitalityAPI.getInstance().getServerManager().keepAlive(this);
    }

    public void sendUpdate() {
        VitalityAPI.getInstance().getServerManager().sendUpdate(this);
    }

    public long getUptime() {
        return System.currentTimeMillis() - startupTime;
    }

    @Override
    public String toString() {
        return "@{" + id + "}," +
                " {NAME: " + name + "}," +
                " {START-UP-TIME: " + startupTime + "}," +
                " {LAST-KEEP-ALIVE: " + lastKeepAlive + "};";
    }
}