package es.elasticsearch;

import java.util.List;
import java.util.Map;

/**
 * Created by kevinyin on 2017/9/9.
 */
public interface ElasticSearchDao {

    //------------------------------------------------------------Index--------------------------------------------------------

    boolean createIndexType(String index,String type,String json);

    boolean deleteIndex(String index);

    boolean createIndexMapping(String index,String json);

    //-------------------------------------------------------------Insert,Update,Delete-----------------------------------------

    int insertData(String index,String type,String json);

    boolean bulkDealData(String index, String type, String json);

    boolean createNgramAyanalzer(String index);

    //-----------------------------------------------------------search-----------------------------------------------------------
    
    Map<String,Object> search(String endpoint, String json);

}
