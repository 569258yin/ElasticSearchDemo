package es.utils;

import es.bean.item.Item;
import es.bean.item.ItemAttribute;
import es.bean.jsonbean.EsItem;
import es.bean.jsonbean.EsItemAttribute;
import es.bean.jsonbean.EsItemHits;
import es.bean.jsonbean.EsSearchResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by kevinyin on 2017/9/11.
 */
public class EsDealResultUtils {

    private static final Logger logger = LoggerFactory.getLogger(EsDealResultUtils.class);
    public static ObjectMapper objectMapper = JsonUtils.objectMapper;

    public static boolean dealResponseResult(String result){
        if (result == null) {
            return false;
        }
        try {
            if (StringUtils.isEmpty(result)) {
                return false;
            }
            Map<String,Object> maps = JsonUtils.jsonDecodeMap(result);
            String acknow = (String) maps.get("acknowledged");
            return acknow != null ? acknow.equalsIgnoreCase("true") :false;
        } catch (Exception e) {
            return false;
        }
    }

    public static Map<String,Object> dealSearchItemResult(String json){
        if (StringUtils.isEmpty(json)) {
            return Collections.emptyMap();
        }
        try {
            Map<String,Object> maps = JsonUtils.jsonDecodeMap(json);
            if (MapUtils.isNotEmpty(maps)) {
                return (Map<String, Object>) maps.get("hits");
            }
        } catch (Exception e) {
            logger.error("dealSearchItemResult",e);
        }
        return Collections.emptyMap();
    }

    public static boolean dealBulkInsertResult(String json) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            Map<String,Object> maps = JsonUtils.jsonDecodeMap(json);
            return (Boolean) maps.get("errors");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
