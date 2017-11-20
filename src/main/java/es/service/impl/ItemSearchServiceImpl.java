package es.service.impl;

import es.bean.es.EsSearchRange;
import es.dao.ItemDao;
import es.elasticsearch.ElasticSearchDao;
import es.bean.item.Item;
import es.service.ItemSearchService;
import es.utils.Constants;
import es.utils.EsJsonUtils;
import es.utils.ObjectUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by kevinyin on 2017/9/11.
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    private Logger logger = LoggerFactory.getLogger(ItemSearchServiceImpl.class);

    @Resource
    private ElasticSearchDao elasticSearchDao;
    @Resource
    private ItemDao itemDao;

    // 1823 ms   不用线程只需要856ms   其中搜索274ms
    @Override
    public List<Item> searchItemByNameAndValue(String tenantId, String name, String value, boolean isAccurate, EsSearchRange range) {
        if (range == null) {
            range = EsSearchRange.getDefaultRang();
        }
        String json = EsJsonUtils.generateQueryItemByNameAndValue(name, value, isAccurate, range);
        long startTime = System.currentTimeMillis();
        Map<String, Object> values = elasticSearchDao.search("/" + tenantId + Constants.AFTER_SEARCH_ITEM, json);
        logger.info("search By ES use Time :" + (System.currentTimeMillis() - startTime) + "ms");
        int total = 0;
        if (MapUtils.isNotEmpty(values)) {
            total = (int) values.get("total");
            return jsonMapToItem(values);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Item> searchAllAttibute(String tenantId, String value, boolean isAccurate, EsSearchRange range) {
        if (range == null) {
            range = EsSearchRange.getDefaultRang();
        }
        String json = EsJsonUtils.generateQueryAllItemByValue(value, isAccurate, range);
        long startTime = System.currentTimeMillis();
        Map<String, Object> values = elasticSearchDao.search("/" + tenantId + Constants.AFTER_SEARCH_ITEM, json);
        logger.info("search By ES use Time :" + (System.currentTimeMillis() - startTime) + "ms");
        int total = 0;
        if (MapUtils.isNotEmpty(values)) {
            total = (int) values.get("total");
            return jsonMapToItem(values);
        }
        return Collections.EMPTY_LIST;
    }

    private List<Item> jsonMapToItem(Map<String, Object> values) {
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) values.get("hits");
        if (CollectionUtils.isNotEmpty(mapList)) {
            List<Item> items = new ArrayList<>(mapList.size());
            for (Map<String, Object> objectMap : mapList) {
                String id = (String) objectMap.get("_id");
                List<Map<String,String>> esAttributes = (List<Map<String, String>>) ((Map<String,Object>)objectMap.get("_source")).get("attribute");
                Map<String,String> attibutes = new HashMap<>(esAttributes.size());
                for (Map<String, String> esAttribute : esAttributes) {
                    attibutes.put(esAttribute.get("name"),esAttribute.get("value"));
                }
                Item item = ObjectUtils.setValueToItem(attibutes,id);
                if (item != null) {
                    items.add(item);
                }
            }
            return items;
        }
        return null;
    }

}
