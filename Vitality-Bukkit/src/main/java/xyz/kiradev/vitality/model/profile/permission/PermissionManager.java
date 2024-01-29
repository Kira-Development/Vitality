package xyz.kiradev.vitality.model.profile.permission;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 17/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.api.model.rank.Rank;

import java.util.*;

public class PermissionManager {

    private static final Map<UUID, PermissionAttachment> profileAttachments = new HashMap<>();
    private static final Map<UUID, PermissionAttachment> rankAttachments = new HashMap<>();

    public static void setProfilePermissions(Player player, Set<String> permissions) {
        clearProfilePermissions(player);

        PermissionAttachment attachment = player.addAttachment(Vitality.getInstance());

        for (String permission : permissions) {
            attachment.setPermission(permission, true);
        }

        profileAttachments.put(player.getUniqueId(), attachment);
    }

    public static void setRankPermissions(Player player, Set<String> permissions) {
        clearRankPermissions(player);

        PermissionAttachment attachment = player.addAttachment(Vitality.getInstance());

        for (String permission : permissions) {
            attachment.setPermission(permission, true);
        }

        rankAttachments.put(player.getUniqueId(), attachment);
    }

    public static void clearProfilePermissions(Player player) {
        PermissionAttachment attachment = profileAttachments.remove(player.getUniqueId());
        clearPermissions(attachment);
    }

    public static void clearRankPermissions(Player player) {
        PermissionAttachment attachment = rankAttachments.remove(player.getUniqueId());
        clearPermissions(attachment);
    }

    private static void clearPermissions(PermissionAttachment attachment) {
        if (attachment != null) {
            for (String permission : attachment.getPermissions().keySet()) {
                attachment.unsetPermission(permission);
            }
            attachment.remove();
        }
    }

    public static void refreshAll(Player player) {
        clearRankPermissions(player);
        clearProfilePermissions(player);
        Profile profile = Vitality.getInstance().getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
        Set<String> rankPermissions = new HashSet<>();
        for(UUID uuid : profile.getCurrentRank().getInheritance()) {
            Rank rank = Vitality.getInstance().getApi().getApi().getRankManager().getRankByUUID(uuid);
            rankPermissions.addAll(rank.getAllPermissions());
        }
        setProfilePermissions(player, profile.getPermissions());
        setRankPermissions(player, rankPermissions);
    }
}