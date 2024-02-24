package xyz.kiradev.vitality.model.rank.commands;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/27/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.kiradev.clash.command.annotation.Command;
import xyz.kiradev.clash.command.annotation.Parameter;
import xyz.kiradev.clash.command.annotation.SubCommand;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.rank.Rank;
import xyz.kiradev.vitality.model.rank.menus.edit.main.RankEditMenu;
import xyz.kiradev.vitality.model.rank.procedures.EditProcedure;
import xyz.kiradev.vitality.util.file.LanguageLocale;
import xyz.kiradev.vitality.util.text.C;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RankCommands {

    private final Vitality plugin;

    @Command(label = "rank", permission = "core.command.rank")
    public void help(CommandSender sender) {
        C.sendMessage(sender, LanguageLocale.COMMAND_HELP.getString()
                .replaceAll("<commandName>", "Rank"));
        C.sendMessage(sender, LanguageLocale.COMMAND_ENTRY.getString()
                .replaceAll("<command>", "rank").replaceAll("<args>", "create <rank name>"));
        C.sendMessage(sender, LanguageLocale.COMMAND_ENTRY.getString()
                .replaceAll("<command>", "rank").replaceAll("<args>", "delete <rank name>"));
        C.sendMessage(sender, LanguageLocale.COMMAND_ENTRY.getString()
                .replaceAll("<command>", "rank").replaceAll("<args>", "edit <rank name>"));
        C.sendMessage(sender, LanguageLocale.COMMAND_ENTRY.getString()
                .replaceAll("<command>", "rank").replaceAll("<args>", "list"));
        //C.sendMessage(sender, LanguageLocale.COMMAND_ENTRY.getString()
        //        .replaceAll("<command>", "rank").replaceAll("<args>", "export"));
        //C.sendMessage(sender, LanguageLocale.COMMAND_ENTRY.getString()
        //        .replaceAll("<command>", "rank").replaceAll("<args>", "import"));
    }

    @SubCommand(label = "create", parent = "rank", permission = "core.command.rank")
    public void create(CommandSender sender, @Parameter(name = "rank name") String rankName) {
        if(plugin.getApi().getApi().getRankManager().getRankByName(rankName) != null) {
            LanguageLocale.RANK_ALREADY_EXISTS.sendMessage(sender);
            return;
        }

        Rank rank = new Rank(rankName);
        plugin.getApi().getApi().getRankManager().update(rank);
        C.sendMessage(sender, LanguageLocale.CREATED_RANK.getString().replaceAll("<name>", rank.getDisplayName()));
    }

    @SubCommand(label = "delete", parent = "rank", permission = "core.command.rank")
    public void delete(CommandSender sender, @Parameter(name = "rank name") String rankName) {
        Rank rank = plugin.getApi().getApi().getRankManager().getRankByName(rankName);
        if(rank == null) {
            LanguageLocale.RANK_DOESNT_EXISTS.sendMessage(sender);
            return;
        }
        if(rank.isDefault()) {
            C.sendMessage(sender, "&cYou cannot delete a default rank.");
            return;
        }

        C.sendMessage(sender, LanguageLocale.DELETED_RANK.getString().replaceAll("<name>", rank.getDisplayName()));
        plugin.getApi().getApi().getRankManager().delete(rank);
    }

    @SubCommand(label = "edit", parent = "rank", permission = "core.command.rank")
    public void edit(Player sender, @Parameter(name = "rank name") String rankName) {
        Rank rank = plugin.getApi().getApi().getRankManager().getRankByName(rankName);
        if(rank == null) {
            LanguageLocale.RANK_DOESNT_EXISTS.sendMessage(sender);
            return;
        }

        new RankEditMenu(new EditProcedure(sender.getUniqueId(), rank)).openMenu(sender);
    }

    @SubCommand(label = "list", parent = "rank", permission = "core.command.rank")
    public void list(CommandSender sender) {
        StringBuilder builder = new StringBuilder();
        List<Rank> ranksSorted = new ArrayList<>(plugin.getApi().getApi().getRankManager().getRanks());
        ranksSorted.sort(Comparator.comparingInt(Rank::getWeight).reversed());
        for (int i = 0; i < ranksSorted.size(); i++) {
            Rank rank = ranksSorted.get(i);
            builder.append(rank.getDisplayName());
            if(i >= plugin.getApi().getApi().getRankManager().getRanks().size()) {
                builder.append("&7.");
            } else {
                builder.append("&7, ");
            }
        }

        C.sendMessage(sender, builder.toString());
    }
}
