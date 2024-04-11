package xyz.kiradev.vitality.util;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 18/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.kiradev.vitality.Proxy;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.api.model.rank.Rank;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class C {
    public String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

    public void broadcast(String message) {
        Proxy.getInstance().getProxy().broadcast(color(message));
    }

    public String convertListToString(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if(i != 0) builder.append("\n");
            builder.append(list.get(i));
        }

        return builder.toString();
    }

    public void staffBroadcast(String message) {
        System.out.println(color(message));
        List<ProxiedPlayer> players = Proxy.getInstance().getProxy().getPlayers().stream().filter(p -> {
            Profile profile = Proxy.getInstance().getApi().getApi().getProfileManager().getProfileByID(p.getUniqueId());
            Rank rank = profile.getCurrentRank();
            return rank != null && rank.isStaff();
        }).collect(Collectors.toList());
        if(players.isEmpty()) return;
        players.forEach(player -> sendMessage(player, message));
    }
}
