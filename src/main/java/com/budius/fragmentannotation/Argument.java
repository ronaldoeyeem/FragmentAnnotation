package com.budius.fragmentannotation;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by budius on 26.08.14.
 */
class Argument {

    static final List<String> ACCEPTABLE_TYPES = Arrays.asList(
            // primitives
            "char", "int", "long", "byte", "boolean", "double", "float", "short",

            // java
            "java.lang.String",
            "java.lang.CharSequence",
            "java.io.Serializable",

            // android
            "android.os.Parcelable",
            "android.os.Bundle",
            "android.os.Parcelable"
    );

    static List<Argument> getArguments(String fragmentClassName, Element klass, Set<? extends Element> annotatedElements) {
        List<Argument> l = new ArrayList<Argument>();
        for (VariableElement field : ElementFilter.fieldsIn(klass.getEnclosedElements())) {
            if (annotatedElements.contains(field)) {
                l.add(new Argument(fragmentClassName, field));
            }
        }
        return l;
    }

    Argument(String fragmentClassName, VariableElement element) {
        type = element.asType().toString();
        name = element.getSimpleName().toString();
        key = "KEY_" + Utils.simplifiedType(type.toUpperCase()) + "_" + name.toUpperCase();
        key_value = fragmentClassName + ".key." + type.toLowerCase() + "." + name.toLowerCase();
        if (!ACCEPTABLE_TYPES.contains(type))
            throw new IllegalArgumentException("Bundle cannot contain type " + type);
    }

    final String type;
    final String name;
    final String key;
    final String key_value;
}
