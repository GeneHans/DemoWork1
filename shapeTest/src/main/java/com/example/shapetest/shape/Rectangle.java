package com.example.shapetest.shape;

@ShapeFactory(type = com.example.shapetest.shape.IShape.class, id = "Rectangle")
public class Rectangle implements IShape {

    @Override
    public void draw() {
        System.out.println("这是一个矩形");
    }
}
