package com.example.shapetest.hello;

public class IHelloWorld implements IHello{
    @Override
    public void print() {
        System.out.println("Hello World");
    }
}
