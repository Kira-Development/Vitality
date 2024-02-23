package xyz.kiradev.vitality.model.rank.menus.edit.metadata.buttons;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/27/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import com.cryptomorin.xseries.XMaterial;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import xyz.kiradev.clash.menu.button.Button;
import xyz.kiradev.clash.utils.C;
import xyz.kiradev.clash.utils.ItemBuilder;
import xyz.kiradev.clash.utils.extras.SymbolUtil;
import xyz.kiradev.vitality.model.rank.procedures.EditProcedure;
import xyz.kiradev.vitality.model.rank.procedures.ProcedureState;

@RequiredArgsConstructor
public class SetPriceButton extends Button {

    private final EditProcedure procedure;

    @Override
    public ItemStack getItem(Player player) {
        return new ItemBuilder(XMaterial.BLAZE_ROD.parseMaterial()).name("&ePrice")
                .lore("&7Current Price: &e" + procedure.getRank().getPrice() + " coins")
                .build();
    }

    @Override
    public boolean cancelEvent(Player player, int slot, ClickType type) {
        return true;
    }

    @Override
    public void onClick(Player player, int slot, ClickType type) {
        procedure.setState(ProcedureState.SET_PRICE);
        player.closeInventory();
        C.sendMessage(player, "&aPlease provide the new price you want for " + procedure.getRank().getDisplayName());
        C.sendMessage(player, "&cTo cancel type \"cancel\".");
    }
}