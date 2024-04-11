package xyz.kiradev.vitality.punishment.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.kiradev.clash.command.annotation.Command;
import xyz.kiradev.clash.command.annotation.Optional;
import xyz.kiradev.clash.command.annotation.Parameter;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.api.model.punishment.Punishment;
import xyz.kiradev.vitality.api.model.punishment.type.PunishmentType;
import xyz.kiradev.vitality.util.file.LanguageLocale;
import xyz.kiradev.vitality.util.text.C;

import java.util.Objects;
import java.util.UUID;

/*
 * Copyright Kira Development© 2024
 * @project Vitality-Recoding
 * @date 2/23/2024
 */
public class BanCommand {

    private final Vitality plugin;
    private boolean silent;

    public BanCommand(Vitality plugin) {
        this.plugin = plugin;
    }

    @Command(label = "ban", permission = "core.command.ban", appendStrings = true)
    public void banCommand(CommandSender sender, @Parameter(name = "player") Player target, @Parameter(name = "reason") String reason, @Optional(value = "silent") String args) {
        if (args.contains("-s") || args.contains("-S")) {
            silent = true;
        } else {
            silent = false;
            return;
        }
        if (!(sender instanceof Player)) {
            Profile punishedProfile = plugin.getApi().getApi().getProfileManager().getProfileByID(target.getUniqueId());
            Punishment punishment = new Punishment(new UUID(0, 0), target.getUniqueId(), reason, PunishmentType.BAN, Long.MAX_VALUE, plugin.getConfigFile().getString("global.console-format"));
            punishedProfile.addPunishment(punishment);
            punishedProfile.
            if(silent) {
                C.broadcastRedisMessage(LanguageLocale.BAN_BROADCAST.getString().replaceAll("<targetPlayer>", target.getDisplayName().replaceAll("<silent>", LanguageLocale.SILENT_FORMAT.getString())));
            } else {

            }
        } else {
            Player player = (Player) sender;
            Profile issuerProfile = plugin.getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
            Profile punishedProfile = plugin.getApi().getApi().getProfileManager().getProfileByID(target.getUniqueId());
            punishedProfile.addPunishment(new Punishment(UUID.randomUUID(), player.getUniqueId(), target.getUniqueId(), reason, PunishmentType.BAN, Long.MAX_VALUE, issuerProfile.getCurrentServer()));
        }
    }
}