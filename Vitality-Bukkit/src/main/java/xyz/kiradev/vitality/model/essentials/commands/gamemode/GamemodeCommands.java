package xyz.kiradev.vitality.model.essentials.commands.gamemode;

import com.sun.org.apache.bcel.internal.generic.ARETURN;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.kiradev.clash.command.annotation.Command;
import xyz.kiradev.clash.command.annotation.Optional;
import xyz.kiradev.clash.command.annotation.Parameter;
import xyz.kiradev.vitality.util.file.LanguageLocale;
import xyz.kiradev.vitality.util.text.C;

/*
 * Copyright Spear Development© 2024
 * @project Vitality-Recoding
 * @date 2/3/2024
 */
public class GamemodeCommands {


    @Command(label = "gamemode", aliases = {"gm", "mode"}, permission = "core.command.gamemode")
    public void gamemodeCommand(Player player, String gameMode, @Optional(value = "player") Player target) {
        GameMode gameModeFinal = GameMode.valueOf(gameMode.toUpperCase());
        if (gameMode.equalsIgnoreCase("c"))
            gameModeFinal = GameMode.CREATIVE;
        if (gameMode.equalsIgnoreCase("s"))
            gameModeFinal = GameMode.SURVIVAL;
        if (gameMode.equalsIgnoreCase("sp"))
            gameModeFinal = GameMode.SPECTATOR;
        if (gameMode.equalsIgnoreCase("spec"))
            gameModeFinal = GameMode.SPECTATOR;
        if (gameMode.equalsIgnoreCase("a"))
            gameModeFinal = GameMode.ADVENTURE;
        if (gameModeFinal != null) {
            if (target == null) {
                player.setGameMode(gameModeFinal);
                C.broadcastLocalStaffLog(LanguageLocale.GAMEMODE_CHANGE_LOG.getContent().toString().replaceAll("<gamemode>", gameModeFinal.toString()));
                return;
            }
            target.setGameMode(gameModeFinal);
        }
    }

    @Command(label = "gmc", aliases = {"gm1"}, permission = "core.command.gamemode")
    public void gmcCommand(Player player, @Optional(value = "player") Player target) {
        if (target == null) {
            player.setGameMode(GameMode.CREATIVE);
            C.sendMessage(player, LanguageLocale.GAMEMODE_CHANGE.getContent().toString().replace("<gamemode>", "CREATIVE"));
            return;
        }
        target.setGameMode(GameMode.CREATIVE);
    }

    @Command(label = "gms", aliases = {"gm0"}, permission = "core.command.gamemode")
    public void gmsCommand(Player player, @Optional(value = "player") Player target) {
        if (target == null) {
            player.setGameMode(GameMode.SURVIVAL);
            C.sendMessage(player, LanguageLocale.GAMEMODE_CHANGE.getContent().toString().replace("<gamemode>", "SURVIVAL"));
            return;
        }
        target.setGameMode(GameMode.SURVIVAL);
    }

    @Command(label = "gmsp", aliases = {"gm3"}, permission = "core.command.gamemode")
    public void gmspCommand(Player player, @Optional(value = "player") Player target) {
        if (target == null) {
            player.setGameMode(GameMode.SPECTATOR);
            C.sendMessage(player, LanguageLocale.GAMEMODE_CHANGE.getContent().toString().replace("<gamemode>", "SPECTATOR"));
            return;
        }
        target.setGameMode(GameMode.SPECTATOR);
    }
    @Command(label = "gma", aliases = {"gm2"}, permission = "core.command.gamemode")
    public void gmaCommand(Player player, @Optional(value = "player") Player target) {
        if (target == null) {
            player.setGameMode(GameMode.ADVENTURE);
            C.sendMessage(player, LanguageLocale.GAMEMODE_CHANGE.getContent().toString().replace("<gamemode>", "ADVENTURE"));
            return;
        }
        target.setGameMode(GameMode.ADVENTURE);
    }

}