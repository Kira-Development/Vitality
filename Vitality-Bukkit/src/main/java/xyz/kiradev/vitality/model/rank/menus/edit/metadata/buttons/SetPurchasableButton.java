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
import xyz.kiradev.clash.utils.ItemBuilder;
import xyz.kiradev.clash.utils.extras.SymbolUtil;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.model.rank.procedures.EditProcedure;

@RequiredArgsConstructor
public class SetPurchasableButton extends Button {

    private final EditProcedure procedure;

    @Override
    public ItemStack getItem(Player player) {
        boolean isPurchasable = procedure.getRank().isPurchasable();
        return new ItemBuilder(XMaterial.GOLD_INGOT.parseMaterial()).name("&ePurchasable")
                .lore(isPurchasable ? "&a" + SymbolUtil.ARROW_RIGHT + " Enabled" : "&7" + SymbolUtil.DOT + " Enabled",
                        isPurchasable ? "&7" + SymbolUtil.DOT + " Disabled" : "&c" + SymbolUtil.ARROW_RIGHT + " Disabled")
                .build();
    }

    @Override
    public boolean cancelEvent(Player player, int slot, ClickType type) {
        return true;
    }

    @Override
    public boolean updateOnClick(Player player, int slot, ClickType type) {
        return true;
    }

    @Override
    public void onClick(Player player, int slot, ClickType type) {
        boolean isPurchasable = procedure.getRank().isPurchasable();
        procedure.getRank().setPurchasable(!isPurchasable);
        Vitality.getInstance().getApi().getApi().getRankManager().update(procedure.getRank());
    }
}