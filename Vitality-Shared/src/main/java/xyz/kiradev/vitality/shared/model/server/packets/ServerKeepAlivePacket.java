package xyz.kiradev.vitality.shared.model.server.packets;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.server.Server;
import xyz.kiradev.vitality.api.util.redis.interfaces.Packet;
import xyz.kiradev.vitality.shared.VitalityShared;

@RequiredArgsConstructor
public class ServerKeepAlivePacket implements Packet {

    private final Server server;

    @Override
    public void onReceived() {
        try {
            Bukkit.getScheduler().runTask(VitalityShared.getInstance().getPlugin(), () -> {
                VitalityAPI.getInstance().getServerManager().getServers().remove(server.getId(), server);
                VitalityAPI.getInstance().getServerManager().getServers().put(server.getId(), server);
            });
        } catch (Exception ignored) {}
    }
}
