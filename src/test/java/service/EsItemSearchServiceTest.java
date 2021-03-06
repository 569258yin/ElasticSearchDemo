package service;

import es.ElasticApplication;
import es.bean.es.EsSearchRange;
import es.bean.item.Item;
import es.service.ItemSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by kevinyin on 2017/9/11.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElasticApplication.class)
public class EsItemSearchServiceTest {

    @Resource
    private ItemSearchService itemSearchService;

    private String tenantId = "ca771880-2cd1-4a5d-a53c-581a4721252e";

    @Test
    public void testSearchNameAndValue(){
        long startTime = System.currentTimeMillis();
        EsSearchRange searchRange = EsSearchRange.factoryPageSize(0,100);
        List<Item> items = itemSearchService.searchItemByNameAndValue(tenantId,"name","支点",true,searchRange);
        System.out.println(items.size());
        System.out.println("use Time : "+(System.currentTimeMillis() - startTime)+"ms" );
    }

    @Test
    public void testSearchAllAttibute(){
        long startTime = System.currentTimeMillis();
        EsSearchRange searchRange = EsSearchRange.factoryPageSize(0,100);
        List<Item> items = itemSearchService.searchAllAttibute(tenantId,"合金",false,searchRange);
        System.out.println(items.size());
        System.out.println("use Time : "+(System.currentTimeMillis() - startTime)+"ms" );
    }

}
