package elacticsearch.impl;

import es.ElasticApplication;
import es.elasticsearch.ElasticSearchDao;
import es.utils.EsJsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by kevinyin on 2017/10/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElasticApplication.class)
public class ElasticAPITest {
    @Resource
    private ElasticSearchDao elasticSearchDao;

    private String index = "test";
    private String type = "item";

    @Test
    public void createIndexTypeTest() {
        String json = EsJsonUtils.generateSettingShards(3,2);
        Assert.assertTrue(elasticSearchDao.createIndex(index,json));
    }

    @Test
    public void deleteIndexTest(){
       Assert.assertTrue(elasticSearchDao.deleteIndex(index));
    }
}
