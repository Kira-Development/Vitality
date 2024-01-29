package xyz.kiradev.vitality.model.profile.listeners;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.model.profile.permission.PermissionManager;

public class ProfileListener implements Listener {

    private final Vitality plugin;

    public ProfileListener(Vitality plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        Profile profile = plugin.getApi().getApi().getProfileManager().getProfileByID(event.getUniqueId());
        if(profile == null) {
            profile = new Profile(event.getUniqueId());
        }

        profile.setName(event.getName());
        profile.setCurrentServer(plugin.getCurrentServer().getId());
        profile.setLastIP(event.getAddress().getHostAddress());
        profile.addIp(event.getAddress().getHostAddress());
        plugin.getApi().getApi().getProfileManager().save(profile);
    }

    @EventHandler
    public void onServerSwitch(PlayerJoinEvent event) {
        Profile profile = plugin.getApi().getApi().getProfileManager().getProfileByID(event.getPlayer().getUniqueId());
        profile.setCurrentServer(plugin.getCurrentServer().getId());
        PermissionManager.refreshAll(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PermissionManager.clearProfilePermissions(event.getPlayer());
        PermissionManager.clearRankPermissions(event.getPlayer());
    }
}
