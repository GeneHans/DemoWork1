package com.example.shapetest.textinput;

import com.example.annotationtest.InputAnnotation;
import com.example.shapetest.shape.ProcessingException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

public class InputProcessor extends AbstractProcessor {


    private Types mTypeUtils;
    private Messager mMessager;
    private Filer mFiler;
    private Elements mElementUtils;

    private Map<String, InputTextGroupedClasses> factoryClasses = new LinkedHashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        try {
            //  通过RoundEnvironment获取到所有被@InputAnnotation注解的对象
            for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(InputAnnotation.class)) {
                //指定此注解只能使用在Field上
                if (annotatedElement.getKind() != ElementKind.FIELD) {
                    //注解没有使用在Field上，直接抛出异常终止编译
                    throw new ProcessingException(annotatedElement, "Only Filed can be annotated with @%s",
                            InputAnnotation.class.getSimpleName());
                }
                TypeElement typeElement = (TypeElement) annotatedElement;
                //新建FactoryAnnotatedClass对象存放annotationElement信息
                InputTextAnnotatedClass annotatedClass = new InputTextAnnotatedClass(typeElement);
                //检查是否格式正确
                checkValidClass(annotatedClass);

                //创建ShapeFactory类的代码生成类
                InputTextGroupedClasses factoryClass = factoryClasses.get(annotatedClass.getQualifiedSuperClassName());
                //防止反复创建
                if (factoryClass == null) {
                    //添加Element信息
                    String qualifiedGroupName = annotatedClass.getQualifiedSuperClassName();
                    factoryClass = new InputTextGroupedClasses(qualifiedGroupName);
                    factoryClasses.put(qualifiedGroupName, factoryClass);
                }

                // Checks if id is conflicting with another @Factory annotated class with the same id
                //判断是否与另外的注解有着相同的ID，如果有则需要抛出异常
                factoryClass.add(annotatedClass);
            }

            // 生成ShapeFactory代码
            for (InputTextGroupedClasses factoryClass : factoryClasses.values()) {
                factoryClass.generateCode(mElementUtils, mFiler);
            }
            //清空创建队列
            factoryClasses.clear();
        } catch (ProcessingException e) {
            error(e.getElement(), e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mTypeUtils = processingEnvironment.getTypeUtils();
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
    }

    /**
     * 此方法返回要处理的注解类型的名称set集合
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotation = new LinkedHashSet<>();
        //此处必须是完整的包名+类名
        annotation.add(InputAnnotation.class.getCanonicalName());
        return annotation;
    }

    /**
     * 用来指定当前正在使用的Java版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    private void checkValidClass(InputTextAnnotatedClass item) throws ProcessingException {
        TypeElement classElement = item.getTypeElement();

        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
            throw new ProcessingException(classElement, "the class %s is not public", classElement.getQualifiedName().toString());
        }

        //有抽象方法终止编译
        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
            throw new ProcessingException(classElement, "the class %s is abstract，can't annotate abstract class", classElement.getQualifiedName().toString());
        }

        TypeElement superClassElement = mElementUtils.getTypeElement(item.getQualifiedSuperClassName());
        //检查被注解类是否实现了或者继承了注解ShapeFactory.type() 的指定类
        if (superClassElement.getKind() == ElementKind.INTERFACE) {
            if (!classElement.getInterfaces().contains(superClassElement.asType())) {
                throw new ProcessingException(classElement,
                        "The class %s annotated with @%s must implement the interface %s",
                        classElement.getQualifiedName().toString(), InputAnnotation.class.getSimpleName(),
                        item.getSimpleTypeName());
            }
        } else {
            TypeElement currentClass = classElement;
            while (true) {
                TypeMirror superClassType = currentClass.getSuperclass();
                if (superClassType.getKind() == TypeKind.NONE) {
                    //向上遍历父类，直到Object也没有获取到所需的父类，终止循环抛出异常
                    throw new ProcessingException(classElement,
                            "The class %s annotated with @%s must inherit from %s",
                            classElement.getQualifiedName().toString(), InputAnnotation.class.getSimpleName(),
                            item.getQualifiedSuperClassName());
                }
                if (superClassType.toString().equals(item.getQualifiedSuperClassName())) {
                    //校验通过，终止遍历
                    break;
                }
                currentClass = (TypeElement) mTypeUtils.asElement(superClassType);
            }
        }

        //检查是否由public的无参构造方法
        for (Element enclosed : classElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructorElement = (ExecutableElement) enclosed;
                if (constructorElement.getParameters().size() == 0 &&
                        constructorElement.getModifiers().contains(Modifier.PUBLIC)) {
                    //存在public的无参构造方法，检查结束
                    return;
                }
            }
        }
        // 为检测到public的无参构造方法，抛出异常，终止编译
        throw new ProcessingException(classElement,
                "The class %s must provide an public empty default constructor",
                classElement.getQualifiedName().toString());

    }

    private void error(Element e, String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }

    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }

}
