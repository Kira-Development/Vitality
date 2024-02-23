package xyz.kiradev.vitality.api.model.grant;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 17/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import java.util.List;
import java.util.UUID;

public interface IGrantManager {
    Grant getGrantByUUID(UUID uuid);
    void update(Grant grant);
    void delete(Grant grant);
    List<Grant> getGrants();
}