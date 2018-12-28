package com.cargo.ships.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * This custom serializer is used for serializing
 * String properties to JSON integers whenever possible.
 * Otherwise the property is serialized as String.
 *
 * @author Stoimen Stoimenov
 */
public class TonnageSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(
            String value,
            JsonGenerator gen,
            SerializerProvider serializers
    ) throws IOException {
        try {
            gen.writeNumber(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            gen.writeString(value);
        }
    }

}