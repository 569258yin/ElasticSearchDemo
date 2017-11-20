package es.service;

import es.bean.item.Item;

import java.util.List;

public interface ItemService {

    List<Item> fetchItems(String tenantId);
}
