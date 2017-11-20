package es.service.impl;

import es.bean.es.EsSearchRange;
import es.dao.ItemDao;
import es.elasticsearch.ElasticSearchDao;
import es.bean.item.Item;
import es.service.ItemSearchService;
import es.utils.Constants;
import es.utils.EsJsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by kevinyin on 2017/9/11.
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService{
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
        String json = EsJsonUtils.generateQueryItemByNameAndValue(name,value,isAccurate,range);
        long startTime = System.currentTimeMillis();
        List<String> ids = elasticSearchDao.search("/"+tenantId+ Constants.AFTER_SEARCH_ITEM,json);
        logger.info("search By ES use Time :" +(System.currentTimeMillis() - startTime) +"ms");
        if (CollectionUtils.isNotEmpty(ids)) {
            return itemDao.getItemsByIds(ids,false);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Item> searchAllAttibute(String tenantId,String value,boolean isAccurate, EsSearchRange range) {
        if (range == null) {
            range = EsSearchRange.getDefaultRang();
        }
        String json = EsJsonUtils.generateQueryAllItemByValue(value,isAccurate,range);
        long startTime = System.currentTimeMillis();
        List<String> ids = elasticSearchDao.search("/"+tenantId+ Constants.AFTER_SEARCH_ITEM,json);
        logger.info("search By ES use Time :" +(System.currentTimeMillis() - startTime) +"ms");
        if (CollectionUtils.isNotEmpty(ids)) {
            return itemDao.getItemsByIds(ids,false);
        }
        return Collections.EMPTY_LIST;
    }

}
