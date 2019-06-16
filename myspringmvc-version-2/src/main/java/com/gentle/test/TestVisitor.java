package com.gentle.test;

import org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class TestVisitor extends ClassVisitor {

    public TestVisitor(int asmVersion) {
        super(asmVersion);
    }

//    @Override
//    public MethodVisitor visitMethod(int access, String name, String desc,
//                                     String sig, String[] value) {
//        //如果字段加 final ,则可以有默认值value,否则为null
//        System.out.println(  access+"    "+   name+"    " + desc +"     "+sig+"    "+value);
//
//        return new MethodVisitor(Opcodes.ASM5) {
//
//
//        return super.visitMethod(access, name, desc, sig,value);
//    }



    public static void main(String[] args) throws IOException {
        Users users = new Users();
        users.setName("123123","123");
        System.out.println(users.getClass().getName().replace(".", "/") + ".class");
        ClassReader creader = new ClassReader(
                Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(
                        users.getClass().getName().replace(".", "/") + ".class")));
        TestVisitor visitor = new TestVisitor(Opcodes.ASM5);
        creader.accept(visitor, 0);
    }
}