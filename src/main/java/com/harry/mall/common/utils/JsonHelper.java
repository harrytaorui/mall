package com.harry.mall.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonHelper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String parse(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T read(String valueString, Class<T> clazz) throws JsonProcessingException {
        return MAPPER.readValue(valueString, clazz);
    }
}
