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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.api.model.punishment.Punishment;
import xyz.kiradev.vitality.api.model.punishment.type.PunishmentType;
import xyz.kiradev.vitality.model.profile.permission.PermissionManager;
import xyz.kiradev.vitality.util.file.LanguageLocale;
import xyz.kiradev.vitality.util.text.C;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProfileListener implements Listener {

    private final Vitality plugin;

    public ProfileListener(Vitality plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
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

    @EventHandler(priority = EventPriority.HIGH)
    public void altCheck(AsyncPlayerPreLoginEvent event) {
        Profile profile = plugin.getApi().getApi().getProfileManager().getProfileByID(event.getUniqueId());
        if(profile == null) return;

        for (Profile profiles : plugin.getApi().getApi().getProfileManager().getProfiles()) {
            profile.getIps().forEach(ip -> {
                for (String altIp : profiles.getIps()) {
                    if(ip.equalsIgnoreCase(altIp)) {
                        profiles.getIps().forEach(profile::addIp);
                        profile.getIps().forEach(profiles::addIp);
                    }
                }
            });
        }

        plugin.getApi().getApi().getProfileManager().save(profile);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void evasionCheck(PlayerLoginEvent event) {
        Profile profile = plugin.getApi().getApi().getProfileManager().getProfileByID(event.getPlayer().getUniqueId());

        if(profile == null) return;

        List<Profile> bannedAlts = new ArrayList<>();
        List<Profile> blacklistedAlts = new ArrayList<>();

        for (UUID alts : profile.getAlts()) {
            Profile altProfile = plugin.getApi().getApi().getProfileManager().getProfileByID(alts);

            if(altProfile == null) continue;
            if(altProfile.getActivePunishments().isEmpty()) continue;

            List<Punishment> bans = altProfile.getActivePunishments().stream().filter(punishment -> punishment.getType() == PunishmentType.BAN).collect(Collectors.toList());
            if(!bans.isEmpty()) bannedAlts.add(profile);

            List<Punishment> blacklists = altProfile.getActivePunishments().stream().filter(punishment -> punishment.getType() == PunishmentType.BLACKLIST).collect(Collectors.toList());
            if(!blacklists.isEmpty()) blacklistedAlts.add(profile);
        }

        if(!bannedAlts.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < bannedAlts.size(); i++) {
                builder.append(bannedAlts.get(i).getName());
                if(i == bannedAlts.size()) {
                    builder.append("&7.");
                } else {
                    builder.append("&7, ");
                }
            }
            C.broadcastRedisMessage(LanguageLocale.PLAYER_BAN_EVADING.getString()
                            .replaceAll("<player>", profile.getDisplayName())
                            .replaceAll("<alts>", builder.toString())
                    , true);
        }

        if(!blacklistedAlts.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < blacklistedAlts.size(); i++) {
                builder.append(blacklistedAlts.get(i).getName());
                if(i == blacklistedAlts.size()) {
                    builder.append("&7.");
                } else {
                    builder.append("&7, ");
                }
            }
            C.broadcastRedisMessage(LanguageLocale.PLAYER_BLACKLIST_EVADING.getString()
                            .replaceAll("<player>", profile.getDisplayName())
                            .replaceAll("<alts>", builder.toString())
                    , true);
        }
        if(profile.isVanishOnJoin()) {
            profile.setVanished(true);
            LanguageLocale.STAFF_AUTO_VANISH_JOIN_MESSAGE.sendMessage(event.getPlayer());
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onServerSwitch(PlayerJoinEvent event) {
        Profile profile = plugin.getApi().getApi().getProfileManager().getProfileByID(event.getPlayer().getUniqueId());

        PermissionManager.refreshAll(event.getPlayer());
        profile.setCurrentServer(plugin.getCurrentServer().getId());

        plugin.getApi().getApi().getProfileManager().save(profile);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event) {
        PermissionManager.clearProfilePermissions(event.getPlayer());
        PermissionManager.clearRankPermissions(event.getPlayer());
    }
}
