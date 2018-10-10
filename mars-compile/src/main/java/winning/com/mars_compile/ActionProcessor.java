package winning.com.mars_compile;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import winning.com.mars_annotation.Action;

@AutoService(Processor.class)
public class ActionProcessor extends AbstractProcessor {
    private Messager mMessager;
    private Elements mElementUtils;
    private Filer filer;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
        mMessager.printMessage(Diagnostic.Kind.NOTE, "init...");
        filer = processingEnv.getFiler();
    }
    @Override public Set<String> getSupportedAnnotationTypes() {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "annotation...");
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(Action.class.getCanonicalName());
        return supportTypes;
    }


    @Override public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "processing...");
        initActionDataClass(roundEnvironment);
        return true;
    }

    public void initActionDataClass(RoundEnvironment roundEnvironment){
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Action.class);
        MethodSpec.Builder builder = MethodSpec.methodBuilder("init")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(void.class);

        for (Element element : elements) {
            if (element.getKind() == ElementKind.CLASS) {
                Action action = element.getAnnotation(Action.class);
                ClassName actionClassName = ClassName.get((TypeElement) element);
                builder.addCode("if( map.get($S) == null ){\n", action.value())
                        .addCode("map.put($S,new $T());\n}\n", action.value(),actionClassName);
            }
        }
        MethodSpec add = builder.build();
        FieldSpec map = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), ClassName.get(Object.class)),
                "map", Modifier.PUBLIC, Modifier.STATIC)
                .initializer("new $T()", HashMap.class)
                .build();
        TypeSpec ActionData = TypeSpec.classBuilder("ActionData")
                .addModifiers(Modifier.PUBLIC)
                .addField(map)
                .addMethod(add)
                .build();
        JavaFile javaFile = JavaFile.builder("com.winning.mars_security.core", ActionData)
                .build();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
