package xyz.kiradev.vitality.model.profile.staff.command.onjoin;

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
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;

public class ToggleAutoVanishCommand {

    private final Vitality instance;

    public ToggleAutoVanishCommand(Vitality instance) {
        this.instance = instance;
    }

    @Command(label = "toggleautovanish", permission = "core.command.toggleautovanish", aliases = {"tav", "toggleautov"})
    public void toggleAutoVanish(Player player) {
        Profile profile = instance.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
        profile.setVanishOnJoin(!profile.isVanishOnJoin());
    }
}
