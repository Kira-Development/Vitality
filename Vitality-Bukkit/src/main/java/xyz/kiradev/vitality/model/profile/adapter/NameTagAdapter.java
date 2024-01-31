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
import xyz.kiradev.clash.nametag.NameTagAPI;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.util.text.C;

public class NameTagAdapter implements xyz.kiradev.clash.nametag.adapter.NameTagAdapter {

    private final Vitality instance;

    public NameTagAdapter(Vitality instance) {
        this.instance = instance;

        if(instance.getConfig().getBoolean("global.allow-nametags")) {
            new NameTagAPI(instance, this, Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    @Override
    public String getPrefix(Player player) {
        Profile profile = instance.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
        String finalPrefix;

        if(profile.isVanished()) {
            finalPrefix = instance.getConfig().getString("global.vanish-tag-value");
        } else if(profile.isStaffMode()) {
            finalPrefix = instance.getConfig().getString("global.staff-mode-tag-value");
        } else {
            finalPrefix = instance.getConfig().getString("global.default-tag").replace("<rank_color>", profile.getCurrentRank().getDisplayColor())
                    .replace("<rank_prefix>", profile.getCurrentRank().getPrefix());
        }
        return C.color(finalPrefix);
    }

    @Override
    public String getSuffix(Player player) {
        return "";
    }
}
