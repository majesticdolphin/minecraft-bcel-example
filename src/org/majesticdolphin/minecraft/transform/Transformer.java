package org.majesticdolphin.minecraft.transform;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

public abstract class Transformer {

    public abstract ClassGen transform(ClassGen classGen);

    public abstract String getDesiredClass();

    public String[] getManifest() {
        String[] manifest = {"MajesticDolphin", "0.0.1"};
        return manifest;
    }

    protected ClassGen injectReturn(ClassGen classGenerator, String methodName, String fieldName, Type fieldType) {
        //Get class's constants
        ConstantPoolGen constantPoolGen = classGenerator.getConstantPool();

        // Create new instruction list for injection
        InstructionList instructionList = new InstructionList();

        // Create new instruction factory to insert required methods/fields
        InstructionFactory instructionFactory = new InstructionFactory(classGenerator, constantPoolGen);

        // Get class name, needed misc.
        String className = classGenerator.getClassName();

        // Create a new method gen
        // new MethodGen(Public Function, Function Type, Function Argument types, Function arguments, Name of function (or method),
        // the class to insert, the instruction list, and the consants)
        MethodGen methodGen = new MethodGen(Constants.ACC_PUBLIC, fieldType, Type.NO_ARGS, new String[]{},  methodName, className, instructionList, constantPoolGen);

        //Load reference from local variable... maybe a reference point required for instruction list?
        instructionList.append(new ALOAD(0));

        // not sure
        instructionList.append(instructionFactory.createGetField(className, fieldName, fieldType));
        instructionList.append(instructionFactory.createReturn(fieldType));

        methodGen.setMaxLocals();
        methodGen.setMaxStack();
        classGenerator.addMethod(methodGen.getMethod());
        return classGenerator;
    }

    protected ClassGen plainInjectReturn(ClassGen classGenerator, String methodName, String fieldName, Type fieldType) {
        //Get class's constants
        ConstantPoolGen constantPoolGen = classGenerator.getConstantPool();

        // Create new instruction list for injection
        InstructionList instructionList = new InstructionList();

        // Create new instruction factory to insert required methods/fields
        InstructionFactory instructionFactory = new InstructionFactory(classGenerator, constantPoolGen);

        // Get class name, needed misc.
        String className = classGenerator.getClassName();

        // Create a new method gen
        // new MethodGen(Public Function, Function Type, Function Argument types, Function arguments, Name of function (or method),
        // the class to insert, the instruction list, and the consants)
        MethodGen methodGen = new MethodGen(Constants.ACC_PUBLIC, Type.OBJECT, Type.NO_ARGS, new String[]{},  methodName, className, instructionList, constantPoolGen);

        //Load reference from local variable... maybe a reference point required for instruction list?
        instructionList.append(new ALOAD(0));

        // not sure
        instructionList.append(instructionFactory.createGetField(className, fieldName, fieldType));
        instructionList.append(instructionFactory.createReturn(fieldType));

        methodGen.setMaxLocals();
        methodGen.setMaxStack();
        classGenerator.addMethod(methodGen.getMethod());
        return classGenerator;
    }

    protected ClassGen injectBlockESP(String setting, ClassGen classGen, Method method, float r, float g, float b) {
        ConstantPoolGen constantPool = classGen.getConstantPool();
        MethodGen methodGen = new MethodGen(method, classGen.getClassName(), constantPool);
        InstructionList instructionList = methodGen.getInstructionList();
        InstructionHandle[] instructionHandles = instructionList.getInstructionHandles();
        Instruction[] insertInstructions = {new DLOAD(2), new DLOAD(4), new DLOAD(6)};
        Instruction insertInstruction = null;
        InstructionFactory instructionFactory = new InstructionFactory(classGen, constantPool);
        for(int i = 0; i < instructionHandles.length; i++) {
            if(instructionHandles[i].getInstruction() instanceof ALOAD && insertInstruction == null) {
                insertInstruction = instructionHandles[i].getInstruction();
            }
        }
        instructionList.insert(insertInstruction, new LDC(constantPool.addString(setting)));
        for(Instruction instruction : insertInstructions) {
            instructionList.insert(insertInstruction, instruction);
        }
        instructionList.insert(insertInstruction, new LDC(constantPool.addFloat(r)));
        instructionList.insert(insertInstruction, new LDC(constantPool.addFloat(g)));
        instructionList.insert(insertInstruction, new LDC(constantPool.addFloat(b)));

        InvokeInstruction cleanup = instructionFactory.createInvoke(
                "org.majesticdolphin.minecraft.util.RenderEngine", "drawBlockESP", Type.VOID,
                new Type[]{Type.STRING, Type.DOUBLE, Type.DOUBLE, Type.DOUBLE, Type.FLOAT, Type.FLOAT, Type.FLOAT},
                Constants.INVOKESTATIC);
        instructionList.insert(insertInstruction, cleanup);
        methodGen.setInstructionList(instructionList);
        instructionList.setPositions();
        methodGen.setMaxStack();
        methodGen.setMaxLocals();
        methodGen.removeLineNumbers();
        classGen.replaceMethod(method, methodGen.getMethod());
        return classGen;
    }

