package dao;

import es.dao.ItemDAO;
import es.utils.EsJsonUtils;
import es.item.bean.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by kevinyin on 2017/9/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
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

}
