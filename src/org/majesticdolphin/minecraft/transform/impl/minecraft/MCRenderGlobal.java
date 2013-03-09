package org.majesticdolphin.minecraft.transform.impl.minecraft;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;
import org.majesticdolphin.minecraft.transform.Transformer;

import java.io.File;
import java.io.IOException;

public class MCRenderGlobal extends Transformer {
    @Override



    public ClassGen transform(ClassGen classGen) {
        ConstantPoolGen constantPool = classGen.getConstantPool();
        for(Method method : classGen.getMethods()) {
            if(method.getName().equals("a") && method.getAccessFlags() == Constants.ACC_PRIVATE && method.getSignature().equals("(Laoe;)V")) {
                method.setAccessFlags(Constants.ACC_PUBLIC);
                MethodGen methodGen = new MethodGen(method, classGen.getClassName(), constantPool);
                methodGen.setName("drawBox");
                methodGen.setMaxStack();
                methodGen.setMaxLocals();
                methodGen.removeLineNumbers();
                classGen.addMethod(methodGen.getMethod());
            }
        }
        try {
            classGen.getJavaClass().dump(new File("bav.class"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classGen;
    }

    @Override
    public String getDesiredClass() {
        return "bav";
    }
}
