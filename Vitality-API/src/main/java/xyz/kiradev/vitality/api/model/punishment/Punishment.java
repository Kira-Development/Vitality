package xyz.kiradev.vitality.api.model.punishment;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 02/12/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import xyz.kiradev.vitality.api.model.punishment.type.PunishmentType;

import java.util.UUID;

@Getter @Setter
public class Punishment {

    @Expose @SerializedName("_id")
    private final UUID uuid;

    @Expose
    private final UUID issuer, player;

    @Expose
    private final PunishmentType type;

    @Expose
    private final long duration, addedAt;

    @Expose
    private final String reason, addedOn;

    @Expose
    private String unbanReason, unbannedOn;

    @Expose
    private UUID unbannedBy;

    @Expose
    private long unbannedAt;

    @Expose
    private boolean active;

    public Punishment(UUID uuid, UUID issuer, UUID player, String reason, PunishmentType type, long duration, String addedOn) {
        this.uuid = uuid;
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