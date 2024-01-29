package xyz.kiradev.vitality.model.grant;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 17/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import lombok.Getter;
import lombok.Setter;
import xyz.kiradev.vitality.api.model.rank.Rank;
import xyz.kiradev.vitality.model.grant.stage.GrantStage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GrantProcess {

    @Getter private static final List<GrantProcess> grantProcesses = new ArrayList<>();

    private final UUID user, issuer;
    private GrantStage grantStage = GrantStage.RANK;
    private Rank rank;
    private long duration, executedAt;

    public GrantProcess(UUID user, UUID issuer) {
        this.user = user;
        this.issuer = issuer;
        grantProcesses.add(this);
    }

    public static GrantProcess getByIssuer(UUID uuid) {
        return grantProcesses.stream().filter(grantProcedure -> grantProcedure.getIssuer() == uuid)
                .findFirst().orElse(null);
    }
}