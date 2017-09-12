package es.searchtask;

import es.elasticsearch.ItemElasticSearchDao;
import es.utils.Constants;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kevinyin on 2017/9/11.
 */
public class SearchItemIdTask implements Callable<List<String>>{

    private final ItemElasticSearchDao itemElasticSearchDao;
    private final String tenantId;
    private final String json;

    public SearchItemIdTask(ItemElasticSearchDao itemElasticSearchDao, String tenantId, String json) {
        this.itemElasticSearchDao = itemElasticSearchDao;
        this.tenantId = tenantId;
        this.json = json;
    }

    @Override
    public List<String> call() throws Exception {
        return itemElasticSearchDao.search("/"+tenantId+ Constants.AFTER_SEARCH_ITEM,json);
    }
}
