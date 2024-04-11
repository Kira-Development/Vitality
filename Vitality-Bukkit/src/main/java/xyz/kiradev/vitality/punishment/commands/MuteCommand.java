package xyz.kiradev.vitality.punishment.commands;

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
 * @date 2/24/2024
 */
public class MuteCommand {

    private final Vitality plugin;

    public MuteCommand(Vitality plugin) {
        this.plugin = plugin;
    }

    @Command(label = "mute", permission = "core.command.mute", appendStrings = true)
    public void muteCommand(CommandSender sender, @Parameter(name = "player") Player target, @Parameter(name = "reason") String reason) {
        if(!(sender instanceof Player)) {
            Profile punishedProfile = plugin.getApi().getApi().getProfileManager().getProfileByID(target.getUniqueId());
            punishedProfile.addPunishment(new Punishment(UUID.randomUUID(), UUID.randomUUID(), target.getUniqueId(), reason, PunishmentType.BAN, Long.MAX_VALUE, plugin.getConfigFile().getString("global.console-format")));
        } else {
            Player player = (Player) sender;
            Profile issuerProfile = plugin.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
            Profile punishedProfile = plugin.getApi().getApi().getProfileManager().getProfileByID(target.getUniqueId());
            punishedProfile.addPunishment(new Punishment(UUID.randomUUID(), player.getUniqueId(), target.getUniqueId(), reason, PunishmentType.BLACKLIST, Long.MAX_VALUE, issuerProfile.getCurrentServer()));
        }
    }
}