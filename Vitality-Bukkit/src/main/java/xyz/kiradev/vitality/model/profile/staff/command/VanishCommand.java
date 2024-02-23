package xyz.kiradev.vitality.model.profile.staff.command;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/31/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import org.bukkit.entity.Player;
import xyz.kiradev.clash.command.annotation.Command;
import xyz.kiradev.clash.command.annotation.Parameter;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.util.file.LanguageLocale;
import xyz.kiradev.vitality.util.text.C;

public class VanishCommand {

    private final Vitality instance;

    public VanishCommand(Vitality instance) {
        this.instance = instance;
    }

    @Command(label = "vanish", permission = "core.command.vanish", aliases = {"v", "invis", "vis"})
    public void vanish(Player player, @Parameter(name = "player") Player target) {
        if (target == null) {
            Profile profile = instance.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
            profile.setVanished(!profile.isVanished());
            instance.getApi().getApi().getProfileManager().save(profile);
            if (profile.isVanished()) {
                LanguageLocale.STAFF_VANISH_ON.sendMessage(player);
                return;
            }
            LanguageLocale.STAFF_VANISH_OFF.sendMessage(player);
        } else {
            Profile profile = instance.getApi().getApi().getProfileManager().getProfileByID(target.getUniqueId());
            profile.setVanished(!profile.isVanished());
            if(profile.isVanished()) {
                C.sendMessage(player, LanguageLocale.STAFF_VANISH_OTHER_OFF.getString()
                        .replaceAll("<target>", profile.getDisplayName()));
                return;
            }
            C.sendMessage(player, LanguageLocale.STAFF_VANISH_OTHER_ON.getString()
                    .replaceAll("<target>", profile.getDisplayName()));
        }
    }
}