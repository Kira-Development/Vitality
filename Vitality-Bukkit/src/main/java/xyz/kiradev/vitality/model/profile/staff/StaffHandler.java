package xyz.kiradev.vitality.model.profile.staff;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/31/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import org.bukkit.entity.Player;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;

public class StaffHandler {

    private final Vitality instance;

    public StaffHandler(Vitality instance) {
        this.instance = instance;
    }

    public boolean isVanished(Player player) {
        Profile profile = instance.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
        return profile.isVanished();
    }
    public boolean isVanishedOnJoin(Player player) {
        Profile profile = instance.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
        return profile.isVanishOnJoin();
    }

    public boolean isModModed(Player player) {
        Profile profile = instance.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
        return profile.isStaffMode();
    }
    public boolean isModModedOnJoin(Player player) {
        Profile profile = instance.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
        return profile.isStaffModeOnJoin();
    }
}