package es.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import es.item.bean.Item;
import es.item.util.ItemUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by cao on 04/07/2017.
 */
@Repository("itemCache")
public class ItemCacheDao {

    @Resource(name = "itemRedisTemplate")
    private HashOperations<String, String, String> itemHashOperations;
    @Resource(name = "TaskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    public void save(String tenantId, List<Item> items) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        Map<String, String> itemMap = Maps.newHashMap();
        for (Item item : items) {
            itemMap.put(item.getId(), ItemUtil.serializeItem(item));
        }

        itemHashOperations.putAll(tenantId, itemMap);
    }

    public Item getById(String tenantId, String itemId) {
        return ItemUtil.deserializeItem(itemHashOperations.get(tenantId, itemId));
    }

    public List<Item> multiGet(String tenantId, List<String> itemIds) {
        List<String> itemStrs = itemHashOperations.multiGet(tenantId, itemIds);
        List<Item> results = generateItems(itemStrs);
        results.sort(Comparator.comparingLong(Item::getSortIdx));
        return results;
    }

    public List<Item> getByTenantId(String tenantId) {
        long count = itemHashOperations.size(tenantId);
        List<Item> results;
        if (count > 2000) {
            results = Collections.synchronizedList(Lists.newArrayListWithCapacity((int) count));
            List<String> keys = Lists.newArrayList(itemHashOperations.keys(tenantId));
            try {
                List<List<String>> lists = Lists.partition(keys, 2000);
                final CountDownLatch doneSignal = new CountDownLatch(lists.size());
                for (final List<String> itemIds : lists) {
                    taskExecutor.execute(() -> {
                        try {
                            results.addAll(generateItems(itemHashOperations.multiGet(tenantId, itemIds)));
                        } finally {
                            doneSignal.countDown();
                        }
                    });
                }
                doneSignal.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            List<String> itemStrs = itemHashOperations.values(tenantId);
            results = generateItems(itemStrs);
        }
        results.sort(Comparator.comparingLong(Item::getSortIdx));
        return results;
    }

    private List<Item> generateItems(List<String> itemStrs) {
        if (CollectionUtils.isEmpty(itemStrs)) {
            return Collections.emptyList();
        }
        List<Item> results = Lists.newArrayListWithCapacity(itemStrs.size());
        for (String itemStr : itemStrs) {
            Item item = ItemUtil.deserializeItem(itemStr);
            if (item != null) {
                results.add(item);
            }
        }
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results;
    }


    public long delete(String tenantId, String itemId) {
        return multiDelete(tenantId, Lists.newArrayList(itemId));
    }

    public long multiDelete(String tenantId, List<String> itemIds) {
        if (CollectionUtils.isEmpty(itemIds)) {
            return 0;
        }
        return itemHashOperations.delete(tenantId, itemIds.toArray());
    }
}
