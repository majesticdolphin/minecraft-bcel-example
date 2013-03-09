package org.majesticdolphin.minecraft.transform.impl.client;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;
import org.majesticdolphin.minecraft.transform.Transformer;

public class ClientGameUpdater extends Transformer {
    public ClassGen transform(ClassGen classGen) {
        try {
            for(Method method : classGen.getMethods()) {
                if(method.getName().equals("createApplet")) {
                    ConstantPoolGen cpg = classGen.getConstantPool();
                    MethodGen methodGen = new MethodGen(method, classGen.getClassName(), cpg);
                    Instruction instruction = null;
                    InstructionList instructionList = methodGen.getInstructionList();
                    InstructionHandle[] instructionHandles = instructionList.getInstructionHandles();
                    for(int i = 0; i < instructionHandles.length; i++) {
                        //System.out.println(instructionHandles[i].getInstruction());  //debug
                        if(instructionHandles[i].getInstruction() instanceof INVOKEVIRTUAL) {
                            instruction = instructionHandles[i].getInstruction();
                            break;
                        }
                    }

                    InstructionFactory instructionFactory = new InstructionFactory(classGen, cpg);
                    InvokeInstruction classLoaderCall =
                            instructionFactory.createInvoke(
                                    "org.majesticdolphin.minecraft.MinecraftLauncher", "loadClass", Type.CLASS, new Type[]{Type.STRING}, Constants.INVOKESTATIC);

                    instructionList.insert(instruction, classLoaderCall);
                    instructionList.delete(instruction);

                    methodGen.setInstructionList(instructionList);
                    instructionList.setPositions();
                    methodGen.setMaxStack();
                    methodGen.setMaxLocals();
                    methodGen.removeLineNumbers();
                    classGen.replaceMethod(method, methodGen.getMethod());
                    return classGen;
                }
            }
        } catch (TargetLostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDesiredClass() {
        return "net.minecraft.GameUpdater";
    }
}
