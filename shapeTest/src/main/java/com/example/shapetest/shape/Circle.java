package com.example.shapetest.shape;

@ShapeFactory(type = com.example.shapetest.shape.IShape.class, id = "Circle")
public class Circle implements IShape {
    @Override
    public void draw() {
        System.out.println("这是一个圆形");
    }
}
