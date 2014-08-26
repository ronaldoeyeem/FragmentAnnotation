package com.budius.fragmentannotation;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Set;

/**
 * Created by budius on 26.08.14.
 */
class ClassInfo {

    private static final String PACKAGE = "com.budius.FragmentAnnotation.Builders";
    private static final String CLASS_POSTFIX = "Builder";

    ClassInfo(Element klass, Set<? extends Element> annotatedElements) {
        if (!(klass instanceof TypeElement))
            throw new IllegalArgumentException(klass.getClass().getCanonicalName() + " is not a TypeElement");

        TypeElement classElement = (TypeElement) klass;
        PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

        fragmentClassName = klass.getSimpleName().toString();
        fragmentPackage = packageElement.getQualifiedName().toString();
        fragmentQualifiedClassName = classElement.getQualifiedName().toString();

        builderClassName = fragmentClassName + CLASS_POSTFIX;
        builderPackage = fragmentPackage;
        builderQualifiedClassName = builderPackage + "." + builderClassName;

        argumentList = Argument.getArguments(fragmentQualifiedClassName, klass, annotatedElements);
    }

    boolean shouldCreateClass() {
        return argumentList.size() > 0;
    }

    final List<Argument> argumentList;
    final String fragmentClassName;
    final String fragmentPackage;
    final String fragmentQualifiedClassName;
    final String builderClassName;
    final String builderPackage;
    final String builderQualifiedClassName;

    @Override
    public String toString() {
        return builderQualifiedClassName;
    }
}
