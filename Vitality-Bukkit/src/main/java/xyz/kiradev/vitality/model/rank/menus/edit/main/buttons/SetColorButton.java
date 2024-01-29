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
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import xyz.kiradev.clash.menu.button.Button;
import xyz.kiradev.clash.utils.C;
import xyz.kiradev.clash.utils.ItemBuilder;
import xyz.kiradev.vitality.model.rank.procedures.EditProcedure;
import xyz.kiradev.vitality.model.rank.procedures.ProcedureState;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SetColorButton extends Button {

    private final EditProcedure procedure;

    @Override
    public ItemStack getItem(Player player) {
        return new ItemBuilder(new ItemStack(XMaterial.GRAY_DYE.parseMaterial(), 1, (short) 8)).name("&eSet Color")
                .lore("&7Click &bhere &7to change the &bcolor &7of the rank",
                        "&7", "&7Current Color: " + procedure.getRank().getDisplayColor() + procedure.getRank().getColor().getName().toUpperCase())
                .build();
    }

    @Override
    public boolean cancelEvent(Player player, int slot, ClickType type) {
        return true;
    }

    @Override
    public void onClick(Player player, int slot, ClickType type) {
        player.closeInventory();
        procedure.setState(ProcedureState.SET_COLOR);
        C.sendMessage(player, "&aPlease pick a color and type it in chat");
        StringBuilder builder = new StringBuilder();
        int i = 0;
        List<ChatColor> colors = Arrays.stream(ChatColor.values()).filter(ChatColor::isColor).collect(Collectors.toList());
        for (ChatColor color : colors) {
            builder.append(color).append(color.name());
            if(i >= colors.size()) {
                builder.append(C.color("&7."));
            } else {
                builder.append(C.color("&7, "));
            }
            i++;
        }
        C.sendMessage(player, builder.toString());
    }
}
