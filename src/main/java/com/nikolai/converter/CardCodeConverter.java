package com.nikolai.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CardCodeConverter implements AttributeConverter<Integer, String> {

    @Override
    public String convertToDatabaseColumn(Integer attribute) {
        return attribute + "";
    }

    @Override
    public Integer convertToEntityAttribute(String dbData) {
        return Integer.parseInt(dbData);
    }
}
