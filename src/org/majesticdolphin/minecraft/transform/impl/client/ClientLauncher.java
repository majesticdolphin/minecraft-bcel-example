package org.majesticdolphin.minecraft.transform.impl.client;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.generic.ClassGen;
import org.majesticdolphin.minecraft.transform.Transformer;

public class ClientLauncher extends Transformer {

    public ClassGen transform(ClassGen classGen) {
        classGen.addInterface("org.majesticdolphin.minecraft.hook.MinecraftApplet");
        for(Field field : classGen.getFields()) {
            if(field.getSignature().equals("Ljava/applet/Applet;")) {
                return injectReturn(classGen, "getApplet", field.getName(), field.getType());
            }
        }
        return null;
    }

    public String getDesiredClass() {
        return "net.minecraft.Launcher";
    }


}
