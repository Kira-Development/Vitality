package xyz.kiradev.vitality.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.kiradev.vitality.Proxy;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.profile.Profile;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 10/12/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */
public class HubCommand extends BaseCommand {

    private final Proxy instance;
    private final VitalityAPI api;

    public HubCommand(Proxy instance, VitalityAPI api) {
        this.instance = instance;
        this.api = api;
    }

    @CommandAlias("hub|lobby|l")
    public void hubCommand(ProxiedPlayer player) {
        Profile profile = api.getProfileManager().getProfileByID(player.getUniqueId());
        if(profile.getCurrentServer() == api.getServerManager().getHubs().getName()) {
            player.sendMessage(instance.getLanguageFile().getConfig().getString("hub-balancer.already-connected"));
        }
        if(!(profile.getCurrentServer() == api.getServerManager().getHubs().getName())) {
            profile.setCurrentServer(api.getServerManager().getHubs().getName());
            player.connect(instance.hubServersInfo);
        }
    }
}
