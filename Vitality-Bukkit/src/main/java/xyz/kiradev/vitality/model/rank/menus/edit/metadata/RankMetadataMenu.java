package xyz.kiradev.vitality.model.rank.menus.edit.metadata;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/27/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import xyz.kiradev.clash.menu.Menu;
import xyz.kiradev.clash.menu.button.Button;
import xyz.kiradev.vitality.model.rank.menus.edit.metadata.buttons.*;
import xyz.kiradev.vitality.model.rank.procedures.EditProcedure;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class RankMetadataMenu extends Menu {

    private final EditProcedure procedure;

    @Override
    public String getTitle(Player player) {
        return procedure.getRank().getDisplayName() + " &eMeta-data";
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(1, new SetBoldButton(procedure));
        buttons.put(2, new SetItalicButton(procedure));

        buttons.put(4, new SetStaffButton(procedure));

        buttons.put(6, new SetPurchasableButton(procedure));
        buttons.put(7, new SetPriceButton(procedure));

        return buttons;
    }
}
