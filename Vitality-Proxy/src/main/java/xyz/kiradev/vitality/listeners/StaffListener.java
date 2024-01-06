package xyz.kiradev.vitality.listeners;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.kiradev.vitality.Proxy;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.api.model.rank.Rank;
import xyz.kiradev.vitality.util.C;

import java.util.UUID;

public class StaffListener implements Listener {

    private final Proxy plugin;
    private final VitalityAPI api;

    public StaffListener(Proxy plugin) {
        this.plugin = plugin;
        this.api = plugin.getApi().getApi();
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        System.out.println(uuid.toString());
        Profile profile = api.getProfileManager().getProfileByID(event.getPlayer().getUniqueId());
        if (profile == null) return;
        Rank rank = profile.getCurrentRank();
        if (rank.isStaff()) {
            if (event.getFrom() == null) {
                C.staffBroadcast(plugin.getLanguageFile().getConfig().getString("staff.connect")
                        .replaceAll("<player>", profile.getDisplayName())
                        .replaceAll("<server>", event.getPlayer().getServer().getInfo().getName()));
            } else {
                C.staffBroadcast(plugin.getLanguageFile().getConfig().getString("staff.switch")
                        .replaceAll("<player>", profile.getDisplayName())
                        .replaceAll("<new>", event.getPlayer().getServer().getInfo().getName())
                        .replaceAll("<old>", event.getFrom().getName()));
            }
        }
    }

    @EventHandler
    public void onDisconnect(ServerDisconnectEvent event) {
        Profile profile = api.getProfileManager().getProfileByID(event.getPlayer().getUniqueId());
        if (profile == null) return;
        Rank rank = profile.getCurrentRank();
        if (rank.isStaff()) {
            C.staffBroadcast(plugin.getLanguageFile().getConfig().getString("staff.disconnect")
                    .replaceAll("<player>", profile.getDisplayName())
                    .replaceAll("<server>", event.getPlayer().getServer().getInfo().getName()));
        }
        api.getProfileManager().save(profile);
    }
}
