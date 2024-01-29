package xyz.kiradev.vitality.model.rank.procedures;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/27/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import lombok.Getter;
import lombok.Setter;
import xyz.kiradev.vitality.api.model.rank.Rank;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter @Setter
public class EditProcedure {

    @Getter private static Map<UUID, EditProcedure> procedures = new ConcurrentHashMap<>();
    private final UUID issuer;
    private final Rank rank;
    private ProcedureState state;

    public EditProcedure(UUID uuid, Rank rank) {
        this.issuer = uuid;
        this.rank = rank;
        this.state = ProcedureState.NONE;

        procedures.put(uuid, this);
    }
}