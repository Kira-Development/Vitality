package xyz.kiradev.vitality.model.grant.listener;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 17/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import xyz.kiradev.vitality.model.grant.GrantProcess;
import xyz.kiradev.vitality.util.file.LanguageLocale;

public class ProcedureListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        GrantProcess process = GrantProcess.getByIssuer(event.getPlayer().getUniqueId());
        if(process != null) {
            event.setCancelled(true);
            GrantProcess.getGrantProcesses().remove(process);
            LanguageLocale.CANCELLED_GRANT.sendMessage(event.getPlayer());
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        GrantProcess process = GrantProcess.getByIssuer(event.getPlayer().getUniqueId());
        if(process != null) {
            event.setCancelled(true);
            GrantProcess.getGrantProcesses().remove(process);
            LanguageLocale.CANCELLED_GRANT.sendMessage(event.getPlayer());
        }
    }
}