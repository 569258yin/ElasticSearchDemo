package es.elasticsearch;

import es.item.bean.Item;

import java.util.List;

/**
 * Created by kevinyin on 2017/9/9.
 */
public interface ItemElasticSearchDao {

    boolean createIndexType(String index,String type,String json);

    boolean createIndexMapping(String index,String json);

    int insertData(String index,String type,String json);

    int multiDealData(String index,String type,String json);

    boolean createNgramAyanalzer(String index);

    List<String> searchByNameAndValue(String endpoint,String json);

    List<String> searchAllByValue(String endpoint,String value);

}
