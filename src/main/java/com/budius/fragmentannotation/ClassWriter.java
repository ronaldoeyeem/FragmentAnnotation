package com.budius.fragmentannotation;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by budius on 26.08.14.
 */
public class ClassWriter {

    public static void write(ClassInfo i, Writer w) throws IOException {

        String line = "\n";

        // package name, imports and class declaration
        w.append("package ");
        w.append(i.builderPackage);
        w.append(";");
        w.append(line);
        w.append(line);
        w.append("import android.os.Bundle;");
        w.append(line);
        w.append(line);
        w.append("public class ");
        w.append(i.builderClassName);
        w.append(" {\n");
        line = Utils.addIndentation(line);

        // define all the KEYS for the argument
        w.append(line);
        for (Argument a : i.argumentList) {
            w.append("private static final String ");
            w.append(a.key);
            w.append(" = \"");
            w.append(a.key_value);
            w.append("\";");
            w.append(line);
        }
        w.append(line);

        // define the bundle
        w.append("private Bundle b = new Bundle();");
        w.append(line);

        // add setter methods
        for (Argument a : i.argumentList) {
            w.append(line);
            w.append("public ");
            w.append(i.builderClassName);
            w.append(" set");
            w.append(Utils.firstUpper(a.name));
            w.append("(");
            w.append(a.type);
            w.append(" ");
            w.append(a.name);
            w.append(") {");
            line = Utils.addIndentation(line);
            w.append(line);
            w.append("b.put");
            w.append(Utils.firstUpper(Utils.simplifiedType(a.type)));
            w.append("(");
            w.append(a.key);
            w.append(", ");
            w.append(a.name);
            w.append(");");
            w.append(line);
            w.append("return this;");
            line = Utils.removeIndentation(line);
            w.append(line);
            w.append("}");
            w.append(line);
        }

        // add getter methods
        for (Argument a : i.argumentList) {
            w.append(line);
            w.append("public ");
            w.append(a.type);
            w.append(" get");
            w.append(Utils.firstUpper(a.name));
            w.append("(");
            w.append(a.type);
            w.append(" ");
            w.append(a.name);
            w.append(") {");
            line = Utils.addIndentation(line);
            w.append(line);
            w.append("return b.get");
            w.append(Utils.firstUpper(Utils.simplifiedType(a.type)));
            w.append("(");
            w.append(a.key);
            w.append(");");
            line = Utils.removeIndentation(line);
            w.append(line);
            w.append("}");
            w.append(line);
        }

        // fragment built method
        w.append(line);
        w.append("public ");
        w.append(i.fragmentClassName);
        w.append(" build() {");
        line = Utils.addIndentation(line);
        w.append(line);
        w.append(i.fragmentClassName);
        w.append(" f = new ");
        w.append(i.fragmentClassName);
        w.append("();");
        w.append(line);
        w.append("f.setArguments(b);");
        w.append(line);
        w.append("return f;");
        line = Utils.removeIndentation(line);
        w.append(line);
        w.append("}");
        w.append(line);

        // bundle built method
        w.append(line);
        w.append("public Bundle getBundle() { return b; }");
        w.append(line);

        // static method to inject parameters
        w.append(line);
        w.append("public static void inject(");
        w.append(i.fragmentClassName);
        w.append(" fragment) {");
        line = Utils.addIndentation(line);
        for (Argument a : i.argumentList) {
            w.append(line);
            w.append("fragment.");
            w.append(a.name);
            w.append(" = fragment.getArguments().get");
            w.append(Utils.firstUpper(Utils.simplifiedType(a.type)));
            w.append("(");
            w.append(a.key);
            w.append(");");
        }
        line = Utils.removeIndentation(line);
        w.append(line);
        w.append("}");

        // end of class
        w.append("\n}");
    }

}
