package com.example.annotationtest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface InputAnnotation {
    Class type();
    String id();
    String errorMessage();
    String regEx();
}