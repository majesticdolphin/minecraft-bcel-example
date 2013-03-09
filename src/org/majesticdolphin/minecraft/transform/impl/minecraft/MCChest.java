package org.majesticdolphin.minecraft.transform.impl.minecraft;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import org.majesticdolphin.minecraft.transform.Transformer;

import java.io.File;
import java.io.IOException;

public class MCChest extends Transformer {

    @Override
    public ClassGen transform(ClassGen classGen) {
        for(Method method : classGen.getMethods()) {
            if(method.getName().equals("a") && method.getAccessFlags() == Constants.ACC_PUBLIC) {
                classGen = injectBlockESP("CHEST_ESP", classGen, method, 0.5F, 0.4F, 1.0F);
                try {
                    classGen.getJavaClass().dump(new File(classGen.getClassName()+".class"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return classGen;
    }

    @Override
    public String getDesiredClass() {
        return "bdo";
    }

}
