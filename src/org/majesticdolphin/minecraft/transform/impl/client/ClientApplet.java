package org.majesticdolphin.minecraft.transform.impl.client;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.generic.*;
import org.majesticdolphin.minecraft.transform.Transformer;

public class ClientApplet extends Transformer {
    public ClassGen transform(ClassGen classGen) {
        classGen.addInterface("org.majesticdolphin.minecraft.hook.Minecraft");
        for(Field field : classGen.getFields()) {
            if(field.getSignature().equals("Lnet/minecraft/client/Minecraft;")) {
                return plainInjectReturn(classGen, "getMinecraft", field.getName(), field.getType());
            }
        }
        return null;
    }

    public String getDesiredClass() {
        return "net.minecraft.client.MinecraftApplet";
    }
}
