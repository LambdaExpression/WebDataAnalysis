package org.tcat.tools.vo;

import java.io.Serializable;

/**
 * Created by Lin on 2016/5/23.
 */
public class ListMapCriteriaVo implements Serializable{

    private static final long serialVersionUID = 1L;
    private String name;
    private  Object value;

    public ListMapCriteriaVo(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
