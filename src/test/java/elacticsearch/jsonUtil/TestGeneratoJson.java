package elacticsearch.jsonUtil;

import es.utils.EsJsonUtils;
import org.junit.Test;

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


}
