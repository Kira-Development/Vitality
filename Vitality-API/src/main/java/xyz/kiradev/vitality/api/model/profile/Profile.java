package xyz.kiradev.vitality.api.model.profile;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.grant.Grant;
import xyz.kiradev.vitality.api.model.punishment.Punishment;
import xyz.kiradev.vitality.api.model.rank.Rank;

import java.util.*;

@Getter
@Setter
public class Profile {

    @SerializedName("_id")
    private final UUID uuid;

    private String name, lastIP, currentServer, lastItems;
    private Set<String> permissions, ips;
    private Set<UUID> alts;
    private List<Grant> grants;
    private List<Punishment> punishments;
    private boolean vanished, staffMode, freeze, vanishOnJoin, staffModeOnJoin;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.permissions = new HashSet<>();
        this.ips = new HashSet<>();
        this.alts = new HashSet<>();
        this.grants = new ArrayList<>();
        this.punishments = new ArrayList<>();
    }

    public String getDisplayName() {
        return getCurrentRank().getDisplayColor() + name;
    }

    public Grant getHighestGrant() {
        if(grants.isEmpty()) return null;
        Grant grant = grants.get(0);
        for (Grant grant1 : grants) {
            Rank rank = VitalityAPI.getInstance().getRankManager().getRankByUUID(grant1.getRankId());
            Rank rank2 = VitalityAPI.getInstance().getRankManager().getRankByUUID(grant.getRankId());
            if ((rank.getWeight() - rank2.getWeight()) >= 0) grant = grant1;
        }
        return grant;
    }

    public Rank getCurrentRank() {
        Rank rank = VitalityAPI.getInstance().getRankManager().getDefaultRank();
        if(grants == null || grants.isEmpty()) return rank;
        return VitalityAPI.getInstance().getRankManager().getRankByUUID(getHighestGrant().getRankId());
    }

    public void addGrant(Grant grant) {
        if(grants.contains(grant)) return;
        grants.add(grant);
    }

    public void removeGrant(Grant grant) {
        grants.remove(grant);
    }

    public void addIp(String ip) {
        if(ips.contains(ip)) return;
        ips.add(ip);
    }

    public void removeIp(String ip) {
        ips.remove(ip);
    }

    public void addPermission(String permission) {
        if(permissions.contains(permission)) return;
        permissions.add(permission);
    }

    public void removePermission(String permission) {
        permissions.remove(permission);
    }

    public boolean hasPermission(String permission) {
        return getAllPermissions().contains(permission) || getAllPermissions().contains("*");
    }

    public void addPunishment(Punishment punishment) {
        if(!punishments.contains(punishment)) punishments.add(punishment);
    }

    public void removePunishment(Punishment punishment) {
        punishments.remove(punishment);
    }

    public void addAlt(UUID uuid) {
        alts.add(uuid);
    }

    public void removeAlt(UUID uuid) {
        alts.remove(uuid);
    }

    public List<Punishment> getActivePunishments() {
        List<Punishment> list = new ArrayList<>();
        if(punishments.isEmpty()) return list;
        for (Punishment punishment : punishments) {
            if(punishment.isActive()) {
                list.add(punishment);
            }
        }
        return list;
    }

    public Set<String> getAllPermissions() {
        Set<String> set = new HashSet<>(permissions);
        set.addAll(getCurrentRank().getAllPermissions());
        for (Grant grant : grants) {
            if(grant == null) continue;
            set.addAll(grant.getRank().getAllPermissions());
        }
        return set;
    }

    public boolean hasHigherRank(Rank rank) {
        return getCurrentRank().getWeight() - rank.getWeight() > 0;
    }
}