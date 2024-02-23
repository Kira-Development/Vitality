package xyz.kiradev.vitality.api.util.redis.credentials;

/*
 *
 * RedisAPI is a property of MTR-Development-Team
 * It was created @ 13/08/2023
 * Coded by the founder of MTR-Development-Team
 * EmpireMTR
 *
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RedisCredentials {

    private final String hostname, channel, password;
    private final int port;

    private final boolean auth;

    public RedisCredentials(String hostname, String channel, int port) {
        this.hostname = hostname;
        this.channel = channel;
        this.port = port;
        this.auth = false;
        this.password = "HELLO";
    }

}