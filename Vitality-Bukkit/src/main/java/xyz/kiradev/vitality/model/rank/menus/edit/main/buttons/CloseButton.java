package xyz.kiradev.vitality.model.rank.menus.edit.main.buttons;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/27/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import xyz.kiradev.clash.menu.button.Button;
import xyz.kiradev.clash.utils.C;
import xyz.kiradev.clash.utils.ItemBuilder;
import xyz.kiradev.vitality.model.rank.procedures.EditProcedure;

@RequiredArgsConstructor
public class CloseButton extends Button {

    private final EditProcedure procedure;

    @Override
    public ItemStack getItem(Player player) {
        return new ItemBuilder(Material.BARRIER).name("&cSave & Exit").lore("&7Click here to &bsave your rank &7and &bexit &7the editor").build();
    }

    @Override
    public boolean cancelEvent(Player player, int slot, ClickType type) {
        return true;
    }

    @Override
    public void onClick(Player player, int slot, ClickType type) {
        player.closeInventory();
        EditProcedure.getProcedures().remove(player.getUniqueId(), procedure);
        C.sendMessage(player, "&aYou have successfully closed and saved the rank!");
    }
}
