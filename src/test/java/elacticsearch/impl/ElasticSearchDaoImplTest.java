package elacticsearch.impl;

import es.dao.ItemDAO;
import es.elasticsearch.ElasticSearchDao;
import es.elasticsearch.EsJsonUtils;
import es.elasticsearch.impl.ElasticSearchDaoImpl;
import es.item.bean.Item;
import es.utils.Constants;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * ElasticSearchDaoImpl Tester.
 *
 * @author YH
 * @version 1.0
 * @since <pre>���� 9, 2017</pre>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class ElasticSearchDaoImplTest {

    public static final String TENANT_ID = "5cab9a0c-b22e-4640-ac7e-7426dd9fea73";
    @Resource
    private ElasticSearchDao elasticSearchDao;
    @Resource
    private ItemDAO itemDAO;



    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createIndexType(String index, String type, String json)
     */
    @Test
    public void testCreateIndexType() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: createIndexMapping(String index, String type, String json)
     */
    @Test
    public void testCreateIndexMapping() throws Exception {
        String json = EsJsonUtils.generateItemMapping();
        System.out.println(json);
        elasticSearchDao.createIndexMapping(TENANT_ID+Constants.INDEX_SPLIT+ Constants.ITEM,json);
    }

    /**
     * Method: insertData(String index, String type, String json)
     */
    @Test
    public void testInsertData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: multiInsertData(String index, String type, String json)
     */
    @Test
    public void testMultiInsertData() throws Exception {
        String tenantId = "5cab9a0c-b22e-4640-ac7e-7426dd9fea73";
        List<Item> items = itemDAO.getAllNotDeleteItems(tenantId);
        System.out.println(items.size());
        int pointsDataLimit = 1000;//限制条数
        int size = items.size();
        //判断是否有必要分批
        if (pointsDataLimit < size) {
            int part = size / pointsDataLimit;//分批数
            System.out.println("共有 ： " + size + "条，！" + " 分为 ：" + part + "批");
            for (int i = 0; i < part; i++) {
                //1000条
                List<Item> listPage = items.subList(0, pointsDataLimit);
                String json = EsJsonUtils.generateMultiInsertItem(listPage);
                System.out.println(json);
                elasticSearchDao.multiDealData(TENANT_ID + Constants.INDEX_SPLIT + Constants.ITEM,Constants.ITEM,json);
                //剔除
                items.subList(0, pointsDataLimit).clear();
            }
            if (!items.isEmpty()) {
                String json = EsJsonUtils.generateMultiInsertItem(items);
                System.out.println(json);
                elasticSearchDao.multiDealData(TENANT_ID+ Constants.INDEX_SPLIT + Constants.ITEM,Constants.ITEM,json);
            }
        }
    }


} 
