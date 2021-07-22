package com.example.demowork1.annotation.shape;

import com.example.annotationtest.ShapeFactory;

@ShapeFactory(type = IShape.class, id = "Rectangle")
public class Rectangle implements IShape {

    @Override
    public void draw() {
        System.out.println("这是一个矩形");
    }
}
