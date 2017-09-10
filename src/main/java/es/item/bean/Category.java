package es.item.bean;

import es.item.util.ItemConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.UUID;

public class Category implements Serializable {
    private static final long serialVersionUID = -1360248356119498244L;
    private String id;
    private String name;
    private String parentId;
    private String tenantId;
    private boolean isDeleted;

    public Category() {
    }

    public Category(String id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        if (StringUtils.isEmpty(this.id)) {
            UUID attrId = UUID.randomUUID();
            this.id = attrId.toString();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        if (parentId == null) {
            this.parentId = ItemConstants.EMPTY_UUID;
        }
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTenantId() {
        if (tenantId == null) {
            tenantId = "-1";
        }
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }


    public ViewModel convertViewModel() {
        return new ViewModel(this);
    }

    public class ViewModel {
        private String id;
        private String name;

        public ViewModel() {

        }

        public ViewModel(Category category) {
            id = category.getId();
            name = category.getName();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }

}
