package com.example.shapetest;

import com.example.shapetest.hello.IHelloWorld;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.lang.model.element.Modifier;

public class MyClass {
    public static void main(String args[]) {
        IHelloWorld iHelloWorld = new IHelloWorld();
        iHelloWorld.print();
        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")//声明类名为HelloWorld
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)//声明类的修饰符为 public final
                .addMethod(getMethodSpec("hello1", "Hello"))//为HelloWorld类添加名为hello1的方法，返回值为Hello
                .addMethod(getMethodSpec("hello2", "Java"))//同上
                .addMethod(getMethodSpec("hello3", "Poet!"))//同上
                .build();

        // 建立 com.aiiage.testjavapoet.HelloWorld.java 对象
        JavaFile javaFile = JavaFile.builder("com.example.shapetest.hellp", helloWorld)
                .addFileComment(" This codes are generated automatically. Do not modify!")
                .build();
        // 写入文件
        try {
            javaFile.writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @param methodStr  方法名
     * @param returnStr  返回值
     * @return
     */
    private static MethodSpec getMethodSpec(String methodStr, String returnStr) {
        return MethodSpec.methodBuilder(methodStr)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)//指定方法修饰符为 public static
                .returns(String.class) //指定返回值为String类型
                .addStatement("return $S", returnStr) //拼接返回值语句
                .build();
    }
}