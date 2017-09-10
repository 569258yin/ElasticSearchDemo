package es.dao;

import es.item.bean.Item;
import es.item.bean.ItemAttribute;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by cao on 3/24/16.
 */
@Repository("itemDAO")
public interface ItemDAO {

    Item getItem(@Param("itemId") String itemId, @Param("tenantId") String tenantId, @Param("withDeleted") boolean withDeleted);

    List<Item> getItemsByIds(@Param("itemIds") List<String> itemIds, @Param("withDeleted") boolean withDeleted);

    List<Item> getItemsByTenantId(@Param("tenantId") String tenantId, @Param("withDeleted") boolean withDeleted);

    int createItems(@Param("tenantId") String tenantId, @Param("items") List<Item> items);

    int createCustomerAttr(@Param("tenantId") String tenantId, @Param("itemAttributes") List<ItemAttribute> attrNameValuePairs);

    int updateItem(@Param("es/item") Item item);

    int updateItems(@Param("items") List<Item> items);

    int updateCustomerAttr(@Param("tenantId") String tenantId, @Param("customerAttr") List<ItemAttribute> attrNameValuePairs);

    int deleteItem(@Param("itemId") String itemId);

    int deleteItems(@Param("itemIds") List<String> itemIds);

    int deleteItemAttributes(@Param("items") List<Item> items);

    List<Item> getItemsByCodes(@Param("codes") List<String> codes, @Param("tenantId") String tenantId, @Param("withDeleted") boolean withDeleted);

    List<String> getItemByCategoryId(@Param("category") String categoryId);

    List<Item> getAllNotDeleteItems(@Param("tenantId")String tenantId);

    List<Map<String,String>> selectOrderSnapshots();
    List<Map<String,String>> selectVoucherSnapshots();
    List<Map<String,String>> selectPurchaseSnapshots();

}