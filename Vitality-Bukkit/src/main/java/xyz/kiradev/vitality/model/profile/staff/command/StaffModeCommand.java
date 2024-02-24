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
import xyz.kiradev.clash.utils.LocationUtil;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.api.model.server.enums.ServerType;
import xyz.kiradev.vitality.util.file.LanguageLocale;

public class StaffModeCommand {

    private final Vitality instance;

    public StaffModeCommand(Vitality instance) {
        this.instance = instance;
    }


    @Command(label = "staffmode", aliases = {"sm", "mm", "modmode", "h", "hacker"}, permission = "core.command.staffmode")
    public void vanish(Player player, @Parameter(name = "player") Player target) {
        if(instance.getCurrentServer().getType() == ServerType.HUB) {
            LanguageLocale.STAFF_NO_STAFFMODE_ON_HUB.sendMessage(player);
        }
        if (instance.getStaffHandler().isVanished(player)) {
            Profile profile = instance.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
            profile.setVanished(true);
        } else {
            Profile profile = instance.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
            profile.setVanished(false);
        }
    }
}