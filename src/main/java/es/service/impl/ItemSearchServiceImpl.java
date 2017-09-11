package es.service.impl;

import es.dao.ItemDAO;
import es.elasticsearch.ItemElasticSearchDao;
import es.item.bean.Item;
import es.searchtask.SearchItemIdTask;
import es.service.ItemSearchService;
import es.utils.Constants;
import es.utils.EsJsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by kevinyin on 2017/9/11.
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService{
    @Resource
    private ItemElasticSearchDao itemElasticSearchDao;
    @Resource
    private ItemDAO itemDAO;

    private static String AFTER_INDEX = Constants.INDEX_SPLIT+Constants.ITEM+"/"+Constants.ITEM+"/"+Constants.SEARCH;
    private final ExecutorService poolThread = Executors.newFixedThreadPool(8);



    // 1823 ms   不用线程只需要856ms
    @Override
    public List<Item> searchItemByNameAndValue(String tenantId,String name, String value) {
        String json = EsJsonUtils.generateQueryItemByNameAndValue(name,value,false);
        Future<List<String>> future = poolThread.submit(new SearchItemIdTask(itemElasticSearchDao,tenantId,json));
        List<Item> items = itemDAO.getItemsByTenantId(tenantId,false);
        if (CollectionUtils.isEmpty(items)){
            return Collections.EMPTY_LIST;
        }
        Map<String,Item> itemMap = new HashMap<>(items.size());
        for (Item item : items) {
            itemMap.put(item.getId(),item);
        }
        List<String> ids = null;
        try {
            ids = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CollectionUtils.isNotEmpty(ids)) {
            List<Item> itemList = new ArrayList<>(ids.size());
            for (String id : ids) {
                if (itemMap.containsKey(id)) {
                    itemList.add(itemMap.get(id));
                }
            }
            return itemList;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Item> searchAllAttibute(String tenantId,String value) {
        return null;
    }

    @Override
    public List<Item> accurateSearchByNameAndValue(String tenantId,String name, String value) {
        return null;
    }

}
