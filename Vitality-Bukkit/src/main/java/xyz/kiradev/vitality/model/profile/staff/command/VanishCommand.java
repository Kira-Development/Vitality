package xyz.kiradev.vitality.model.profile.staff.command;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/31/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import netscape.security.UserTarget;
import org.bukkit.entity.Player;
import xyz.kiradev.clash.command.annotation.Command;
import xyz.kiradev.clash.command.annotation.Optional;
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
    public void vanishCommand(Player player, @Optional(value = "player") Player target) {
        Profile senderProfile = instance.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
        if (senderProfile.isVanished()) {
            senderProfile.setVanished(false);
        } else if (!senderProfile.isVanished()) {
            senderProfile.setVanished(true);
        }
    }
}
