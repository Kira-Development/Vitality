package xyz.kiradev.vitality.model.rank.menus.edit.main.buttons;

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
import xyz.kiradev.vitality.api.model.rank.Rank;
import xyz.kiradev.vitality.model.rank.procedures.EditProcedure;
import xyz.kiradev.vitality.model.rank.procedures.ProcedureState;

@RequiredArgsConstructor
public class SetSuffixButton extends Button {

    private final EditProcedure procedure;

    @Override
    public ItemStack getItem(Player player) {
        return new ItemBuilder(XMaterial.NAME_TAG.parseMaterial()).name("&eSet Suffix")
                .lore("&7Click &bhere &7to change the &bsuffix",
                        "&7",
                        "&7Current Suffix: " + procedure.getRank().getSuffix()).build();
    }

    @Override
    public boolean cancelEvent(Player player, int slot, ClickType type) {
        return true;
    }

    @Override
    public void onClick(Player player, int slot, ClickType type) {
        player.closeInventory();
        procedure.setState(ProcedureState.SET_SUFFIX);
        C.sendMessage(player, "&aPlease provide the new suffix you want for " + procedure.getRank().getDisplayName());
        C.sendMessage(player, "&cTo cancel type \"cancel\".");
    }
}
