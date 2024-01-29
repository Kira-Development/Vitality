package xyz.kiradev.vitality.model.rank.menus.edit.main;

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
import xyz.kiradev.vitality.model.rank.procedures.EditProcedure;
import xyz.kiradev.vitality.model.rank.menus.edit.main.buttons.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class RankEditMenu extends Menu {

    private final EditProcedure procedure;

    @Override
    public String getTitle(Player player) {
        return procedure.getRank().getDisplayName();
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(0, new SetDisplayNameButton(procedure));
        buttons.put(1, new SetPrefixButton(procedure));
        buttons.put(2, new SetSuffixButton(procedure));

        buttons.put(3, new SetColorButton(procedure));
        buttons.put(4, new SetWeightButton(procedure));
        buttons.put(5, new SetPermissionsButton(procedure));

        buttons.put(6, new SetInheritanceButton(procedure));
        buttons.put(7, new SetMetadatasButton(procedure));
        buttons.put(8, new CloseButton(procedure));

        return buttons;
    }
}
