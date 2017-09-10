package es.item.bean;

import com.google.common.collect.Maps;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.Map;

/**
 * Created by gujinxin on 16/8/11.
 */
public enum ItemType {
    PRODUCTS(1, "products", "产品"),
    SEMI_PRODUCTS(2, "semi_products", "半成品"),
    MATERIALS(3, "materials", "原料");
    private int id;
    private String name;
    private String desc;

    ItemType(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    private static Map<String, ItemType> namesMap = Maps.newHashMapWithExpectedSize(3);

    static {
        namesMap.put("PRODUCTS", PRODUCTS);
        namesMap.put("SEMI_PRODUCTS", SEMI_PRODUCTS);
        namesMap.put("MATERIALS", MATERIALS);
    }

    @JsonCreator
    public static ItemType forValue(String value) {
        return namesMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, ItemType> entry : namesMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null; // or fail
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static ItemType parseById(int id) {
        for (ItemType itemType : ItemType.values()) {
            if (itemType.getId() == id) {
                return itemType;
            }
        }
        return null;
    }

    public static ItemType parseByStr(String str) {
        for (ItemType itemType : ItemType.values()) {
            if (itemType.getName().equals(str)) {
                return itemType;
            }
        }
        return null;
    }

    public static ItemType parseByDesc(String str) {
        for (ItemType itemType : ItemType.values()) {
            if (itemType.getDesc().equals(str)) {
                return itemType;
            }
        }
        return null;
    }
}
