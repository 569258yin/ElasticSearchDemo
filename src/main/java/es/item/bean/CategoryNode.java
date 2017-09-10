package es.item.bean;


import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by gujinxin on 2017/2/17.
 */
public class CategoryNode {
    private String id;
    private String name;
    private List<CategoryNode> children;

    public CategoryNode() {
    }

    public CategoryNode(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.children = Lists.newArrayList();
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

    public List<CategoryNode> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryNode> children) {
        this.children = children;
    }
}
