package com.gamelist.game_service.scraper.converters;

import com.gamelist.game_service.enums.*;
import jakarta.persistence.*;

@Converter(autoApply = true)
public class PlatformCategoryTypeConverter implements AttributeConverter<PlatformCategoryType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PlatformCategoryType categoryType) {
        if (categoryType == null) {
            return null;
        }
        return categoryType.getValue();
    }

    @Override
    public PlatformCategoryType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }

        PlatformCategoryType type = PlatformCategoryType.values()[dbData - 1];
        if(type == null) {
            throw new IllegalArgumentException("Unknown database value: " + dbData);
        }

        return type;
    }
}