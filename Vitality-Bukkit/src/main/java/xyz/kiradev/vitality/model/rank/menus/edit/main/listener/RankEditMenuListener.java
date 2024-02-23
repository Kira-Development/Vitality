package xyz.kiradev.vitality.model.rank.menus.edit.main.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import xyz.kiradev.clash.utils.C;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.rank.Rank;
import xyz.kiradev.vitality.model.rank.menus.edit.main.RankEditMenu;
import xyz.kiradev.vitality.model.rank.procedures.EditProcedure;
import xyz.kiradev.vitality.model.rank.procedures.ProcedureState;
import xyz.kiradev.vitality.util.file.LanguageLocale;

import java.util.Arrays;


/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/27/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

public class RankEditMenuListener implements Listener {

    private final Vitality plugin;

    public RankEditMenuListener(Vitality plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        EditProcedure procedure = EditProcedure.getProcedures().get(player.getUniqueId());
        if(procedure == null) return;
        event.setCancelled(true);
        if(event.getMessage().equalsIgnoreCase("cancel") && procedure.getState() != ProcedureState.NONE) {
            procedure.setState(ProcedureState.NONE);
            new RankEditMenu(procedure);
            C.sendMessage(player, "&cYou have successfully cancelled this action!");
            event.setCancelled(true);
        }
        switch (procedure.getState()) {
            case NONE:
                EditProcedure.getProcedures().remove(player.getUniqueId(), procedure);
                event.setCancelled(false);
                break;
            case SET_DISPLAY_NAME:
                procedure.getRank().setDisplayName(C.color(event.getMessage()));
                C.sendMessage(player, "&aYou have successfully set the display name of " + procedure.getRank().getDisplayName() + " &ato &e" + event.getMessage());
                new RankEditMenu(procedure).openMenu(player);
                break;
            case SET_PREFIX:
                procedure.getRank().setPrefix(C.color(event.getMessage()));
                C.sendMessage(player, "&aYou have successfully set the prefix of " + procedure.getRank().getDisplayName() + " &ato &e" + event.getMessage());
                new RankEditMenu(procedure).openMenu(player);
                break;
            case SET_SUFFIX:
                procedure.getRank().setSuffix(C.color(event.getMessage()));
                C.sendMessage(player, "&aYou have successfully set the suffix of " + procedure.getRank().getDisplayName() + " &ato &e" + event.getMessage());
                new RankEditMenu(procedure).openMenu(player);
                break;
            case SET_COLOR:
                ChatColor color = Arrays.stream(ChatColor.values()).filter(color1 -> color1.name().equalsIgnoreCase(event.getMessage())).findFirst().orElse(null);
                if(color == null || !color.isColor()) {
                    C.sendMessage(player, "&cThat color does not exist. Please pick a valid color!");
                } else {
                    C.sendMessage(player, "&aYou have successfully set the rank's color to " + color + color.name());
                    procedure.getRank().setColor(color.asBungee());
                    new RankEditMenu(procedure).openMenu(player);
                }
                break;
            case SET_WEIGHT:
                try {
                    procedure.getRank().setWeight(Integer.parseInt(event.getMessage()));
                    C.sendMessage(player, "&aYou have successfully set the weight of " + procedure.getRank().getDisplayName() + " &ato &e" + event.getMessage());
                    new RankEditMenu(procedure).openMenu(player);
                } catch (Exception ignored) {
                    C.sendMessage(player, "&cPlease provide a number!");
                }
                break;
            case SET_PRICE:
                try {
                    procedure.getRank().setPrice(Integer.parseInt(event.getMessage()));
                    C.sendMessage(player, "&aYou have successfully set the price of " + procedure.getRank().getDisplayName() + " &ato &e" + event.getMessage());
                    new RankEditMenu(procedure).openMenu(player);
                } catch (Exception ignored) {
                    C.sendMessage(player, "&cPlease provide a number!");
                }
                break;
            case ADD_PERMISSION:
                procedure.getRank().addPermission(event.getMessage());
                C.sendMessage(player, "&aYou have successfully added the permission &e" + event.getMessage() + " &ato " + procedure.getRank().getDisplayName());
                new RankEditMenu(procedure).openMenu(player);
                break;
            case ADD_INHERITANCE: {
                Rank rank = plugin.getApi().getApi().getRankManager().getRankByName(event.getMessage());
                if (rank == null) {
                    C.sendMessage(player, LanguageLocale.RANK_DOESNT_EXISTS.getString());
                    return;
                }
                if (procedure.getRank().getInheritance().contains(rank.getUuid())) {
                    C.sendMessage(player, "&cThis rank already inherit " + rank.getDisplayName());
                    return;
                }
                procedure.getRank().addInheritance(rank.getUuid());
                C.sendMessage(player, "&aYou have successfully inherited " + rank.getDisplayName() + " &ato " + procedure.getRank().getDisplayName());
                new RankEditMenu(procedure).openMenu(player);
            }
                break;
            default:
                return;
        }
        plugin.getApi().getApi().getRankManager().update(procedure.getRank());
        procedure.setState(ProcedureState.NONE);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        EditProcedure procedure = EditProcedure.getProcedures().get(player.getUniqueId());
        if(procedure == null) return;
        EditProcedure.getProcedures().remove(player.getUniqueId(), procedure);
    }
}