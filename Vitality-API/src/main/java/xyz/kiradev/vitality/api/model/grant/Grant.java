package xyz.kiradev.vitality.api.model.grant;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 17/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.grant.scope.GrantScope;
import xyz.kiradev.vitality.api.model.rank.Rank;

import java.util.UUID;

@Getter @Setter
public class Grant {

    @SerializedName("_id")
    private final UUID uuid;

    private UUID user, issuer, rankId, revokedBy;
    private String reason, revokedReason, issuedOn, revokedOn;
    private long duration, issuedAt, revokedAt;
    private GrantScope scope;
    private boolean active;

    public Grant(UUID user, Rank rank, UUID issuer, String reason, String issuedOn, GrantScope scope, long duration) {
        this.uuid = UUID.randomUUID();
        this.user = user;
        this.rankId = rank.getUuid();
        this.issuer = issuer;
        this.reason = reason;
        this.duration = duration;
        this.issuedOn = issuedOn;
        this.issuedAt = System.currentTimeMillis();
        this.revokedBy = UUID.randomUUID();
        this.revokedReason = "";
        this.revokedOn = "";
        this.revokedAt = 0;
        this.scope = scope;
        this.active = true;
    }

    private boolean hasExpired() {
        if (duration == -1) return false;
        return (issuedAt + duration) <= System.currentTimeMillis();
    }

    public void revoke(UUID revokedBy, String revokedOn, String revokeReason) {
        this.revokedBy = revokedBy;
        this.revokedOn = revokedOn;
        this.revokedAt = System.currentTimeMillis();
        this.revokedReason = revokeReason;
        this.active = false;
    }

    public boolean check() {
        if (!hasExpired()) return false;
        if (!active) return false;
        revoke(revokedBy, "Automatic", "Expired");
        return true;
    }

    public Rank getRank() {
        return VitalityAPI.getInstance().getRankManager().getRankByUUID(rankId);
    }
}