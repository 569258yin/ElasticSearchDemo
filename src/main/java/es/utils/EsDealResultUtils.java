package es.utils;

import es.bean.jsonbean.EsItemHits;
import es.bean.jsonbean.EsSearchResult;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by kevinyin on 2017/9/11.
 */
public class EsDealResultUtils {

    private static final Logger logger = LoggerFactory.getLogger(EsDealResultUtils.class);
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean dealResponseResult(String result){
        if (result == null) {
            return false;
        }
        try {
            if (StringUtils.isEmpty(result)) {
                return false;
            }
            Map<String,Object> maps = JsonUtils.jsonDecodeMap(result);
            Object acknowledged =  maps.get("acknowledged");
            if (acknowledged == null) {
                return false;
            }
            return  (Boolean) maps.get("acknowledged");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<String> dealSearchItemResult(String json){
        if (StringUtils.isEmpty(json)) {
            return Collections.EMPTY_LIST;
        }
        try {
            EsSearchResult searchResult = objectMapper.readValue(json, EsSearchResult.class);
            if (searchResult != null) {
                int total = searchResult.getHits().getTotal();
                if (total == 0) {
                    return Collections.EMPTY_LIST;
                }
                List<EsItemHits> esItems = searchResult.getHits().getHits();
                List<String> ids = new ArrayList<>(total);
                for (int i = 0; i < esItems.size(); i++) {
                    ids.add(esItems.get(i).get_source().getId());
                }
                return ids;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            logger.error("dealSearchItemResult",e);
            return Collections.EMPTY_LIST;
        }
    }

    public static boolean dealBulkInsertResult(String json) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            Map<String,Object> maps = JsonUtils.jsonDecodeMap(json);
            Object errors =  maps.get("errors");
            if (errors == null) {
                return false;
            }
            return (Boolean) maps.get("errors");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean dealDeleteData(String json){
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            Map<String,Object> maps = JsonUtils.jsonDecodeMap(json);
            Object result =  maps.get("result");
            if (result == null) {
                return false;
            }
            return "deleted".equalsIgnoreCase((String) maps.get("result"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int dealDeleteDataByQuery(String json){
        if (StringUtils.isEmpty(json)) {
            return 0;
        }
        try {
            Map<String,Object> maps = JsonUtils.jsonDecodeMap(json);
            Object deleted =  maps.get("deleted");
            if (deleted == null) {
                return 0;
            }
            return (int) maps.get("deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<String> dealGetAliasesResult(String json) {
        if (StringUtils.isEmpty(json)) {
            return Collections.emptyList();
        }
        try {
            Map<String,Object> maps = JsonUtils.jsonDecodeMap(json);
            if (MapUtils.isNotEmpty(maps)) {
                return maps.keySet().stream().collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "  \"test_2\": {\n" +
                "    \"aliases\": {\n" +
                "      \"test\": {}\n" +
                "    }\n" +
                "  }\n" +
                "}";
        System.out.println(dealGetAliasesResult(json));

    }
}
