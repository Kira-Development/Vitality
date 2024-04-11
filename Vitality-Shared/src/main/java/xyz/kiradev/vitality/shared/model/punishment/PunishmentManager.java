package xyz.kiradev.vitality.shared.model.punishment;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import com.mongodb.client.model.Filters;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.punishment.IPunishmentManager;
import xyz.kiradev.vitality.api.model.punishment.Punishment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PunishmentManager implements IPunishmentManager {

    private final VitalityAPI api;
    @Override
    public Punishment getPunishmentByUUID(UUID uuid) {
        Document document = api.getMongoAPI().getMongoDatabase().getCollection("punishments").find(Filters.eq("_id", uuid.toString())).first();
        if(document == null) return null;

        return api.getGson().fromJson(document.toJson(), Punishment.class);
    }

    @Override
    public void update(Punishment object) {
        api.getMongoAPI().getMongoDatabase().getCollection("punishments")
                .updateOne(Filters.eq("_id", object.getUuid().toString()),
                        new Document("$set", Document.parse(api.getGson().toJson(object))),
                        api.getUpdateOptions());
    }

    @Override
    public void delete(Punishment object) {
        api.getMongoAPI().getMongoDatabase().getCollection("punishments").deleteOne(Filters.eq("_id", object.getUuid().toString()));
    }

    @Override
    public List<Punishment> getPunishments() {
        List<Punishment> list = new ArrayList<>();
        for (Document document : api.getMongoAPI().getMongoDatabase().getCollection("punishments").find()) {
            Punishment object = getPunishmentByUUID(UUID.fromString(document.getString("_id")));
            if(!list.contains(object)) list.add(object);
        }
        return list;
    }
}