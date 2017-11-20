package es.service.impl;

import es.bean.item.Item;
import es.dao.ItemDao;
import es.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    @Resource
    private ItemDao itemDao;

    @Override
    public List<Item> fetchItems(String tenantId) {
        if (StringUtils.isEmpty(tenantId)) {
            return null;
        }
        return itemDao.getAllNotDeleteItems(tenantId);
    }
}
