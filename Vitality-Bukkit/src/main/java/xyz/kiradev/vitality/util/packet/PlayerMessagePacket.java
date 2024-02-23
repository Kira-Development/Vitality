package xyz.kiradev.vitality.util.packet;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 2/3/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.kiradev.vitality.api.util.redis.interfaces.Packet;
import xyz.kiradev.vitality.util.text.C;

import java.util.UUID;

@AllArgsConstructor
public class PlayerMessagePacket implements Packet {

    private final UUID player;
    private final String message;

    @Override
    public void onReceived() {
        try {
            Player bukkitPlayer = Bukkit.getPlayer(player);
            C.sendMessage(bukkitPlayer, message);
        } catch (Exception ignored) {}
    }
}