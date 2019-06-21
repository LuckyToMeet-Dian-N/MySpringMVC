package com.gentle.context;

import com.gentle.bean.BeanInfomation;
import com.gentle.beanfactory.DefaultBeanFactory;
import com.gentle.resources.Loader;
import com.gentle.util.Assert;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/06/17 : 11:18
 */
public class DefaultXmlBeanReader implements Loader {
    /**
     * xml 文件后缀
     */
    private static final String SUFFIX = "xml";
    private static final String MATCH = "*";
    /**
     * 存放 bean 信息
     */
    private List<BeanInfomation> beanInfomations = new ArrayList<>();

    /**
     * 载入所有 xml 文件
     *
     * @param location 文件名
     */
    @Override
    public void loader(String location) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        location = location.substring(location.indexOf(":") + 1);
        if (!SUFFIX.equalsIgnoreCase(location.substring(location.lastIndexOf(".") + 1))) {
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
            List<Element> beans = rootElement.elements();

            for (Element element : beans) {
                BeanInfomation beanInfomation = new BeanInfomation();
                Assert.notNull((element.attributeValue("id")));

                System.out.println(element.attributeValue("id"));
                beanInfomation.setId(element.attributeValue("id"));

                Assert.notNull((element.attributeValue("clazz")));

                beanInfomation.setClazz(element.attributeValue("clazz"));

                if (element.attributeValue("type") != null) {
                    beanInfomation.setType(element.attributeValue("type"));
                }
                beanInfomation.setMethodName(element.attributeValue("methodName"));

                beanInfomation.setUrl(element.attributeValue("url"));

                beanInfomation.setPackages(element.attributeValue("packages"));

                beanInfomations.add(beanInfomation);
            }
            System.out.println(beanInfomations);
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
     *
     * @return
     */
    public List<BeanInfomation> getBeanInfomations() {
        return beanInfomations;
    }


    public static void main(String[] args) {
        DefaultXmlBeanReader defaultXmlBeanReader = new DefaultXmlBeanReader();

        URL resource = defaultXmlBeanReader.getClass().getClassLoader().getResource("test.xml");


        defaultXmlBeanReader.loader("classpath:test.xml");

    }
}
