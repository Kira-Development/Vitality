package xyz.kiradev.vitality.shared.profile;

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
import xyz.kiradev.vitality.api.model.profile.IProfileManager;
import xyz.kiradev.vitality.api.model.profile.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProfileManager implements IProfileManager {

    private final VitalityAPI api;

    @Override
    public Profile getProfileByID(UUID uuid) {
        Document document = api.getMongoAPI().getMongoDatabase().getCollection("profiles").find(Filters.eq("_id", uuid.toString())).first();
        if(document == null) return null;

        return api.getGson().fromJson(document.toJson(), Profile.class);
    }

    @Override
    public Profile getProfileByName(String name) {
        return getProfiles().stream().filter(profile -> profile.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public void save(Profile profile) {
        api.getMongoAPI().getMongoDatabase().getCollection("profiles")
                .updateOne(Filters.eq("_id", profile.getUuid().toString()),
                        new Document("$set", Document.parse(api.getGson().toJson(profile))),
                        api.getUpdateOptions());
    }

    @Override
    public void delete(Profile profile) {
        api.getMongoAPI().getMongoDatabase().getCollection("profiles").deleteOne(Filters.eq("_id", profile.getUuid().toString()));
    }

    @Override
    public List<Profile> getProfiles() {
        List<Profile> profiles = new ArrayList<>();
        for (Document document : api.getMongoAPI().getMongoDatabase().getCollection("profiles").find()) {
            Profile profile = getProfileByID(UUID.fromString(document.getString("_id")));
            if(!profiles.contains(profile)) profiles.add(profile);
        }
        return profiles;
    }
}
