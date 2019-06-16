package com.gentle.support;

import com.gentle.support.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/06/16 : 16:23
 */
public  class AbstractAnnotationSupport implements AnnotationSupport {

    private static List<Class<?>> annontationList = new ArrayList<>();

    static {
        annontationList.add(RestController.class);
        annontationList.add(Controller.class);
        annontationList.add(Bean.class);
        annontationList.add(RequestMapping.class);
        annontationList.add(Service.class);
        annontationList.add(Component.class);
        annontationList.add(Autowire.class);
    }





}
