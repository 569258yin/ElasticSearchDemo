package es.bean.item;

import org.codehaus.jackson.JsonGenerator;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by gujinxin on 16/8/11.
 */
public class ItemAttribute implements Serializable {

    private static final long serialVersionUID = 987612345013L;

    private String itemId;
    private String name;
    private String value;
    private String tenantId;

    public ItemAttribute() {}

    public ItemAttribute(String itemId,String name, String value){
        this.itemId = itemId;
        this.name = name;
        this.value = value;
    }
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
