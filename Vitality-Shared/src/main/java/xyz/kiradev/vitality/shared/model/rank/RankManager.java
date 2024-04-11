package xyz.kiradev.vitality.shared.model.rank;

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
import xyz.kiradev.vitality.api.model.rank.IRankManager;
import xyz.kiradev.vitality.api.model.rank.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class RankManager implements IRankManager {

    private final VitalityAPI api;

    @Override
    public Rank getRankByUUID(UUID uuid) {
        Document document = api.getMongoAPI().getMongoDatabase().getCollection("ranks").find(Filters.eq("_id", uuid.toString())).first();
        if(document == null) return null;

        return api.getGson().fromJson(document.toJson(), Rank.class);
    }

    @Override
    public Rank getRankByName(String name) {
        return getRanks().stream().filter(rank -> rank.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public Rank getDefaultRank() {
        return getRanks().stream().filter(Rank::isDefault).findFirst().orElse(null);
    }
    @Override
    public void update(Rank rank) {
        api.getMongoAPI().getMongoDatabase().getCollection("ranks")
                .updateOne(Filters.eq("_id", rank.getUuid().toString()),
                        new Document("$set", Document.parse(api.getGson().toJson(rank))),
                        api.getUpdateOptions());
    }

    @Override
    public void delete(Rank rank) {
        api.getMongoAPI().getMongoDatabase().getCollection("ranks").deleteOne(Filters.eq("_id", rank.getUuid().toString()));
    }

    @Override
    public List<Rank> getRanks() {
        List<Rank> list = new ArrayList<>();
        for (Document document : api.getMongoAPI().getMongoDatabase().getCollection("ranks").find()) {
            Rank object = getRankByUUID(UUID.fromString(document.getString("_id")));
            if(!list.contains(object)) list.add(object);
        }
        return list;
    }
}
