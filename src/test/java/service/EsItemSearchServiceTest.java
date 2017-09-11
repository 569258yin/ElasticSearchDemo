package service;

import es.item.bean.Item;
import es.service.ItemSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by kevinyin on 2017/9/11.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class EsItemSearchServiceTest {

    @Resource
    private ItemSearchService itemSearchService;

    private String tenantId = "5cab9a0c-b22e-4640-ac7e-7426dd9fea73";

    @Test
    public void testSearchNameAndValue(){
        long startTime = System.currentTimeMillis();
        List<Item> items = itemSearchService.searchItemByNameAndValue(tenantId,"型号","20");
        System.out.println(items.size());
        System.out.println("use Time : "+(System.currentTimeMillis() - startTime)+"ms" );
    }

}
