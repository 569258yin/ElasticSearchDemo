package es.elasticsearch;

import java.util.List;

/**
 * Created by kevinyin on 2017/9/9.
 */
public interface ElasticSearchDao {

    //------------------------------------------------------------Index--------------------------------------------------------

    boolean createIndex(String index,String json);

    boolean createIndexType(String index,String type,String json);

    boolean deleteIndex(String index);

    boolean createIndexMapping(String index,String json);

    boolean makeIndexAliases(String alias,String ... indexs);

    List<String> getIndexAllAliases(String index);

    boolean deleteIndexAllAliases(String index);

    boolean createNgramAyanalzer(String index);
    //-------------------------------------------------------------Insert,Update,Delete,GET-----------------------------------------

    int insertData(String index,String type,String json);

    boolean bulkDealData(String index, String type, String json);

    String getById(String index,String type,Object id);

    boolean deleteById(String index,String type,Object id);

    int deleteByQuery(String index,String type,String json);

    //-----------------------------------------------------------search-----------------------------------------------------------
    
    List<String> search(String endpoint, String json);

}
