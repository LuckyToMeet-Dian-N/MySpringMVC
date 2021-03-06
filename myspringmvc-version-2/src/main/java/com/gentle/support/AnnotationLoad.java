package com.gentle.support;

import com.gentle.bean.BeanInfomation;
import com.gentle.beanfactory.DefaultBeanFactory;
import com.gentle.util.Assert;
import com.gentle.util.ClassUtils;

import java.net.URL;
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

    /**
     * 注解初始化
     */
    @Override
    public void annotationLoader() {

        Assert.notNull(packagePath);
        //扫描包下所有文件，并将文件名保存下来
        initScanClassFile();
        //注册 bean 到容器中
        doAnnotationLoader();
        //初始化映射
        initMapping(defaultBeanFactory);
        //初始化 autowire 注入
        initInjection(defaultBeanFactory);

    }



    /**
     * 构建bean 信息，并注入
     */
    private void doAnnotationLoader() {

        for (String clazz : classNames) {
            try {
                Class<?> classByClassName = ClassUtils.getClassByClassName(clazz);
                BeanInfomation beanInfomation = AnnotationContext.executeStrategy(classByClassName);
                if (beanInfomation==null){
                    continue;
                }
                defaultBeanFactory.registerBean(beanInfomation);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化时扫描文件
     */
    private void initScanClassFile() {
        packagePath.forEach(e -> {
            URL filePath = this.getClass().getClassLoader().getResource(e.replace(".", "/"));
            packageScan(e, filePath.getPath());
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
