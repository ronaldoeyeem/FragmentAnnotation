package com.budius.fragmentannotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * Created by budius on 25.08.14.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("com.budius.fragmentannotation.FragArg")
public class FragmentBuilder extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment env) {
        // getRootElements() will loop through all the classes
        processClasses(env.getRootElements(), env.getElementsAnnotatedWith(FragArg.class));
        return true;
    }

    private void processClasses(Set<? extends Element> klasses, Set<? extends Element> annotatedElements) {

        for (Element klass : klasses) {

            ClassInfo classInfo = new ClassInfo(klass, annotatedElements);

            // only process this class if it has annotated elements
            if (!classInfo.shouldCreateClass())
                continue;

            log("Processing " + classInfo.toString() + ".");

            try {
                JavaFileObject file = processingEnv.getFiler().createSourceFile(classInfo.builderQualifiedClassName);
                Writer writer = file.openWriter();
                ClassWriter.write(classInfo, writer);
                writer.close();
            } catch (IOException e) {
                err("IOException: " + e.getMessage());
            }
        }
    }

    private void log(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }

    private void err(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }

}
