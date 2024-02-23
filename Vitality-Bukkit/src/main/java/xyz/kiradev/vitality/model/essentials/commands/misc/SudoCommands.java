package xyz.kiradev.vitality.model.essentials.commands.misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;
import xyz.kiradev.clash.command.annotation.Command;
import xyz.kiradev.clash.command.annotation.Parameter;
import xyz.kiradev.vitality.util.file.LanguageLocale;

/*
 * Copyright Spear Development© 2024
 * @project Vitality-Recoding
 * @date 2/3/2024
 */
public class SudoCommands {

    @Command(label = "sudo", permission = "core.command.sudo", appendStrings = true)
    public void sudo(Player player, @Parameter(name = "player") Player target, @Parameter(name = "message") String message) {
        if (message.startsWith("c:")) {
            target.chat(message.replace("c:", ""));
        } else {
            target.performCommand(message);

        }

    }

    @Command(label = "massay", permission = "core.command.massay", appendStrings = true)
    public void massay(Player player, @Parameter(name = "message") String message) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (message.startsWith("c:")) {
                players.chat(message.replace("c:", ""));
            } else {
                players.performCommand(message);
            }
        }
    }
}
