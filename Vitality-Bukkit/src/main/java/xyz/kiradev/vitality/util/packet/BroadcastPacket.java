package xyz.kiradev.vitality.util.packet;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/31/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import lombok.RequiredArgsConstructor;
import xyz.kiradev.vitality.api.util.redis.interfaces.Packet;
import xyz.kiradev.vitality.util.text.C;

@RequiredArgsConstructor
public class BroadcastPacket implements Packet {

    private final String message;
    private final boolean staff;

    public BroadcastPacket(String message) {
        this.message = message;
        this.staff = false;
    }

    @Override
    public void onReceived() {
        if (staff) {
            C.broadcastStaffMessage(message);
            return;
        }

        C.broadcastMessage(message);
    }
}