    protected ClassGen injectEntityESP(String setting, ClassGen classGen, Method method, float r, float g, float b) {
        ConstantPoolGen constantPool = classGen.getConstantPool();
        MethodGen methodGen = new MethodGen(method, classGen.getClassName(), constantPool);
        InstructionList instructionList = methodGen.getInstructionList();
        InstructionHandle[] instructionHandles = instructionList.getInstructionHandles();
        Instruction[] insertInstructions = {new DLOAD(2), new DLOAD(4), new DLOAD(6)};
        Instruction insertInstruction = null;
        InstructionFactory instructionFactory = new InstructionFactory(classGen, constantPool);
        for(int i = 0; i < instructionHandles.length; i++) {
            if(instructionHandles[i].getInstruction() instanceof ALOAD && insertInstruction == null) {
                insertInstruction = instructionHandles[i].getInstruction();
            }
        }

        instructionList.insert(insertInstruction, new LDC(constantPool.addString(setting)));
        for(Instruction instruction : insertInstructions) {
            instructionList.insert(insertInstruction, instruction);
        }
        instructionList.insert(insertInstruction, new LDC(constantPool.addFloat(r)));
        instructionList.insert(insertInstruction, new LDC(constantPool.addFloat(g)));
        instructionList.insert(insertInstruction, new LDC(constantPool.addFloat(b)));

        InvokeInstruction cleanup = instructionFactory.createInvoke(
                "org.majesticdolphin.minecraft.util.RenderEngine", "drawEntityESP", Type.VOID,
                new Type[]{Type.STRING, Type.DOUBLE, Type.DOUBLE, Type.DOUBLE, Type.FLOAT, Type.FLOAT, Type.FLOAT},
                Constants.INVOKESTATIC);
        instructionList.insert(insertInstruction, cleanup);
        methodGen.setInstructionList(instructionList);
        instructionList.setPositions();
        methodGen.setMaxStack();
        methodGen.setMaxLocals();
        methodGen.removeLineNumbers();
        classGen.replaceMethod(method, methodGen.getMethod());
        return classGen;
    }

    /*protected ClassGen injectEntityESP(ClassGen classGen, Method method, float r, float g, float b) {
        ConstantPoolGen constantPool = classGen.getConstantPool();
        MethodGen methodGen = new MethodGen(method, classGen.getClassName(), constantPool);
        InstructionList instructionList = methodGen.getInstructionList();
        InstructionHandle[] instructionHandles = instructionList.getInstructionHandles();
        Instruction[] insertInstructions = {new DLOAD(2), new DLOAD(4), new DLOAD(6)};
        Instruction insertInstruction = null;
        InstructionFactory instructionFactory = new InstructionFactory(classGen, constantPool);
        for(int i = 0; i < instructionHandles.length; i++) {
            if(instructionHandles[i].getInstruction() instanceof ALOAD && insertInstruction == null) {
                insertInstruction = instructionHandles[i].getInstruction();
            }
        }
        instructionList.insert(insertInstruction, new LDC(constantPool.addFloat(r)));
        instructionList.insert(insertInstruction, new LDC(constantPool.addFloat(g)));
        instructionList.insert(insertInstruction, new LDC(constantPool.addFloat(b)));
        InvokeInstruction init = instructionFactory.createInvoke(
                "org.majesticdolphin.minecraft.util.RenderEngine", "init", Type.VOID, new Type[]{Type.FLOAT, Type.FLOAT, Type.FLOAT}, Constants.INVOKESTATIC);
        instructionList.insert(insertInstruction, init);
        instructionList.insert(insertInstruction, new INVOKESTATIC(constantPool.addMethodref(
                "net.minecraft.client.Minecraft",
                "x",
                "()Lnet/minecraft/client/Minecraft;")));
        instructionList.insert(insertInstruction, new GETFIELD(constantPool.addFieldref("net.minecraft.client.Minecraft", "f", "Lbav;")));
        instructionList.insert(insertInstruction, new NEW(constantPool.addClass("aoe")));
        instructionList.insert(insertInstruction, new DUP());

        Instruction[][] modifiers = {{new LDC2_W(constantPool.addDouble(0.5)), new DSUB()}, {new LDC2_W(constantPool.addDouble(1.5)), new DSUB()}, {new LDC2_W(constantPool.addDouble(0.5)), new DSUB()}};
        int i = 0;
        for(Instruction instruction : insertInstructions) {
            instructionList.insert(insertInstruction, instruction);
            instructionList.insert(insertInstruction, modifiers[i][0]);
            instructionList.insert(insertInstruction, modifiers[i][1]);
            i++;
        }

        Instruction[][] modifiersFinal = {{new LDC2_W(constantPool.addDouble(0.5)), new DADD()}, {new LDC2_W(constantPool.addDouble(0.5)), new DADD()}, {new LDC2_W(constantPool.addDouble(0.5)), new DADD()}};
        i = 0;
        for(Instruction instruction : insertInstructions) {
            instructionList.insert(insertInstruction, instruction);
            instructionList.insert(insertInstruction, modifiersFinal[i][0]);
            instructionList.insert(insertInstruction, modifiersFinal[i][1]);
            i++;
        }

        instructionList.insert(insertInstruction, new INVOKESPECIAL(constantPool.addMethodref("aoe", "<init>", "(DDDDDD)V")));
        instructionList.insert(insertInstruction, new INVOKEVIRTUAL(constantPool.addMethodref("bav", "drawBox", "(Laoe;)V")));

        InvokeInstruction cleanup = instructionFactory.createInvoke(
                "org.majesticdolphin.minecraft.util.RenderEngine", "cleanUp", Type.VOID, new Type[]{}, Constants.INVOKESTATIC);
        instructionList.insert(insertInstruction, cleanup);
        methodGen.setInstructionList(instructionList);
        instructionList.setPositions();
        methodGen.setMaxStack();
        methodGen.setMaxLocals();
        methodGen.removeLineNumbers();
        classGen.replaceMethod(method, methodGen.getMethod());
        return classGen;
    } */

