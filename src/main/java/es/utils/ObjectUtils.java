package es.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import es.bean.item.Category;
import es.bean.item.Item;
import es.bean.item.ItemAttribute;
import es.bean.item.ItemType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kevinyin on 2017/9/13.
 */
public class ObjectUtils {

    public static Map<String, Object> getValueMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        // System.out.println(obj.getClass());
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null) {
                    map.put(varName, o);
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;

    }

    public static Item setValueToItem(Map<String, String> valuse, String id) {
        try {
            Item instance = new Item();
            Iterator<Map.Entry<String, String>> it = valuse.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                String key = entry.getKey();
                Field field = null;
                try {
                    field = Item.class.getDeclaredField(key);
                } catch (NoSuchFieldException e) {
                    continue;
                }
                if (field == null) {
                    continue;
                }
                // 获取原来的访问控制权限
                boolean accessFlag = field.isAccessible();
                field.setAccessible(true);
                if (field.getType().equals(Category.class)) {
                    field.set(instance, Category.factoryByName(entry.getValue()));
                } else if (field.getType().equals(ItemType.class)) {
                    field.set(instance, ItemType.parseByDesc(entry.getValue()));
                }
                Object value = entry.getValue();
                if (field.getType().equals(String.class)) {
                    field.set(instance, entry.getValue());
                } else if (field.getType().equals(Double.class)) {
                    field.set(instance, Double.valueOf(entry.getValue()));
                } else if (field.getType().equals(Integer.class)) {
                    field.set(instance, Integer.valueOf(entry.getValue()));
                } else if (field.getType().equals(Boolean.class)) {
                    field.set(instance, Boolean.valueOf(entry.getValue()));
                } else if (field.getType().equals(Long.class)) {
                    field.set(instance, Long.valueOf(entry.getValue()));
                } else if (field.getType().equals(Date.class)) {
                    field.set(instance, DateUtils.getDateFromDateStr(entry.getValue()));
                }
                it.remove();
                field.setAccessible(accessFlag);
            }
            List<ItemAttribute> itemAttributes = new ArrayList<>(valuse.size());
            valuse.forEach((key,value) ->{
                ItemAttribute itemAttribute = new ItemAttribute(id, key, value);
                itemAttributes.add(itemAttribute);
            });
            instance.setItemAttributes(itemAttributes);
            instance.setId(id);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
