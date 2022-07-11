package com.example.demowork1.annotation.shape;

import com.example.annotationtest.ShapeFactory;
import com.example.demowork1.util.LogUtil;

@ShapeFactory(type = IShape.class,id = "Circle")
public class Circle implements IShape {
    @Override
    public void draw() {
        LogUtil.INSTANCE.d("这是一个圆形");
    }
}
