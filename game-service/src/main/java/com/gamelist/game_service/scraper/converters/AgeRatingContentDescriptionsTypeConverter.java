package com.gamelist.game_service.scraper.converters;

import com.gamelist.game_service.enums.*;
import jakarta.persistence.*;

@Converter(autoApply = true)
public class AgeRatingContentDescriptionsTypeConverter implements AttributeConverter<AgeRatingContentDescriptionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AgeRatingContentDescriptionType categoryType) {
        if (categoryType == null) {
            return null;
        }
        return categoryType.getValue();
    }

    @Override
    public AgeRatingContentDescriptionType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }

        AgeRatingContentDescriptionType type = AgeRatingContentDescriptionType.values()[dbData - 1];
        if(type == null) {
            throw new IllegalArgumentException("Unknown database value: " + dbData);
        }

        return type;
    }
}