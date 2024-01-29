package xyz.kiradev.vitality.api.model.rank;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import xyz.kiradev.vitality.api.VitalityAPI;

import java.util.*;

@Getter @Setter
public class Rank {

    @Expose @SerializedName("_id")
    private final UUID uuid;

    @Expose
    private String name, displayName, prefix, suffix;

    @Expose
    private ChatColor color;

    @Expose
    private boolean bold, italic, isDefault, staff, purchasable;

    @Expose
    private int weight, price;

    @Expose
    private Set<String> permissions;

    @Expose
    private Set<UUID> inheritance;

    public Rank(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.color = ChatColor.GRAY;
        this.displayName = color + name;
        this.prefix = "";
        this.suffix = "";
        this.bold = false;
        this.italic = false;
        this.isDefault = false;
        this.staff = false;
        this.weight = 0;
        this.price = 0;
        this.permissions = new HashSet<>();
        this.inheritance = new HashSet<>();
    }

    public String getDisplayColor() {
        return color + "" + (bold ? ChatColor.BOLD : "") + (italic ? ChatColor.ITALIC : "");
    }

    public void addPermission(String permission) {
        if(permissions.contains(permission)) return;
        permissions.add(permission);
    }

    public void removePermission(String permission) {
        permissions.remove(permission);
    }

    public void addInheritance(UUID rank) {
        if(inheritance.contains(rank)) return;
        inheritance.add(rank);
    }

    public void removeInheritance(UUID rank) {
        inheritance.remove(rank);
    }

    public Set<String> getAllPermissions() {
        Set<String> list = new HashSet<>(permissions);
        for (UUID uuid : inheritance) {
            Rank rank = VitalityAPI.getInstance().getRankManager().getRankByUUID(uuid);
            if(rank == null) continue;
            list.addAll(rank.getPermissions());
        }
        return list;
    }
}