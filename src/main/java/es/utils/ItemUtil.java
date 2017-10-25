package es.utils;


import es.bean.item.Category;
import es.bean.item.Item;
import es.bean.item.ItemAttribute;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by gujinxin on 16/8/12.
 */
public class ItemUtil {
    private static final Logger logger = LoggerFactory.getLogger(ItemUtil.class);

    public static String serializeItem(Item item) {
        if (item == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        JsonFactory jsonFactory = JsonUtils.objectMapper.getJsonFactory();
        JsonGenerator jg = null;
        try {
            jg = jsonFactory.createJsonGenerator(sw);
            jg.writeStartObject();
            jg.writeStringField("id", StringUtils.trimToEmpty(item.getId()));
            jg.writeStringField("code", StringUtils.trimToEmpty(item.getCode()));
            jg.writeStringField("name", StringUtils.trimToEmpty(item.getName()));
            jg.writeObjectField("type", item.getType());

            Category category = item.getCategory();
            jg.writeObjectFieldStart("category");
            jg.writeStringField("id", category == null ? StringUtils.EMPTY : category.getId());
            jg.writeStringField("name", category == null ? StringUtils.EMPTY : category.getName());
            jg.writeEndObject();

            jg.writeStringField("unit", StringUtils.trimToEmpty(item.getUnit()));
            jg.writeNumberField("price", item.getPrice());
            jg.writeNumberField("cost", item.getCost());
            jg.writeNumberField("taxRate", item.getTaxRate());
            jg.writeStringField("image", StringUtils.trimToEmpty(item.getImage()));
            jg.writeStringField("album", StringUtils.trimToEmpty(item.getAlbum()));
            jg.writeStringField("attachment", StringUtils.trimToEmpty(item.getAttachment()));
            jg.writeStringField("blueprint", StringUtils.trimToEmpty(item.getBlueprint()));
            jg.writeStringField("comments", StringUtils.trimToEmpty(item.getComments()));
            jg.writeStringField("GDID", StringUtils.trimToEmpty(item.getGDID()));
            jg.writeNumberField("sortIdx", item.getSortIdx());
            jg.writeBooleanField("allowSale", item.isAllowSale());
            jg.writeBooleanField("allowPurchase", item.isAllowPurchase());
            jg.writeBooleanField("allowProduction", item.isAllowProduction());
            jg.writeNumberField("shelfLife", item.getShelfLife());
            jg.writeStringField("tenantId", item.getTenantId());

            jg.writeArrayFieldStart("itemAttributes");
            for (ItemAttribute itemAttribute : item.getItemAttributes()) {
                jg.writeStartObject();
                jg.writeStringField("name", itemAttribute.getName());
                jg.writeStringField("value", itemAttribute.getValue());
                jg.writeEndObject();
            }
            jg.writeEndArray();
            jg.writeEndObject();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        } finally {
            Closer.close(jg);
        }
        return sw.toString();
    }

    public static Item deserializeItem(String itemJson) {
        if (StringUtils.isEmpty(itemJson)) {
            return null;
        }
        try {
            return JsonUtils.objectMapper.readValue(itemJson, Item.class);
        } catch (IOException e) {
            logger.error("反序列化item出错,itemJson:{}", itemJson, e);
        }
        return null;
    }
}