        /*
    protected ClassGen injectBlockESP(ClassGen classGen, Method method, float r, float g, float b) {
        ConstantPoolGen constantPool = classGen.getConstantPool();
        MethodGen methodGen = new MethodGen(method, classGen.getClassName(), constantPool);
        InstructionList instructionList = methodGen.getInstructionList();
        InstructionHandle[] instructionHandles = instructionList.getInstructionHandles();
        Instruction[] insertInstructions = {new DLOAD(2), new DLOAD(4), new DLOAD(6)};
        Instruction insertInstruction = null;
        InstructionFactory instructionFactory = new InstructionFactory(classGen, constantPool);
        for(int i = 0; i < instructionHandles.length; i++) {
            if(instructionHandles[i].getInstruction() instanceof ALOAD && insertInstruction == null) {
                insertInstruction = instructionHandles[i].getInstruction();
            }
        }
        instructionList.insert(insertInstruction, new LDC(constantPool.addFloat(r)));
        instructionList.insert(insertInstruction, new LDC(constantPool.addFloat(g)));
        instructionList.insert(insertInstruction, new LDC(constantPool.addFloat(b)));
        InvokeInstruction init = instructionFactory.createInvoke(
                "org.majesticdolphin.minecraft.util.RenderEngine", "init", Type.VOID, new Type[]{Type.FLOAT, Type.FLOAT, Type.FLOAT}, Constants.INVOKESTATIC);
        instructionList.insert(insertInstruction, init);
        instructionList.insert(insertInstruction, new INVOKESTATIC(constantPool.addMethodref(
                "net.minecraft.client.Minecraft",
                "x",
                "()Lnet/minecraft/client/Minecraft;")));
        instructionList.insert(insertInstruction, new GETFIELD(constantPool.addFieldref("net.minecraft.client.Minecraft", "f", "Lbav;")));
        instructionList.insert(insertInstruction, new NEW(constantPool.addClass("aoe")));
        instructionList.insert(insertInstruction, new DUP());


        for(Instruction instruction : insertInstructions) {
            instructionList.insert(insertInstruction, instruction);
        }

        for(Instruction instruction : insertInstructions) {
            instructionList.insert(insertInstruction, instruction);
            instructionList.insert(insertInstruction, new DCONST(1.0));
            instructionList.insert(insertInstruction, new DADD());
        }

        instructionList.insert(insertInstruction, new INVOKESPECIAL(constantPool.addMethodref("aoe", "<init>", "(DDDDDD)V")));
        instructionList.insert(insertInstruction, new INVOKEVIRTUAL(constantPool.addMethodref("bav", "drawBox", "(Laoe;)V")));

        InvokeInstruction cleanup = instructionFactory.createInvoke(
                "org.majesticdolphin.minecraft.util.RenderEngine", "cleanUp", Type.VOID, new Type[]{}, Constants.INVOKESTATIC);
        instructionList.insert(insertInstruction, cleanup);
        methodGen.setInstructionList(instructionList);
        instructionList.setPositions();
        methodGen.setMaxStack();
        methodGen.setMaxLocals();
        methodGen.removeLineNumbers();
        classGen.replaceMethod(method, methodGen.getMethod());
        return classGen;
    } */

}
