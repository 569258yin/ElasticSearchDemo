package es.elasticsearch;

import es.item.bean.Item;
import es.item.bean.ItemAttribute;
import es.utils.Closer;
import es.utils.Constants;
import es.utils.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by kevinyin on 2017/9/9.
 */
public class EsJsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static final String NGRAM_ANAYZER = "ngram_analyzer";
    public static final String ITEM_NAME = "itemName";

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.INTERN_FIELD_NAMES, true);
//        objectMapper.configure(JsonParser.Feature.CANONICALIZE_FIELD_NAMES, true);
    }

    public static String generateItemMapping(){
        StringBuffer sw = new StringBuffer();
        try {
           sw.append("{");
           sw.append(generateNgramAynalyz());
           sw.append(",");
           sw.append("\"mappings\":{");
                sw.append("\""+ Constants.ITEM+"\":{");
                    sw.append("\"properties\":{");
                        sw.append("\"id\":{");
                            sw.append("\"type\":\"keyword\",\"index\":\"not_analyzed\"");
                        sw.append("},");
                        sw.append("\"attribute\":{");
                            sw.append("\"properties\":{");
                                sw.append("\"name\":{");
                                    sw.append("\"type\":\"keyword\",\"index\":\"not_analyzed\"");
                                sw.append("},");
                                sw.append("\"value\":{");
                                    sw.append("\"type\":\"keyword\",\"index\":\"not_analyzed\"");
                                sw.append("},");
                                sw.append("\"textValue\":{");
                                    sw.append("\"type\":\"text\",\"analyzer\":\""+NGRAM_ANAYZER+"\"");
                                sw.append("}");
                            sw.append("}");
                    sw.append("}");
                sw.append("}");
           sw.append("}");
           sw.append("}");
           sw.append("}");
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return sw.toString();
    }

    public static String generateNgramAynalyz(){
        StringBuffer sw = new StringBuffer();
        try {
            sw.append("\"settings\":{");
            sw.append("\"analysis\": {");
            sw.append("\"analyzer\": {");
            sw.append("\""+NGRAM_ANAYZER+"\": {");
            sw.append("\"tokenizer\": \"my_tokenizer\"");
            sw.append("}");
            sw.append("},");
            sw.append("\"tokenizer\": {");
            sw.append("\"my_tokenizer\": {");
            sw.append("\"type\": \"ngram\",");
            sw.append("\"min_gram\": 1,");
            sw.append(" \"max_gram\": 1");
            sw.append("}");
            sw.append("}");
            sw.append("}");
            sw.append("}");
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return sw.toString();
    }

    public static String generateMultiInsertItem(List<Item> items){
        if (CollectionUtils.isEmpty(items)) {
            return "";
        }
        StringBuffer sw = new StringBuffer();
        try {
            for (Item item : items) {
                sw.append(generateBulkIndex());
                sw.append("\n");
                sw.append(generateBulkIndexItem(item));
                sw.append("\n");
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return sw.toString();
    }

    public final static String generateBulkIndex(){
        return "{ \"index\": {}}";
    }

    public static String generateBulkIndexItem(Item item){
        StringWriter sw = new StringWriter();
        JsonFactory jsonFactory = objectMapper.getJsonFactory();
        JsonGenerator jg = null;
        try {
            jg = jsonFactory.createJsonGenerator(sw);
            jg.writeStartObject();
            jg.writeStringField("id", item.getId());
            jg.writeFieldName("attribute");
            jg.writeStartArray();
            if (CollectionUtils.isNotEmpty(item.getItemAttributes())) {
                for (ItemAttribute attribute : item.getItemAttributes()) {
                    jg.writeStartObject();
                    jg.writeStringField("name",attribute.getName());
                    jg.writeStringField("value",attribute.getValue());
                    jg.writeStringField("text",attribute.getValue());
                    jg.writeEndObject();
                }
            }
            jg.writeStartObject();
            jg.writeStringField("name",ITEM_NAME);
            jg.writeStringField("value",item.getName());
            jg.writeStringField("text",item.getName());
            jg.writeEndObject();
            jg.writeEndArray();
            jg.writeEndObject();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        } finally {
            Closer.close(jg);
        }
        return sw.toString();
    }
}
