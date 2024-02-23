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
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.server.Server;
import xyz.kiradev.vitality.api.util.redis.interfaces.Packet;
import xyz.kiradev.vitality.shared.VitalityShared;

@RequiredArgsConstructor
public class ServerUpdatePacket implements Packet {

    private final Server server;

    @Override
    public void onReceived() {
        VitalityAPI.getInstance().getServerManager().getServers().put(server.getId(), server);
        if (VitalityShared.getInstance().getServerUpdateConsumer() != null) {
            VitalityShared.getInstance().getServerUpdateConsumer().accept(server);
        }
    }
}
