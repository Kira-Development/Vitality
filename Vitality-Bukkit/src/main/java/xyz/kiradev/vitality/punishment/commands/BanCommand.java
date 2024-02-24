package xyz.kiradev.vitality.punishment.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.kiradev.clash.command.annotation.Command;
import xyz.kiradev.clash.command.annotation.Parameter;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.api.model.punishment.Punishment;
import xyz.kiradev.vitality.api.model.punishment.type.PunishmentType;

import java.util.Objects;
import java.util.UUID;

/*
 * Copyright Kira Development© 2024
 * @project Vitality-Recoding
 * @date 2/23/2024
 */
public class BanCommand {

    private final Vitality plugin;

    public BanCommand(Vitality plugin) {
        this.plugin = plugin;
    }

    @Command(label = "ban", permission = "core.command.ban", appendStrings = true)
    public void banCommand(CommandSender sender, @Parameter(name = "target") Player target, @Parameter(name = "reason") String reason) {
        Player player = (Player) sender;
        Profile issuerProfile = plugin.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
        Profile punishedProfile = plugin.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
        if(!(sender instanceof Player)) {
            new Punishment(UUID.randomUUID(), UUID.fromString("Console"), target.getUniqueId(), reason, PunishmentType.BAN, Long.MAX_VALUE, "Console");
        } else {
            new Punishment(UUID.randomUUID(), Objects.requireNonNull(((Player) sender).getPlayer()).getUniqueId(), target.getUniqueId(), reason, PunishmentType.BAN, Long.MAX_VALUE, issuerProfile.getCurrentServer().toString());
        }
    }

}
