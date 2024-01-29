package xyz.kiradev.vitality.listeners;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 18/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.kiradev.vitality.Proxy;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.profile.Profile;

public class PermissionsListener implements Listener {

    private final VitalityAPI api;

    public PermissionsListener(Proxy proxy) {
        this.api = proxy.getApi().getApi();
    }

    @EventHandler
    public void onPermission(PermissionCheckEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        Profile profile = api.getProfileManager().getProfileByID(player.getUniqueId());

        event.setHasPermission(profile.hasPermission(event.getPermission()));
    }

}