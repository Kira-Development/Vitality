package xyz.kiradev.vitality.model.essentials.commands.message;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.kiradev.clash.command.annotation.Command;
import xyz.kiradev.clash.command.annotation.Parameter;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.util.file.LanguageLocale;
import xyz.kiradev.vitality.util.text.C;

/*
 * Copyright Kira Development© 2024
 * @project Vitality
 * @date 2/20/2024
 */

@RequiredArgsConstructor
public class MessageCommand {

    private final Vitality instance;

    @Command(label = "message", permission = "core.command.message", aliases = {"msg", "pm"}, appendStrings = true)
    public void messageCommand(Player player, @Parameter(name = "player") Player target, @Parameter(name = "message") String message) {
        C.sendMessage(target, LanguageLocale.MESSAGE_FROM.getContent().toString().replace("<fromPlayer>", player.getName().replace("<message>", message)));
        C.sendMessage(player, LanguageLocale.MESSAGE_TO.getContent().toString().replace("<toPlayer>", target.getName().replace("<message>", message)));
        instance.getMessageHandler().addMessagedPlayers(player, target);
    }

}
