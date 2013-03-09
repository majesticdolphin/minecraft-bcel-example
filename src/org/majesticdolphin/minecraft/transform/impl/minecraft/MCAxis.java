package org.majesticdolphin.minecraft.transform.impl.minecraft;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.majesticdolphin.minecraft.transform.Transformer;

public class MCAxis extends Transformer {
    @Override
    public ClassGen transform(ClassGen classGen) {
        for(Method method : classGen.getMethods()) {
            if(method.getName().equals("<init>")) {
                method.setAccessFlags(Constants.ACC_PUBLIC);
            }
        }

        return classGen;
    }

    @Override
    public String getDesiredClass() {
        return "aoe";
    }
}
