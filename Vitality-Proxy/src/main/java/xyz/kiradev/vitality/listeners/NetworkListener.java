package xyz.kiradev.vitality.listeners;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 10/12/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.kiradev.vitality.Proxy;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.api.model.rank.Rank;
import xyz.kiradev.vitality.util.C;

public class NetworkListener implements Listener {

    private final Proxy plugin;
    private final VitalityAPI api;

    public NetworkListener(Proxy proxy) {
        this.plugin = proxy;
        this.api = proxy.getApi().getApi();
    }

    @EventHandler
    public void onPing(ProxyPingEvent event) {
        if(plugin.getConfig().getBoolean("motd.enabled")) return;
        String motd = plugin.getLanguageFile().getConfig().getString("MOTD.line-1")
                + "\n" +
                plugin.getLanguageFile().getConfig().getString("MOTD.line-2");
        if(plugin.getConfig().getBoolean("motd.maintenance-enabled") &&
                plugin.getConfig().getBoolean("maintenance.enabled")) {
            motd = plugin.getLanguageFile().getConfig().getString("MOTD.maintenance-line-1")
                    + "\n" +
                    plugin.getLanguageFile().getConfig().getString("MOTD.maintenance-line-2");
        }

        event.getResponse().setDescription(C.color(motd));
    }

    @EventHandler
    public void onConnect(ServerConnectEvent event) {
        if (!plugin.getConfigFile().getConfig().getBoolean("maintenance.enabled")) return;
        Profile profile = api.getProfileManager().getProfileByID(event.getPlayer().getUniqueId());
        Rank whitelistRank = api.getRankManager().getRankByName(
                plugin.getConfig().getString("maintenance.rank")
        );
        if (whitelistRank == null) return;
        if (profile == null) {
            event.getPlayer().disconnect(C.color(C.convertListToString(plugin.getLanguageFile().getConfig().getStringList("maintenance.kick-message"))));
            return;
        }
        if (!profile.hasHigherRank(whitelistRank)) {
            event.getPlayer().disconnect(C.color(C.convertListToString(plugin.getLanguageFile().getConfig().getStringList("maintenance.kick-message"))));
        }
    }
}