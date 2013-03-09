package org.majesticdolphin.minecraft.transform;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransformHandler {

    private static Logger logger = Logger.getLogger(TransformHandler.class.getName());
    private HashMap<String, Transformer> transformers;

    public TransformHandler() {
        this.transformers = new HashMap<String, Transformer>();
    }

    public void load() {
        File[] files = {new File("out/production/MinecraftBCELBot/org/majesticdolphin/minecraft/transform/impl/client"),
                        new File("out/production/MinecraftBCELBot/org/majesticdolphin/minecraft/transform/impl/minecraft")};
        for(File folder : files) {
            if(!folder.exists()) {
                logger.log(Level.WARNING, "WARNING: COULD NOT FIND TRANSFORMATIONS -- TRANSFORMATIONS WILL NOT LOAD");
                return;
            }
            for (File file : folder.listFiles()) {
                try {
                    Class<?> transform = Class.forName("org.majesticdolphin.minecraft.transform.impl." + folder.getName()+ "." + file.getName().replace(".class", ""));
                    Transformer transformer = (Transformer)transform.newInstance();
                    this.transformers.put(transformer.getDesiredClass(), transformer);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Transformer getTransformer(String desiredClass) {
        return this.transformers.get(desiredClass);
    }

    public HashMap<String, Transformer> getTransformers() {
        return this.transformers;
    }
}
