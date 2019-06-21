package com.gentle.parameter;

import com.gentle.util.ClassUtils;
import org.objectweb.asm.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gentle
 * @date 2019/06/16 : 14:28
 */
public class LocalMethodParameterNameDiscoverer implements ParameterNameDiscoverer {
    /**
     * 缓存类对象中方法和方法中的参数
     */
    private static Map<Class<?>, Map<Member, List<String>>> methodParameterNameCache = new ConcurrentHashMap<>();

    @Override
    public String[] getParameterNames(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();

        Map<Member, List<String>> memberMap = methodParameterNameCache.get(declaringClass);
        if (memberMap == null) {
            inspectClass(method, declaringClass, new ConcurrentHashMap<>(16));
        } else if (memberMap.get(method) == null) {
            inspectClass(method, declaringClass, memberMap);
        } else {
            return objectToString( memberMap.get(method).toArray());
        }
        return objectToString( methodParameterNameCache.get(declaringClass).get(method).toArray());
    }

    private String[] objectToString(Object[] objects){

        String[] strings = new String[objects.length];
       for (int i =0;i<objects.length;i++){
           strings[i]= String.valueOf(objects[i]);
       }
       return strings;


    }

    /**
     *
     * @param method  方法
     * @param declaringClass 方法对应的类名
     * @param map 缓存存储对象
     */
    private void inspectClass(Method method, Class<?> declaringClass, Map<Member, List<String>> map) {
        String classFileName = ClassUtils.getClassFileName(declaringClass);
        //根据文件名拿该文件的流
        InputStream resourceAsStream = declaringClass.getResourceAsStream(classFileName);
        ClassReader classReader;
        try {
            //读取文件
            classReader = new ClassReader(resourceAsStream);
            //参数名发现方法
            ParameterNameDiscoveringVisitor parameterNameDiscoveringVisitor = new ParameterNameDiscoveringVisitor(Opcodes.ASM5, map, method);
            classReader.accept(parameterNameDiscoveringVisitor, 0);
            methodParameterNameCache.put(declaringClass, map);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                resourceAsStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ASM 类访问器
     */
    private static class ParameterNameDiscoveringVisitor extends ClassVisitor {

        private Map<Member, List<String>> memberMap;
        private Method method;

        ParameterNameDiscoveringVisitor(int api, Map<Member, List<String>> memberMap, Method method) {
            super(api);
            this.memberMap = memberMap;
            this.method = method;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc,
                                         String signature, String[] exceptions) {
            //判断当前方法是否是和传入方法一致
            if (name.equals(method.getName())) {
                return new MethodVisitor(Opcodes.ASM5) {
                    @Override
                    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                        //拿去实例方法中的方法参数名，静态方法忽略
                        if (index > 0) {

                            if (!memberMap.containsKey(method)){
                                memberMap.put(method,new ArrayList<>());
                            }
                            memberMap.get(method).add(name);

                        }
                    }
                };
            }
            return null;
        }

    }


}
