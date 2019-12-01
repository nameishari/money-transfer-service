package com.bank.moneytransfer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

import static com.bank.moneytransfer.Application.OBJECT_MAPPER;

public final class JsonUtils {

    public static String toJson(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Failed to make a json for %s", value), e);
        }
    }

    public static <T> T fromJson(String content, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(content, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert from json to Object", e);
        }
    }
}
