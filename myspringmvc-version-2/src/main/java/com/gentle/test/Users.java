package com.gentle.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author Gentle
 * @date 2019/06/20 : 11:38
 */
public class Users {
    private String name;

    private Users users;

    private Integer abc;

    public Integer getAbc() {
        return abc;
    }

    public void setAbc(String name,int abc) {
        this.abc = abc;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", users=" + users +
                ", abc=" + abc +
                '}';
    }

    public static void main(String[] args) {

        Users users = new Users();

        Class<? extends Users> aClass = users.getClass();
        Integer abc = 456;
        String name = "123";
        Object o = name;
        Object b =abc;

        System.out.println(Integer.class == int.class);


        Method[] methods = aClass.getMethods();

        Arrays.stream(methods).forEach(method -> {
            if ("setAbc".equals(method.getName())){
                Type[] genericParameterTypes = method.getGenericParameterTypes();
                System.out.println(genericParameterTypes[0]+"  "+genericParameterTypes[1]);
                Class<?>[] parameterTypes = method.getParameterTypes();

                try {
                    method.invoke(users,null,b);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println(users);
    }
}
