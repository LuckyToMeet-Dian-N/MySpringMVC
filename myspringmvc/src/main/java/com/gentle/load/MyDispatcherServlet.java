package com.gentle.load;

import com.gentle.annotation.*;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gentle
 * @date 2019/04/06 : 20:31
 * 第一版本的 springmvc 实现，完全无框架艺术，仅仅为了实现而实现
 * 该版本支持多种参数类型。不支持 对象->对象->对象，后期进行递归修改
 * 下一版本将以框架思想编写该 MVC 框架。拭目以待
 */
public class MyDispatcherServlet extends HttpServlet {
    /**
     *    ioc 容器
     */
    private Map<String, Object> beans = new ConcurrentHashMap<>();
    /**
     *  存储所有类文件名字
     */
    private List<String> classNames = new ArrayList<>();
    /**
     * 映射容器
     */
    private Map<String, Object> mapping = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * 处理请求
     * @param request request 对象
     * @param response response 对象
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求的 URL
        String requestURI = request.getRequestURI();
        //获取 上下文
        String contextPath = request.getContextPath();
        //如果有上下文，替换掉，否则都是404
        String url = requestURI.replace(contextPath, "");
        //将请求来的第一个 /  去掉
        url = url.substring(url.indexOf("")+1);

        System.out.println("请求的url:"+url+"     上下文url："+contextPath+"    requestURI: "+requestURI);
        //去映射容器中拿方法
        Method method = (Method) mapping.get(url);

        if (method==null){
            response.getWriter().write("404 not the fond");
            return;
        }
        //获取所有请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //获取方法中的参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        //存储值，用于注入方法
        List<Object> list = new ArrayList<>();
        //用于存储参数类型和参数名对应
        Map<String, String> map = new LinkedHashMap<>();
        //获取参数名
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        if (parameterNames == null) {
            return;
        }

