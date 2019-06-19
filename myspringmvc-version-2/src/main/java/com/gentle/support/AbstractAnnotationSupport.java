package com.gentle.support;

import com.gentle.support.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/06/16 : 16:23
 */
public abstract   class AbstractAnnotationSupport implements AnnotationSupport {

    protected static List<Class<?>> annontationList = new ArrayList<>();
    /**
     * 保存所有类文件名，用于注解扫描
     */
    protected  List<String> classNames = new ArrayList<>(32);
    static {
        annontationList.add(RestController.class);
        annontationList.add(Controller.class);
        annontationList.add(Bean.class);
        annontationList.add(RequestMapping.class);
        annontationList.add(Service.class);
        annontationList.add(Component.class);
        annontationList.add(Autowire.class);
    }

    /**
     * 扫描所有包，将文件名加入集合中
     *
     * @param packagePath 包路径（properties读取的） 例如：com.gentle
     * @param filePath    文件路径  转换后的路径 com/gentle
     */
    protected void packageScan(String packagePath, String filePath) {
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
                classNames.add(packagePath + "." + e.getName().substring(0, e.getName().lastIndexOf(".")));
            }
        });
    }




}
