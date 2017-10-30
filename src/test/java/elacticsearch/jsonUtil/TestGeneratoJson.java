package elacticsearch.jsonUtil;

import es.utils.EsJsonUtils;
import es.utils.JsonUtils;
import org.junit.Test;

import java.util.Map;

/**
 * Created by kevinyin on 2017/9/9.
 */
public class TestGeneratoJson {

    @Test
    public void testCreateItemMapping(){
        System.out.println(EsJsonUtils.generateItemMapping());
    }

    @Test
    public void testCreateNgram(){
        System.out.println(EsJsonUtils.generateNgramAynalyz());
    }

    @Test
    public void testDealMultiInsertResultJson(){
        String json = "{\"took\":374,\"errors\":false,\"items\":[{\"index\":{\"_index\":\"ca771880-2cd1-4a5d-a53c-581a4721252e_item\",\"_type\":\"item\",\"_id\":\"e6137b90-3c7d-49de-9c58-b64e612013b6\",\"_version\":1,\"result\":\"created\",\"_shards\":{\"total\":2,\"successful\":1,\"failed\":0},\"created\":true,\"status\":201},\"index\":{\"_index\":\"ca771880-2cd1-4a5d-a53c-581a4721252e_item\",\"_type\":\"item\",\"_id\":\"e6137b90-3c7d-49de-9c58-b64e612013b6\",\"_version\":1,\"result\":\"created\",\"_shards\":{\"total\":2,\"successful\":1,\"failed\":0},\"created\":true,\"status\":201}}]}";
        Map<String,Object> maps = JsonUtils.jsonDecodeMap(json);
        System.out.println(maps.size());
    }

    @Test
    public void generateAddAliasesTest(){
        String[] tests = new String[]{"test1","test2","test3"};
        String json = EsJsonUtils.generateAddAliases("test",tests);
        System.out.println(json);
    }


}
