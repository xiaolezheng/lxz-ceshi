/*
 * Copyright (c) 2014 Qunar.com. All Rights Reserved.
 */
package com.lxz.util;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xiaole Date: 14-5-18 Time: 下午8:12
 * @version: \$Id$
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
    }

    public static String encode(Object object) {
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, object);
        } catch (IOException e) {
            logger.error("", e);
        }
        return writer.toString();
    }

    public static <T> T decode(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            logger.error("json: {}", json, e);
        }
        return null;
    }

    public static JsonNode toJsonNode(String json) {
        try {
            return mapper.readTree(json);
        } catch (Exception e) {
            logger.error("json: {}", json, e);
        }

        return null;
    }

    public static <T> T jsonNodeToT(JsonNode jsonNode, Class<T> type) {
        try {
            return mapper.readValue(jsonNode, type);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

}
