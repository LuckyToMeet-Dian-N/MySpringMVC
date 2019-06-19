package com.gentle.support;

import com.gentle.bean.BeanInfomation;
import com.gentle.beanfactory.DefaultBeanFactory;
import com.gentle.util.Assert;
import com.gentle.util.ClassUtils;

import java.util.List;

/**
 * @author Gentle
 * @date 2019/06/16 : 16:23
 */
public class AnnotationLoad extends AbstractAnnotationSupport {

    private List<String> packagePath;

    private DefaultBeanFactory defaultBeanFactory;


    public AnnotationLoad(List<String> packagePath) {
        this.packagePath = packagePath;
    }


    @Override
    public void annotationLoader() {

        Assert.notNull(packagePath);
        //扫描包下所有文件，并将文件名保存下来
        initScanClassFile();

        doAnnotationLoader();

    }

    private void doAnnotationLoader() {

        for (String clazz : classNames) {
            try {
                Class<?> classByClassName = ClassUtils.getClassByClassName(clazz);
                BeanInfomation beanInfomation = AnnotationContext.executeStrategy(classByClassName);
                Assert.notNull(beanInfomation);
                defaultBeanFactory.registerBean(beanInfomation);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void initScanClassFile() {
        packagePath.forEach(e -> {
            String filthPath = e.replaceAll(".", "/");
            packageScan(e, filthPath);
        });
    }


    public List<String> getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(List<String> packagePath) {
        this.packagePath = packagePath;
    }

    public void setDefaultBeanFactory(DefaultBeanFactory defaultBeanFactory) {
        this.defaultBeanFactory = defaultBeanFactory;
    }
}