        //参数类型和参数名字匹配
        for (int i = 0; i < parameterTypes.length; i++) {
            map.put(parameterTypes[i].getName(), parameterNames[i]);
        }
        //类型判断
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (String.class.getName().equals(entry.getKey())) {
                list.add(parameterMap.get(entry.getValue())[0]);
            } else if (Integer.class.getName().equals(entry.getKey())) {
                list.add(Integer.valueOf(parameterMap.get(entry.getValue())[0]));
            } else if (Double.class.getName().equals(entry.getKey())) {
                list.add(Double.valueOf(parameterMap.get(entry.getValue())[0]));
            } else if (Long.class.getName().equals(entry.getKey())) {
                list.add(Long.valueOf(parameterMap.get(entry.getValue())[0]));
            } else if (int.class.getName().equals(entry.getKey())) {
                list.add(Integer.valueOf(parameterMap.get(entry.getValue())[0]));
            } else if (long.class.getName().equals(entry.getKey())) {
                list.add(Long.valueOf(parameterMap.get(entry.getValue())[0]));
            } else if (double.class.getName().equals(entry.getKey())) {
                list.add(Double.valueOf(parameterMap.get(entry.getValue())[0]));
            }else if (HttpServletRequest.class.getName().equals(entry.getKey())){
                list.add(request);
            }else if (HttpServletResponse.class.getName().equals(entry.getKey())) {
                list.add(response);
            } else {
                //如无上述类型，为对象 例如 User 对象。构造对象，并将属性值注入
                String key = entry.getKey();
                Class clazz = getClazz(key);
                Object o = null;
                try {
                    o = clazz.newInstance();
                } catch (InstantiationException e) {
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //虎丘所有字段
                Field[] declaredFields = clazz.getDeclaredFields();

                for (Field field : declaredFields) {
                    //取消 私有权限
                    field.setAccessible(true);
                    //根据字段名字，找请求过来的参数是有有对应值
                    String[] strings = parameterMap.get(field.getName());
                    if (strings != null) {
                        try {
                            Class<?> type = field.getType();
                            if (String.class.getName().equals(type.getName())) {
                                field.set(o, String.valueOf(strings[0]));
                            } else if (Integer.class.getName().equals(type.getName())) {
                                field.set(o, Integer.valueOf(strings[0]));
                            } else if (Double.class.equals(type.getName())) {
                                field.set(o, Double.valueOf(strings[0]));
                            } else if (Long.class.equals(type.getName())) {
                                field.set(o, Long.valueOf(strings[0]));
                            } else if (int.class.equals(type.getName())) {
                                field.set(o, Integer.valueOf(strings[0]));
                            } else if (long.class.equals(type.getName())) {
                                field.set(o, Long.valueOf(strings[0]));
                            } else if (double.class.equals(type.getName())) {
                                field.set(o, Double.valueOf(strings[0]));
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //加入集合
                list.add(o);
            }
        }
        //集合转成对象数组
        Object[] objects1 = list.toArray();

        //参数位置转换（巨坑）
        try {
            //注入容器
            method.invoke(beans.get(url),objects1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void init(ServletConfig servletConfig) {

        String contextConfigLocation = servletConfig.getInitParameter("contextConfigLocation");
        //读取配置文件
        readProperties(contextConfigLocation);
        //注入 ioc 对象
        initIoc();
        //mapping 地址初始
        initMapping();

        //autowire 注入属性
        injectBeanByAutowire();
    }

    /**
     * 初始化 ico 容器，将对象注入
     */
    private void initIoc() {
        for (String beanName : classNames) {
            Class clazz = getClazz(beanName);
            String key = null;
            //相关注解注入
            if (clazz.isAnnotationPresent(MyController.class)) {
                MyController myController = (MyController) clazz.getAnnotation(MyController.class);
                key = getKey(clazz, myController.value());
            } else if (clazz.isAnnotationPresent(MyService.class)) {
                MyService myService = (MyService) clazz.getAnnotation(MyService.class);
                key = getKey(clazz, myService.value());
            }

            //空的情况下，说明类没有上述注解
            if (key != null) {
                Object o = null;
                try {
                    //将对象注入 ioc 容器内，key 为对象的名字
                    o = clazz.newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                //new 出的对象注入 ioc 容器。
                beans.put(key, o);
                //判断有没有接口，如果有接口，也一起注入
                Class[] interfaces = clazz.getInterfaces();
                if (interfaces.length != 0) {
                    for (Class i : interfaces) {
                        beans.put(lowerFirst(i), o);
                    }
                }
            }
        }
    }

    /**
     * @param clazz 类对象
     * @param value 注解中的值，如果为空则则为类名首字母小写
     * @return 返回容器的键
     */
    private String getKey(Class clazz, String value) {
        String key;
        //为空时，说明默认使用类名首字母小写
        if ("".equals(value)) {
            key = lowerFirst(clazz);
        } else {
            key = value;
        }
        return key;
    }

    /**
     * 初始化映射器，
     */
    private void initMapping() {
        if (beans.isEmpty()){

            return;
        }
        try{
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            //找到所有方法
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                String key = "";
                //判断类上是否也有 MyRequestMapping 参数
                if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
                    MyRequestMapping myRequestMapping = (MyRequestMapping) clazz.getAnnotation(MyRequestMapping.class);
                    key = myRequestMapping.value();
                }
                //判断方法上是否有 MyRequestMapping 注解标注
                if (method.isAnnotationPresent(MyRequestMapping.class)) {
                    MyRequestMapping annotation = method.getAnnotation(MyRequestMapping.class);
                    String value = annotation.value();
                    //拼接起来
                    value = key + "/" + value;
                    //映射容器中是否已经存在该键
                    if (mapping.containsKey(value)) {
                        throw new IllegalArgumentException("地址已经存在：" + method.getName() + ":" + value);
                    }
                    //注入容器
                    mapping.put(value, method);
                    beans.put(value,entry.getValue());
                }
            }
        }}catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取 class 对象
     *
     * @param beanName 类名
     * @return 类对象
     */
    private Class getClazz(String beanName) {
        Class clazz = null;
        try {
            clazz = Class.forName(beanName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert clazz != null;
        return clazz;
    }

    /**
     * 为被 @Autowire 注解标注的字段注入属性值
     */
    private void injectBeanByAutowire() {

        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Class clazz = entry.getValue().getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(MyAutowire.class)) {
                    continue;
                }
                MyAutowire annotation = field.getAnnotation(MyAutowire.class);
                String value = annotation.value();
                //去除私有权限
                field.setAccessible(true);
                //为空的时候，进行对象类型匹配
                if ("".equals(value)) {
                    String simpleName = field.getType().getSimpleName();
                    Object objectByObjectType = findObjectByObjectType(simpleName);
                    setBean(clazz, field, objectByObjectType);
                } else {
                    //不为空直接时根据输入的值返回
                    Object o = beans.get(value);
                    setBean(clazz, field, o);
                }

            }
        }
    }

    /**
     * @param clazz 类对象
     * @param field 类的字段
     * @param o     放入的值
     */
    private void setBean(Class clazz, Field field, Object o) {
        try {
            //将值注入 @Autowire 标注的对象
            field.set(beans.get(lowerFirst(clazz)), o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将首字母小写， 例如：User  变成 user
     *
     * @param clazz 类对象
     * @return 首字母小写
     */
    private String lowerFirst(Class clazz) {
        char[] chars = clazz.getSimpleName().toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }


    /**
     * 根据对象类型查找相关对象
     *
     * @return 返回对象地址，如无该地址返回空
     */
    private Object findObjectByObjectType(String beanName) {
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object value = entry.getValue();
            Class<?> aClass = value.getClass();
            //没有实现接口的类，直接根据名字找到返回对象
            if (aClass.getSimpleName().equals(beanName)) {
                return value;
            }
            //如果是有接口的类，将对应接口也注入
            Class<?>[] interfaces = aClass.getInterfaces();
            if (interfaces.length != 0) {
                for (Class c : interfaces) {
                    if (c.getSimpleName().equals(beanName)) {
                        return value;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 读取 properties 配置文件
     */
    private void readProperties(String  location) {

        ClassLoader classLoader = this.getClass().getClassLoader();
        location = location.substring(location.indexOf(":")+1);
        InputStream is = classLoader.getResourceAsStream(location);
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object packageScan = properties.get("packageScan");
        URL filePath = this.getClass().getClassLoader().getResource("/"+packageScan.toString().replaceAll(".", "/"));

        //扫描包下所有的文件
        packageScan(packageScan.toString(), filePath.getPath()+packageScan.toString().replace(".","/"));
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
                classNames.add(packagePath + "." + e.getName().substring(0, e.getName().lastIndexOf(".")));
            }
        });

    }
}
