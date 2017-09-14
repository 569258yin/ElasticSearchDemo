package es.searchtask;

import es.elasticsearch.ElasticSearchDao;
import es.utils.Constants;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kevinyin on 2017/9/11.
 */
public class SearchItemIdTask implements Callable<List<String>>{

    private final ElasticSearchDao elasticSearchDao;
    private final String tenantId;
    private final String json;

    public SearchItemIdTask(ElasticSearchDao elasticSearchDao, String tenantId, String json) {
        this.elasticSearchDao = elasticSearchDao;
        this.tenantId = tenantId;
        this.json = json;
    }

    @Override
    public List<String> call() throws Exception {
        return elasticSearchDao.search("/"+tenantId+ Constants.AFTER_SEARCH_ITEM,json);
    }
}
