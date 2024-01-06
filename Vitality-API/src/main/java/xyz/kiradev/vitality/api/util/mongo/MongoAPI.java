package xyz.kiradev.vitality.api.util.mongo;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

import java.util.Collections;

@Getter
public class MongoAPI {

    private final MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private final MongoCredentials credentials;

    public MongoAPI(MongoCredentials credentials) {
        this.credentials = credentials;
        if (credentials.isUriMode()) {
            mongoClient = new MongoClient(new MongoClientURI(credentials.getUri()));
        } else {
            if (credentials.isAuth()) {
                MongoCredential credential = MongoCredential.createCredential(credentials.getUser(), credentials.getDatabase(), credentials.getPassword().toCharArray());
                mongoClient = new MongoClient(new ServerAddress(credentials.getHost(), credentials.getPort()), Collections.singletonList(credential));
            } else {
                mongoClient = new MongoClient(credentials.getHost(), credentials.getPort());
            }
            mongoDatabase = mongoClient.getDatabase(credentials.getDatabase());
        }
    }
}
