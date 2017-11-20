package es.mvc;

import es.bean.es.EsSearchRange;
import es.bean.item.Item;
import es.service.ItemSearchService;
import es.service.ItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/api")
@RestController
public class EsTestController {

    @Resource
    private ItemService itemService;
    @Resource
    private ItemSearchService itemSearchService;

    private final String tenantId = "5cab9a0c-b22e-4640-ac7e-7426dd9fea73";

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public List<Item> testGetItem(){
        List<Item> items = itemService.fetchItems(tenantId);
        return items;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Item> searchItemByAttibute(){
        List<Item> items = itemSearchService.searchItemByNameAndValue(tenantId,"name","21",false, EsSearchRange.getDefaultRang());
        return items;
    }


}
