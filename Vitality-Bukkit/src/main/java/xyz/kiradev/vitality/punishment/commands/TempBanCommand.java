package xyz.kiradev.vitality.punishment.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.kiradev.clash.command.annotation.Command;
import xyz.kiradev.clash.command.annotation.Parameter;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.api.model.punishment.Punishment;
import xyz.kiradev.vitality.api.model.punishment.type.PunishmentType;

import java.util.UUID;

/*
 * Copyright Kira Development© 2024
 * @project Vitality-Recoding
 * @date 2/23/2024
 */
public class TempBanCommand {

    private final Vitality plugin;

    public TempBanCommand(Vitality plugin) {
        this.plugin = plugin;
    }


    @Command(label = "tempban", permission = "core.command.tempban", aliases = "tb", appendStrings = true)
    public void tempBanCommand(CommandSender sender, @Parameter(name = "player") Player target, @Parameter(name = "reason") String reason, @Parameter(name = "duration") long duration) {
        if(!(sender instanceof Player)) {
        new Punishment(UUID.randomUUID(), UUID.randomUUID(), target.getUniqueId(), reason, PunishmentType.BAN, duration, plugin.getConfigFile().getString("global.console-format"));
    } else {
            Player player = (Player) sender;
            Profile profile = plugin.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
            new Punishment(UUID.randomUUID(), player.getUniqueId(), target.getUniqueId(), reason, PunishmentType.BAN, duration, profile.getCurrentServer());
        }
    }
}
