package xyz.kiradev.vitality.shared.model.grant;

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
import xyz.kiradev.vitality.api.model.grant.Grant;
import xyz.kiradev.vitality.api.model.grant.IGrantManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GrantManager implements IGrantManager {

    private final VitalityAPI api;

    @Override
    public Grant getGrantByUUID(UUID uuid) {
        Document document = api.getMongoAPI().getMongoDatabase().getCollection("grants").find(Filters.eq("_id", uuid.toString())).first();
        if(document == null) return null;

        return api.getGson().fromJson(document.toJson(), Grant.class);
    }

    @Override
    public void update(Grant grant) {
        api.getMongoAPI().getMongoDatabase().getCollection("grants")
                .updateOne(Filters.eq("_id", grant.getUuid().toString()),
                        new Document("$set", Document.parse(api.getGson().toJson(grant))),
                        api.getUpdateOptions());
    }

    @Override
    public void delete(Grant grant) {
        api.getMongoAPI().getMongoDatabase().getCollection("grants").deleteOne(Filters.eq("_id", grant.getUuid().toString()));
    }

    @Override
    public List<Grant> getGrants() {
        List<Grant> list = new ArrayList<>();
        for (Document document : api.getMongoAPI().getMongoDatabase().getCollection("grants").find()) {
            Grant grant = getGrantByUUID(UUID.fromString(document.getString("_id")));
            if(!list.contains(grant)) list.add(grant);
        }
        return list;
    }
}
