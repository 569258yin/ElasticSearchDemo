package es.utils;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * json 工具类
 * Created by cao on 14-9-12.
 */
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    public static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        //忽略掉json有，对象没有属性时报错
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.INTERN_FIELD_NAMES, true);
//        objectMapper.configure(JsonParser.Feature.CANONICALIZE_FIELD_NAMES, true);
    }

    /**
     * 生成正确json
     *
     * @param object
     * @return
     */

    public static String generateRightJson(Object object) {
        StringWriter sw = new StringWriter();
        JsonFactory jsonFactory = objectMapper.getJsonFactory();
        JsonGenerator jg = null;
        try {
            jg = jsonFactory.createJsonGenerator(sw);
            jg.writeStartObject();
            jg.writeBooleanField("ret", true);
            jg.writeObjectField("data", object);
            jg.writeEndObject();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        } finally {
            Closer.close(jg);
        }
        return sw.toString();
    }

    public static String generateRightJson(Object object, int size) {
        StringWriter sw = new StringWriter();
        JsonFactory jsonFactory = objectMapper.getJsonFactory();
        JsonGenerator jg = null;
        try {
            jg = jsonFactory.createJsonGenerator(sw);
            jg.writeStartObject();
            jg.writeBooleanField("ret", true);
            jg.writeObjectField("data", object);
            jg.writeNumberField("recordsTotal", size);
            jg.writeNumberField("recordsFiltered", size);
            jg.writeEndObject();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        } finally {
            Closer.close(jg);
        }
        return sw.toString();
    }
    /**
     * 生产错误json
     *
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public static String generateErrorJson(int errorCode, String errorMsg) {
        StringWriter sw = new StringWriter();
        JsonFactory jsonFactory = objectMapper.getJsonFactory();
        JsonGenerator jg = null;
        try {
            jg = jsonFactory.createJsonGenerator(sw);
            jg.writeStartObject();
            jg.writeBooleanField("ret", false);
            jg.writeNumberField("errorCode", errorCode);
            jg.writeStringField("errorMsg", errorMsg);
            jg.writeEndObject();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        } finally {
            Closer.close(jg);
        }
        return sw.toString();
    }

    public static String generateErrorJson(Object object, int errorCode, String errorMsg) {
        StringWriter sw = new StringWriter();
        JsonFactory jsonFactory = objectMapper.getJsonFactory();
        JsonGenerator jg = null;
        try {
            jg = jsonFactory.createJsonGenerator(sw);
            jg.writeStartObject();
            jg.writeBooleanField("ret", false);
            jg.writeObjectField("data", object);
            jg.writeNumberField("errorCode", errorCode);
            jg.writeStringField("errorMsg", errorMsg);
            jg.writeEndObject();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        } finally {
            Closer.close(jg);
        }
        return sw.toString();
    }

    public static String generateJson(Object o) {
        StringWriter sw = new StringWriter();
        JsonFactory jsonFactory = objectMapper.getJsonFactory();
        JsonGenerator jg = null;
        try {
            jg = jsonFactory.createJsonGenerator(sw);
            jg.writeObject(o);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        } finally {
            Closer.close(jg);
        }
        return sw.toString();
    }

    public static <K, T> Map<K, T> jsonDecodeMap(String json) {
        Map<K, T> obj = Collections.emptyMap();
        try {
            if (StringUtils.isNotEmpty(json)) {
                obj = objectMapper.readValue(json, new TypeReference<Map<K, T>>() {
                });
            }
        } catch (IOException e) {
            return Collections.emptyMap();
        }
        return obj;
    }

    public static <T> List<T> listJsonDecode(String json) {
        List<T> obj = Collections.emptyList();
        try {
            if (StringUtils.isNotEmpty(json)) {
                obj = objectMapper.readValue(json, new TypeReference<List<T>>() {
                });
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
        return obj == null ? Collections.emptyList() : obj;
    }


}
