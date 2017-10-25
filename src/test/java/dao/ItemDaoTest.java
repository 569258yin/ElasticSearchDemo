package dao;

import es.ElasticApplication;
import es.dao.ItemDAO;
import es.elasticsearch.ElasticSearchDao;
import es.utils.EsJsonUtils;
import es.bean.item.Item;
import es.utils.ObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kevinyin on 2017/9/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElasticApplication.class)
public class ItemDaoTest {


    @Resource
    private ItemDAO itemDAO;

    @Test
    public void testFetchAllItem() {
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
                //剔除
                items.subList(0, pointsDataLimit).clear();
            }
            if (!items.isEmpty()) {
                String json = EsJsonUtils.generateMultiInsertItem(items);
                System.out.println(json);
            }
        }
    }

    @Test
    public void testItemToMap(){
        String tenantId = "5cab9a0c-b22e-4640-ac7e-7426dd9fea73";
        List<Item> items = itemDAO.getAllNotDeleteItems(tenantId);
        Map<String,Map<String,Object>> itemMaps = new HashMap<>(items.size());
        for (Item item : items) {
            Map<String, Object> obj = ObjectUtils.getValueMap(item);
            itemMaps.put(item.getId(),obj);
        }
        System.out.println(itemMaps.size());
    }

    @Test
    public void testGenerateItemJson(){
        String tenantId = "5cab9a0c-b22e-4640-ac7e-7426dd9fea73";
        List<Item> items = itemDAO.getAllNotDeleteItems(tenantId);
       String json =EsJsonUtils.generateMultiInsertItem(items.subList(0,20));
        System.out.println(json);
    }

}
