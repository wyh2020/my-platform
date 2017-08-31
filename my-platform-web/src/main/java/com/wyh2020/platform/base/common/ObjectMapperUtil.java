package com.wyh2020.platform.base.common;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * Created with wyh.
 * Date: 2017/7/12
 * Time: 下午2:30
 */
public class ObjectMapperUtil {
    static ObjectMapper mapper = new ObjectMapper();
    static {

        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        mapper.configure(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,false);

    }

    public static <T> T readValue(String content, Class<T> valueType) {

        try {
            return mapper.readValue(content, valueType);
        } catch (Exception e) {
            return null;
        }
    }


    public static String writeValueAsString(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            return null;
        }
    }
}
