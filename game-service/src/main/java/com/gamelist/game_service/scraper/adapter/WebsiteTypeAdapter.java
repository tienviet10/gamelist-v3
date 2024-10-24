package com.gamelist.game_service.scraper.adapter;

import com.gamelist.game_service.enums.*;
import com.google.gson.*;

import java.lang.reflect.*;

public class WebsiteTypeAdapter implements JsonDeserializer<WebsiteType> {

    @Override
    public WebsiteType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsInt();
        for (WebsiteType type : WebsiteType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new JsonParseException("Unknown element value: " + value);
    }
}