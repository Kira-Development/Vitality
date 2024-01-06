package xyz.kiradev.vitality.api.model.profile;

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

public interface IProfileManager {
    Profile getProfileByID(UUID uuid);
    Profile getProfileByName(String name);

    void save(Profile profile);
    void delete(Profile profile);
    List<Profile> getProfiles();
}