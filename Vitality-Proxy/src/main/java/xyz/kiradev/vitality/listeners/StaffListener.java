package xyz.kiradev.vitality.listeners;

import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.kiradev.vitality.Proxy;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.api.model.rank.Rank;
import xyz.kiradev.vitality.util.C;

public class StaffListener implements Listener {

    private final Proxy plugin;
    private final VitalityAPI api;

    public StaffListener(Proxy plugin) {
        this.plugin = plugin;
        this.api = plugin.getApi().getApi();
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        Profile profile = api.getProfileManager().getProfileByID(event.getPlayer().getUniqueId());
        if(profile == null) return;
        Rank rank = profile.getCurrentRank() == null ? api.getRankManager().getDefaultRank() : profile.getCurrentRank();
        String to = event.getPlayer().getServer().getInfo().getName();
        profile.setCurrentServer(to);
        api.getProfileManager().save(profile);
        if (rank.isStaff()) {
            if (event.getFrom() == null) {
                C.staffBroadcast(plugin.getLanguageFile().getConfig().getString("staff.connect")
                        .replaceAll("<player>", profile.getDisplayName())
                        .replaceAll("<server>", to));
            } else {
                C.staffBroadcast(plugin.getLanguageFile().getConfig().getString("staff.switch")
                        .replaceAll("<player>", profile.getDisplayName())
                        .replaceAll("<new>", to)
                        .replaceAll("<old>", event.getFrom().getName()));
            }
        }
    }

    @EventHandler
    public void onDisconnect(ServerDisconnectEvent event) {
        Profile profile = api.getProfileManager().getProfileByID(event.getPlayer().getUniqueId());
        if (profile == null) return;
        String to = event.getPlayer().getServer().getInfo().getName();
        Rank rank = profile.getCurrentRank();
        if (rank.isStaff()) {
            C.staffBroadcast(plugin.getLanguageFile().getConfig().getString("staff.disconnect")
                    .replaceAll("<player>", profile.getDisplayName())
                    .replaceAll("<server>", to));
        }
        api.getProfileManager().save(profile);
    }
}