package com.gamelist.game_service.scraper.adapter;

import com.google.gson.*;

import java.lang.reflect.*;
import java.time.*;

public class LocalDateTimeTypeAdapter implements JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int unixTimestamp = json.getAsInt();
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp), ZoneOffset.UTC);
    }
}