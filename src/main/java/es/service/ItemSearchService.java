package es.service;

import es.item.bean.Item;

import java.util.List;

/**
 * Created by kevinyin on 2017/9/11.
 */
public interface ItemSearchService {

    /**
     * 指定字段下搜索
     * @param name
     * @param value
     * @return
     */
    List<Item> searchItemByNameAndValue(String tenantId,String name,String value);

    /**
     * 全文搜索
     * @param value
     * @return
     */
    List<Item> searchAllAttibute(String tenantId,String value);




    /**
     * 精确搜索字段下的值
     * @param name
     * @param value
     * @return
     */
    List<Item> accurateSearchByNameAndValue(String tenantId,String name,String value);
}
