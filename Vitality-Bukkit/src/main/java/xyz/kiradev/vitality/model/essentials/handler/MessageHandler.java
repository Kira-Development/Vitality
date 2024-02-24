package xyz.kiradev.vitality.model.essentials.handler;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/*
 * Copyright Kira Development© 2024
 * @project Vitality
 * @date 2/20/2024
 */
public class MessageHandler {

    private HashMap<UUID, UUID> lastMessagedPlayers = new HashMap<>();

    public void addMessagedPlayers(Player player, Player target) {
        lastMessagedPlayers.put(player.getUniqueId(), target.getUniqueId());
    }

    public void removeMessagedPlayers(Player player, Player target) {
       lastMessagedPlayers.remove(player.getUniqueId());
       lastMessagedPlayers.remove(target.getUniqueId());
    }
    public boolean containsPlayer(UUID uuid) {
       if (lastMessagedPlayers.containsKey(uuid)) {
            return true;
       }
    return false;
    }
}