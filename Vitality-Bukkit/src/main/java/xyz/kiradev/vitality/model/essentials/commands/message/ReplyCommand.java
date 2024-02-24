package xyz.kiradev.vitality.model.essentials.commands.message;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import xyz.kiradev.clash.command.annotation.Command;
import xyz.kiradev.clash.command.annotation.Parameter;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.util.file.LanguageLocale;
import xyz.kiradev.vitality.util.text.C;

/*
 * Copyright Kira Development© 2024
 * @project Vitality-Recoding
 * @date 2/22/2024
 */

@RequiredArgsConstructor
public class ReplyCommand {


    private final Vitality instance;

    @Command(label = "reply", aliases = "r", permission = "core.command.reply", appendStrings = true)
    public void replyCommand(Player player, @Parameter(name = "message") String message, Player target) {
        if(!instance.getMessageHandler().containsPlayer(player.getUniqueId()) && !instance.getMessageHandler().containsPlayer(target.getUniqueId())) {
            LanguageLocale.PROFILE_NOT_FOUND.sendMessage(player);
            return;
        }
        if(instance.getMessageHandler().containsPlayer(player.getUniqueId()) && instance.getMessageHandler().containsPlayer(target.getUniqueId())) {
            C.sendMessage(player, LanguageLocale.MESSAGE_TO.getString().replace("<toPlayer>", player.getName().replace("<message>", message)));
        }
    }
}
