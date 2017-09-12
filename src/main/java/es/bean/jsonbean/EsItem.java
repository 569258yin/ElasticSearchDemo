package es.bean.jsonbean;

import java.util.List;

/**
 * Created by kevinyin on 2017/9/11.
 */
public class EsItem {

    private String id;
    private List<EsItemAttribute> attribute;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<EsItemAttribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<EsItemAttribute> attribute) {
        this.attribute = attribute;
    }
}
