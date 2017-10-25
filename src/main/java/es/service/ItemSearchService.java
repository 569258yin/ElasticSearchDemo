package es.service;

import es.bean.es.EsSearchRange;
import es.bean.item.Item;

import java.util.List;

/**
 * Created by kevinyin on 2017/9/11.
 */
public interface ItemSearchService {

    /**
     * 指定字段下搜索
     * @param name
     * @param value
     * @param isAccurate  是否为精确匹配
     * @return
     */
    List<Item> searchItemByNameAndValue(String tenantId,String name,String value,boolean isAccurate, EsSearchRange range);

    /**
     * 全文搜索
     * @param value
     * @return
     */
    List<Item> searchAllAttibute(String tenantId,String value,boolean isAccurate, EsSearchRange range);

}
