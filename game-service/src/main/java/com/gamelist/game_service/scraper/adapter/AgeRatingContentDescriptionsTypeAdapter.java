package com.gamelist.game_service.scraper.adapter;

import com.gamelist.game_service.enums.*;
import com.google.gson.*;

import java.lang.reflect.*;

public class AgeRatingContentDescriptionsTypeAdapter implements JsonDeserializer<AgeRatingContentDescriptionType> {

    @Override
    public AgeRatingContentDescriptionType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsInt();
        AgeRatingContentDescriptionType type = AgeRatingContentDescriptionType.values()[value - 1];
        if(type == null) {
            throw new IllegalArgumentException("Unknown database value: " + value);
        }

        return type;
    }
}