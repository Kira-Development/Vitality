package xyz.kiradev.vitality.commands;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 10/12/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.CommandSender;
import xyz.kiradev.vitality.Proxy;
import xyz.kiradev.vitality.api.model.rank.Rank;
import xyz.kiradev.vitality.util.C;

import java.util.List;

@RequiredArgsConstructor
@CommandAlias("maintenance")
@CommandPermission("core.maintenance.command")
public class MaintenanceCommand extends BaseCommand {

    private final Proxy plugin;

    @Default
    @Subcommand("help")
    @CommandPermission("core.maintenance.command")
    public void help(CommandSender sender) {
        C.sendMessage(sender, C.convertListToString(plugin.getLanguageFile().getConfig().getStringList("maintenance.command-help-usage")));
    }

    @Subcommand("status")
    @CommandPermission("core.maintenance.command")
    public void status(CommandSender sender) {
        Rank rank = plugin.getApi().getApi().getRankManager().getRankByName(plugin.getConfig().getString("maintenance.rank"));
        StringBuilder players = new StringBuilder();
        for (int i = 0; i < plugin.getConfig().getStringList("maintenance.players").size(); i++) {
            players.append(plugin.getConfig().getStringList("maintenance.players").get(i));
            if(i >= plugin.getConfig().getStringList("maintenance.players").size()) {
                players.append("&7.");
            } else {
                players.append("&7, ");
            }
        }
        C.sendMessage(sender,
                C.convertListToString(plugin.getLanguageFile().getConfig().getStringList("maintenance.status-message"))
                        .replaceAll("<on/off>", plugin.getConfig().getBoolean("maintenance.enabled") ? "&aON" : "&cOFF")
                        .replaceAll("<rank>", rank.getDisplayName())
                        .replaceAll("<players>", players.toString()));
    }

    @Subcommand("toggle")
    @CommandPermission("core.maintenance.command")
    public void toggle(CommandSender sender) {
        boolean whitelisted = plugin.getConfig().getBoolean("maintenance.enabled");
        if(whitelisted) {
            plugin.getConfig().set("maintenance.enabled", false);
            plugin.getConfigFile().saveConfig();
            C.sendMessage(sender, plugin.getLanguageFile().getConfig().getString("maintenance.toggle-off"));
            C.broadcast(plugin.getLanguageFile().getConfig().getString("maintenance.toggle-off-broadcast"));
        } else {
            plugin.getConfig().set("maintenance.enabled", true);
            plugin.getConfigFile().saveConfig();
            C.sendMessage(sender, plugin.getLanguageFile().getConfig().getString("maintenance.toggle-on"));
            C.broadcast(plugin.getLanguageFile().getConfig().getString("maintenance.toggle-on-broadcast"));
        }
    }

    @Subcommand("setrank")
    @CommandPermission("core.maintenance.command")
    public void setRank(CommandSender sender, String rankName) {
        Rank rank = plugin.getApi().getApi().getRankManager().getRankByName(rankName);
        if(rank == null) {
            C.sendMessage(sender, "&cThat rank does not exist!");
            return;
        }

        plugin.getConfig().set("maintenance.rank", rank.getName());
        plugin.getConfigFile().saveConfig();
        C.sendMessage(sender, plugin.getLanguageFile().getConfig().getString("maintenance.set-whitelisted-rank")
                .replaceAll("<rank>", rank.getDisplayName()));
    }

    @Subcommand("addplayer")
    @CommandPermission("core.maintenance.command")
    public void addPlayer(CommandSender sender, String playerName) {
        List<String> list = plugin.getConfig().getStringList("maintenance.players");
        if(!list.contains(playerName)) list.add(playerName);

        plugin.getConfig().set("maintenance.players", list);
        plugin.getConfigFile().saveConfig();
        C.sendMessage(sender, plugin.getLanguageFile().getConfig().getString("maintenance.add-player")
                .replaceAll("<player>", playerName));
    }

    @Subcommand("removeplayer")
    @CommandPermission("core.maintenance.command")
    public void removePlayer(CommandSender sender, String playerName) {
        List<String> list = plugin.getConfig().getStringList("maintenance.players");
        list.remove(playerName);

        plugin.getConfig().set("maintenance.players", list);
        plugin.getConfigFile().saveConfig();
        C.sendMessage(sender, plugin.getLanguageFile().getConfig().getString("maintenance.remove-player")
                .replaceAll("<player>", playerName));
    }
}