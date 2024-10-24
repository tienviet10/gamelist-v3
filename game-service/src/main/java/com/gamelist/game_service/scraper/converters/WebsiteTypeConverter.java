package com.gamelist.game_service.scraper.converters;

import com.gamelist.game_service.enums.*;
import jakarta.persistence.*;

@Converter(autoApply = true)
public class WebsiteTypeConverter implements AttributeConverter<WebsiteType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(WebsiteType categoryType) {
        if (categoryType == null) {
            return null;
        }
        return categoryType.getValue();
    }

    @Override
    public WebsiteType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        for (WebsiteType type : WebsiteType.values()) {
            if (type.getValue() == dbData) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}