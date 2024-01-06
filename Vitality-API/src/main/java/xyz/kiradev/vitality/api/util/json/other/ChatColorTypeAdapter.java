package xyz.kiradev.vitality.api.util.json.other;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import com.google.gson.*;
import net.md_5.bungee.api.ChatColor;

import java.lang.reflect.Type;

public class ChatColorTypeAdapter implements JsonSerializer<ChatColor>, JsonDeserializer<ChatColor> {

    @Override
    public ChatColor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json.getAsString().isEmpty()) return null;
        return ChatColor.of(json.getAsString());
    }

    @Override
    public JsonElement serialize(ChatColor src, Type typeOfSrc, JsonSerializationContext context) {
        String finalPrimitive = src == null ? "" : src.getName();
        return new JsonPrimitive(finalPrimitive);
    }
}
