package com.example.shapetest.shape;

import com.example.annotationtest.ShapeFactory;
import com.example.shapetest.AnnotatedClass;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * 存放annotationElement信息
 */
public class FactoryAnnotatedClass extends AnnotatedClass {
    private TypeElement mAnnotatedClassElement;
    //annotation type的完整类名
    private String mQualifiedSuperClassName;
    private String mSimpleTypeName;
    //annotation id
    private String mId;

    public FactoryAnnotatedClass(TypeElement classElement) {
        this.mAnnotatedClassElement = classElement;
        ShapeFactory annotation = classElement.getAnnotation(ShapeFactory.class);
        mId = annotation.id();
        if (mId.length() == 0) {
            throw new IllegalArgumentException(
                    String.format("id() in @%s for class %s is null or empty! that's not allowed",
                            ShapeFactory.class.getSimpleName(), classElement.getQualifiedName().toString()));
        }
        // Get the full QualifiedTypeName
        try {  // 该类已经被编译
            Class<?> clazz = annotation.type();
            //获取完整类名
            mQualifiedSuperClassName = clazz.getCanonicalName();

            mSimpleTypeName = clazz.getSimpleName();
        } catch (MirroredTypeException mte) {// 该类未被编译
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            mQualifiedSuperClassName = classTypeElement.getQualifiedName().toString();
            mSimpleTypeName = classTypeElement.getSimpleName().toString();
        }
    }

    public String getId(){
        return mId;
    }

    public TypeElement getTypeElement() {
        return mAnnotatedClassElement;
    }

    public String getQualifiedSuperClassName() {
        return mQualifiedSuperClassName;
    }

    public String getSimpleTypeName() {
        return mSimpleTypeName;
    }
}

