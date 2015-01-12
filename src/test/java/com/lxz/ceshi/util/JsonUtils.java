package com.lxz.ceshi.util;

import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class JsonUtils {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.INTERN_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.CANONICALIZE_FIELD_NAMES, true);
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);// JSON节点不包含属性值为NULL
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);// JSON节点不包含Collection、Map等属性值为NULL或EMTY
    }

    public static String encode(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            logger.error("encode(Object)", e); //$NON-NLS-1$
        } catch (JsonMappingException e) {
            logger.error("encode(Object)", e); //$NON-NLS-1$
        } catch (IOException e) {
            logger.error("encode(Object)", e); //$NON-NLS-1$
        }
        return null;
    }
    
    public static <T> T decode(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonParseException e) {
            logger.error("decode(String, Class<T>)", e); //$NON-NLS-1$
        } catch (JsonMappingException e) {
            logger.error("decode(String, Class<T>)", e); //$NON-NLS-1$
        } catch (IOException e) {
            logger.error("decode(String, Class<T>)", e); //$NON-NLS-1$
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T decode(String json, TypeReference<T> reference) {
        try {
            return (T) objectMapper.readValue(json, reference);
        } catch (JsonParseException e) {
            logger.error("decode(String, Class<T>)", e);
        } catch (JsonMappingException e) {
            logger.error("decode(String, Class<T>)", e);
        } catch (IOException e) {
            logger.error("decode(String, Class<T>)", e);
        }
        return null;
    }

    public static <T> T decode(String json, String path, Class<T> valueType) {
        ObjectReader objectReader = objectMapper.reader();
        T value;
        try {
            JsonNode jsonNode = objectReader.readTree(json);
            String[] names = StringUtils.split(path, '.');
            if (ArrayUtils.isEmpty(names)) {
                return null;
            }
            for (String name : names) {
                if (jsonNode.get(name) == null) {
                    return null;
                }
                jsonNode = jsonNode.get(name);
            }
            if (jsonNode == null) {
                return null;
            }
            value = objectMapper.readValue(jsonNode, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}