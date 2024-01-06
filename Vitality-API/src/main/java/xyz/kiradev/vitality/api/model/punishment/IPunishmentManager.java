package xyz.kiradev.vitality.api.model.punishment;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 02/12/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import java.util.List;
import java.util.UUID;

public interface IPunishmentManager {
    Punishment getPunishmentByUUID(UUID uuid);
    void update(Punishment uuid);
    void delete(Punishment uuid);
    List<Punishment> getPunishments();
}