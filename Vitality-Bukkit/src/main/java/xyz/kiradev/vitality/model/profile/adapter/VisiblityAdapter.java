package xyz.kiradev.vitality.model.profile.adapter;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/31/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;

public class VisiblityAdapter extends BukkitRunnable {

    private final Vitality instance;

    public VisiblityAdapter(Vitality instance) {
        this.instance = instance;

        instance.getServer().getScheduler().runTaskTimer(instance, this, 0, 20L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Profile profile = instance.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
            if(!profile.isVanished()) continue;
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                Profile onlinePlayerProfile = instance.getApi().getApi().getProfileManager().getProfileByID(onlinePlayers.getUniqueId());
                if(onlinePlayerProfile.getCurrentRank().isStaff()) continue;
                onlinePlayers.hidePlayer(player);
            }
        }
    }
}