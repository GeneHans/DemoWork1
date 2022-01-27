package com.example.shapetest.textinput;

import com.example.shapetest.shape.IdAlreadyUsedException;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * 存放FactoryAnnotatedClass，并且在这个类中完成ShapeFactory类的代码生成
 */
public class InputTextGroupedClasses {

    private static final String SUFFIX = "Factory";
    private String qualifiedClassName;


    private Map<String, InputTextAnnotatedClass> itemsMap = new LinkedHashMap<>();

    public InputTextGroupedClasses(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    public void add(InputTextAnnotatedClass toInsert) {
        InputTextAnnotatedClass factoryAnnotatedClass = itemsMap.get(toInsert.getId());
        if (factoryAnnotatedClass != null) {
            throw new IdAlreadyUsedException(factoryAnnotatedClass);
        }
        itemsMap.put(toInsert.getId(), toInsert);
    }

    /**
     * 生成ShapeFactory类代码生成
     * @param elementUtils
     * @param filer
     * @throws IOException
     */
    public void generateCode(Elements elementUtils, Filer filer) throws IOException {
        TypeElement superClassName = elementUtils.getTypeElement(qualifiedClassName);
        String factoryClassName = superClassName.getSimpleName() + SUFFIX;
        String qualifiedFactoryClassName = qualifiedClassName + SUFFIX;
        PackageElement pkg = elementUtils.getPackageOf(superClassName);
        String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();

        MethodSpec.Builder method = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "id")
                .returns(TypeName.get(superClassName.asType()));
        method.beginControlFlow("if (id == null)")
                .addStatement("throw new IllegalArgumentException($S)", "id is null!")
                .endControlFlow();

        for (InputTextAnnotatedClass item : itemsMap.values()) {
            method.beginControlFlow("if ($S.equals(id))", item.getId())
                    .addStatement("return new $L()", item.getTypeElement().getQualifiedName().toString())
                    .endControlFlow();
        }

        method.addStatement("throw new IllegalArgumentException($S + id)", "Unknown id = ");

        TypeSpec typeSpec = TypeSpec
                .classBuilder(factoryClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(method.build())
                .build();

        JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
    }

}

