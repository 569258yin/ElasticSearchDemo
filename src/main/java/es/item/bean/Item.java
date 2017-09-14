package es.item.bean;

import es.utils.EsJsonUtils;
import es.utils.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private ItemType type;
    private String unit;
    private double price;
    private double cost;
    private String attachment;
    private String image;
    private String album;
    private String blueprint;
    private String comments;
    private String code;
    @JsonProperty("GDID")
    private String GDID;
    private double taxRate;
    private boolean allowSale;
    private boolean allowPurchase;
    private boolean allowProduction;
    private int shelfLife = -1;
    private Category category;
    private List<ItemAttribute> itemAttributes;
    private long sortIdx;//用于 tenant item 列表排序
    private boolean isDeleted;
    private Date createTime;
    private Date updateTime;
    private String tenantId;

    public Item() {

    }

    public static void generateItemJson(JsonGenerator jg,Item item) throws IOException {
        Map<String,Object> filedMaps = ObjectUtils.getValueMap(item);
        String key;
        Object obj;
        for (Map.Entry<String, Object> entry: filedMaps.entrySet()){
            key = entry.getKey();
            obj = entry.getValue();
            if (obj instanceof ItemType) {
                ItemType itemType = (ItemType) obj;
                EsJsonUtils.generateEsAttribute(jg,key,itemType.getDesc().toString());
            } else if (obj instanceof Category) {
                Category category = (Category) obj;
                EsJsonUtils.generateEsAttribute(jg,key,category.getName().toString());
            } else if (obj instanceof List){
                continue;
            } else {
                EsJsonUtils.generateEsAttribute(jg,key,obj.toString());
            }
        }
    }

    public Item(String id) {
        this.id = id;
    }

    public Item(String id, String tenantID) {
        this.id = id;
        this.tenantId = tenantID;
    }

    public String getId() {
        if (StringUtils.isEmpty(id)) {
            id = UUID.randomUUID().toString();
        }
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

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getBlueprint() {
        return blueprint;
    }

    public void setBlueprint(String blueprint) {
        this.blueprint = blueprint;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGDID() {
        return GDID;
    }

    public void setGDID(String GDID) {
        this.GDID = GDID;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public boolean isAllowSale() {
        return allowSale;
    }

    public void setAllowSale(boolean allowSale) {
        this.allowSale = allowSale;
    }

    public boolean isAllowPurchase() {
        return allowPurchase;
    }

    public void setAllowPurchase(boolean allowPurchase) {
        this.allowPurchase = allowPurchase;
    }

    public boolean isAllowProduction() {
        return allowProduction;
    }

    public void setAllowProduction(boolean allowProduction) {
        this.allowProduction = allowProduction;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ItemAttribute> getItemAttributes() {
        return itemAttributes;
    }

    public void setItemAttributes(List<ItemAttribute> itemAttributes) {
        this.itemAttributes = itemAttributes;
    }

    public long getSortIdx() {
        return sortIdx;
    }

    public void setSortIdx(long sortIdx) {
        this.sortIdx = sortIdx;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}