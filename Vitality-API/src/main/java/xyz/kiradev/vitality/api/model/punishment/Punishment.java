package xyz.kiradev.vitality.api.model.punishment;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 02/12/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import xyz.kiradev.vitality.api.model.punishment.type.PunishmentType;

import java.util.UUID;

@Getter @Setter
public class Punishment {

    @SerializedName("_id")
    private final UUID uuid;
    private final UUID issuer, player;
    private final PunishmentType type;
    private final long duration, addedAt;
    private final String reason, addedOn;
    private String unbanReason, unbannedOn;
    private UUID unbannedBy;
    private long unbannedAt;
    private boolean active;

    public Punishment(UUID issuer, UUID player, String reason, PunishmentType type, long duration, String addedOn) {
        this.uuid = UUID.randomUUID();
        this.issuer = issuer;
        this.player = player;
        this.reason = reason;
        this.duration = duration;
        this.addedOn = addedOn;
        this.addedAt = System.currentTimeMillis();
        this.unbannedBy = new UUID(0, 0);
        this.type = type;

        this.active = true;
    }

    public boolean isPermanent() {
        return duration == -1;
    }

    public long getRemainingTime() {
        return isPermanent() ? -1 : (addedAt + duration) - System.currentTimeMillis();
    }

    public void unban(UUID unbannedBy, String unbanReason, String unbannedOn) {
        this.unbannedBy = unbannedBy;
        this.unbannedAt = System.currentTimeMillis();
        this.unbanReason = unbanReason;
        this.unbannedOn = unbannedOn;
    }

    public boolean isValid() {
        if (unbannedBy != null) return false;
        if (isPermanent()) return false;
        if (getRemainingTime() <= 0) {
            unban(new UUID(0, 0), "Expired", "Console");
            return true;
        }
        return false;
    }
}