package xyz.kiradev.vitality.model.essentials.commands.misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.kiradev.clash.command.annotation.Command;
import xyz.kiradev.clash.command.annotation.Parameter;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 17/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
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
