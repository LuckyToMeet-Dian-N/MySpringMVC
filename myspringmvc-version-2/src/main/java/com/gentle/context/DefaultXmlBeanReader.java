package com.gentle.context;

import com.gentle.bean.BeanInfomation;
import com.gentle.resources.Loader;
import com.gentle.util.Assert;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/06/17 : 11:18
 */
public class DefaultXmlBeanReader implements Loader {
    private static final String SUFFIX = "xml";
    private static final  String MATCH= "*";

    private List<BeanInfomation> beanInfomations = new ArrayList<>();

    /**
     * 载入所有 xml 文件
     * @param location 文件名
     */
    @Override
    public void loader(String location) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        location = location.substring(location.indexOf(":") + 1);
        if (!SUFFIX.equalsIgnoreCase(location)) {
            throw new IllegalArgumentException("不支持的文件类型[ " + location + " ]");
        }
        InputStream is = classLoader.getResourceAsStream(location);

        Assert.notNull(is);

        loader(is);

    }

    /**
     * 解析 xml 找到对应的 bean 信息
     *
     * @param inputStream 输入流
     */
    private void parseXml(InputStream inputStream) {

        SAXReader reader = new SAXReader();
        try {
            Document read = reader.read(inputStream);
            Element rootElement = read.getRootElement();
            Iterator<Element> it = rootElement.elementIterator();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loader(InputStream inputStream) {
        parseXml(inputStream);
    }

    /**
     * 获取解析的 bean 信息
     * @return
     */
    public List<BeanInfomation> getBeanInfomations() {
        return beanInfomations;
    }
}
