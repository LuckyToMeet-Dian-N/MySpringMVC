package com.gentle.bean;


/**
 * @author Gentle
 * @date 2019/06/17 : 16:26
 */
public class BeanInfomation {

    private String id;

    private String clazz;

    private Boolean singlon;

    private String type = "bean";

    private String methodName;

    private String url;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
