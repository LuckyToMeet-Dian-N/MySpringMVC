package com.gentle.bean;

import com.gentle.util.TypeChoose;

/**
 * @author Gentle
 * @date 2019/06/17 : 16:26
 */
public class BeanInfomation {

    private String id;

    private String clazz;

    private Boolean singlon;

    private TypeChoose type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Boolean getSinglon() {
        return singlon;
    }

    public void setSinglon(Boolean singlon) {
        this.singlon = singlon;
    }

    public TypeChoose getType() {
        return type;
    }

    public void setType(TypeChoose type) {
        this.type = type;
    }
}
