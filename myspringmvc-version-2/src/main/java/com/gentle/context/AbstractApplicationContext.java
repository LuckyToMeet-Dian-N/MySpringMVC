package com.gentle.context;


import com.gentle.bean.BeanInfomation;
import com.gentle.beanfactory.BeanFactory;
import com.gentle.beanfactory.DefaultBeanFactory;
import com.gentle.util.Assert;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gentle
 * @date 2019/06/17 : 16:18
 */

public abstract class AbstractApplicationContext implements ConfigApplicationContext {

    /**
     * 注册bean到ioc中
     *
     * @param beanInfomation bean  信息
     */
    public abstract void registerBean(BeanInfomation beanInfomation);

    @Override
    public <T> T getBean(String name) {

        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(Class<?> clazz) {
        return getBeanFactory().getBean(clazz);
    }

    @Override
    public boolean isSingleton(String name) {
        return getBeanFactory().isSingleton(name);
    }

    @Override
    public <T> T getMapping(String url) {
        return getBeanFactory().getMapping(url);
    }

    @Override
    public void refresh(List<BeanInfomation> beanInfomations) {
        try {


            initIoc(beanInfomations);




        } catch (Exception e) {

        }
    }

    private void initIoc(List<BeanInfomation> beanInfomations) {
        beanInfomations.forEach(e-> getBeanFactory().registerBean(e));
    }

    private void intiScanPackage() {

//        URL filePath = this.getClass().getClassLoader().getResource("/"+packageScan.toString().replaceAll(".", "/"));
//
//        //扫描包下所有的文件
//        packageScan(packageScan.toString(), filePath.getPath()+packageScan.toString().replace(".","/"));

    }
    /**
     * 扫描所有包，将文件名加入集合中
     *
     * @param packagePath 包路径（properties读取的） 例如：com.gentle
     * @param filePath    文件路径  转换后的路径 com/gentle
     */
    private void packageScan(String packagePath, String filePath) {
        File file = new File(filePath.trim());
        File[] files = file.listFiles();
        if (files == null) {
            System.out.println("没有文件");
            return;
        }
        //递归扫描文件
        Arrays.stream(files).forEach(e -> {
            if (e.isDirectory()) {
                packageScan(packagePath + "." + e.getName(), filePath + "/" + e.getName());
            } else {
//                classNames.add(packagePath + "." + e.getName().substring(0, e.getName().lastIndexOf(".")));
            }
        });
    }


    private void initAnnotation(DefaultBeanFactory beanFactory) {


    }


}
