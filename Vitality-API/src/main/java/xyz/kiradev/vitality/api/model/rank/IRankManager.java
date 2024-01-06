package xyz.kiradev.vitality.api.model.rank;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 16/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import java.util.List;
import java.util.UUID;

public interface IRankManager {
    Rank getRankByUUID(UUID uuid);
    Rank getRankByName(String name);
    Rank getDefaultRank();

    void update(Rank rank);
    void delete(Rank rank);
    List<Rank> getRanks();
}
