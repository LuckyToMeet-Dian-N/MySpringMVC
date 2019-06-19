package com.gentle.support;

import com.gentle.beanfactory.DefaultBeanFactory;
import com.gentle.util.Assert;

import java.io.File;
import java.util.Arrays;
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

    }

    private void  doAnnotationLoader(){


//        defaultBeanFactory.registerBean();

    }

    private void initScanClassFile(){
        packagePath.forEach(e->{
            String filthPath= e.replaceAll(".","/");
            packageScan(e,filthPath);
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
