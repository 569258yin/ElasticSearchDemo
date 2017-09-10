package es.elasticsearch;

/**
 * Created by kevinyin on 2017/9/9.
 */
public interface ElasticSearchDao {

    boolean createIndexType(String index,String type,String json);

    boolean createIndexMapping(String index,String json);

    int insertData(String index,String type,String json);

    int multiDealData(String index,String type,String json);

    boolean createNgramAyanalzer(String index);

}
