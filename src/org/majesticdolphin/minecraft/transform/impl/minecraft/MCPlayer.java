package org.majesticdolphin.minecraft.transform.impl.minecraft;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import org.majesticdolphin.minecraft.transform.Transformer;

public class MCPlayer extends Transformer {

    @Override
    public ClassGen transform(ClassGen classGen) {
        for(Method method : classGen.getMethods()) {
            if(method.getName().equals("a") && method.getAccessFlags() == Constants.ACC_PUBLIC && method.getSignature().equals("(Lqx;DDDFF)V")) {
                classGen = injectEntityESP("PLAYER_ESP", classGen, method, 0F, 0.8F, 1.0F);
            }
        }
        return classGen;
    }

    @Override
    public String getDesiredClass() {
        return "bco";
    }

}

