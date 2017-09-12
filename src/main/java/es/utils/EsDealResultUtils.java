package es.utils;

import es.bean.jsonbean.EsItemHits;
import es.bean.jsonbean.EsSearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kevinyin on 2017/9/11.
 */
public class EsDealResultUtils {

    private static final Logger logger = LoggerFactory.getLogger(EsDealResultUtils.class);
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean dealResponseResult(HttpEntity httpEntity){
        if (httpEntity == null) {
            return false;
        }
        try {
            String result = EntityUtils.toString(httpEntity,Constants.UTF_8);
            if (StringUtils.isEmpty(result)) {
                return false;
            }
            JSONObject jsonObject = new JSONObject(result);
            String acknow = (String) jsonObject.get("acknowledged");
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
}
