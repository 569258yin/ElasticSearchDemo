package es.elasticsearch;

import java.util.List;

/**
 * Created by kevinyin on 2017/9/9.
 */
public interface ElasticSearchDao {

    //------------------------------------------------------------Index--------------------------------------------------------

    boolean createIndexType(String index,String type,String json);

    boolean deleteIndex(String index);

    boolean createIndexMapping(String index,String json);

    boolean createNgramAyanalzer(String index);
    //-------------------------------------------------------------Insert,Update,Delete,GET-----------------------------------------

    int insertData(String index,String type,String json);

    boolean bulkDealData(String index, String type, String json);

    String getById(String index,String type,Object id);

    boolean deleteById(String index,String type,Object id);

    boolean deleteByQuery(String index,String type,String json);

    //-----------------------------------------------------------search-----------------------------------------------------------
    
    List<String> search(String endpoint, String json);

}
