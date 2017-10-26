package es.utils;

import es.bean.jsonbean.EsItemHits;
import es.bean.jsonbean.EsSearchResult;
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
            String acknow = (String) maps.get("acknowledged");
            return acknow != null ? acknow.equalsIgnoreCase("true") :false;
        } catch (Exception e) {
            return false;
        }
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
            return "deleted".equalsIgnoreCase((String) maps.get("result"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean dealDeleteDataByQuery(String json){
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            Map<String,Object> maps = JsonUtils.jsonDecodeMap(json);
            return (Integer) maps.get("deleted") > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"_shards\" : {\n" +
                "        \"total\" : 2,\n" +
                "        \"failed\" : 0,\n" +
                "        \"successful\" : 2\n" +
                "    },\n" +
                "    \"found\" : true,\n" +
                "    \"_index\" : \"twitter\",\n" +
                "    \"_type\" : \"tweet\",\n" +
                "    \"_id\" : \"1\",\n" +
                "    \"_version\" : 2,\n" +
                "    \"result\": \"deleted\"\n" +
                "}";
        Map<String,Object> maps = JsonUtils.jsonDecodeMap(json);
        System.out.println(maps);

    }
}
