package org.majesticdolphin.minecraft.loader;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.*;
import org.majesticdolphin.minecraft.transform.TransformHandler;
import org.majesticdolphin.minecraft.transform.Transformer;
import sun.misc.URLClassPath;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class MinecraftClassLoader extends ClassLoader {

    private JarFile jar;
    private JarURLConnection con;
    private URLClassPath ucp;
    private TransformHandler transformHandler;
    private HashMap<String, Class<?>> classes;

    public MinecraftClassLoader(URL url) {
        try {
            this.con = (JarURLConnection) url.openConnection();
            this.jar = con.getJarFile();
            this.ucp = new URLClassPath(new URL[]{url});
            this.transformHandler = new TransformHandler();
            this.transformHandler.load();
            this.classes = new HashMap<String, Class<?>>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Class<?> loadClass(String name) {
        Class c = null;
        try {
            c = super.findSystemClass(name);
        } catch (ClassNotFoundException e) {
            try {
                ZipEntry zip = jar.getEntry(name.replace(".", "/") + ".class");
                JavaClass clazz = new ClassParser(jar.getInputStream(zip), name).parse();
                ClassGen generator = new ClassGen(clazz);
                Transformer transformer = this.transformHandler.getTransformer(generator.getClassName());
                if(transformer != null)
                    generator = transformer.transform(generator);

                byte[] buffer = generator.getJavaClass().getBytes();
                c = defineClass(name, buffer, 0 , buffer.length);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (NullPointerException e1) {
                System.out.println("Null: " + name);
            }
        }
        if(c == null) {
            try {
                return super.loadClass(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        this.classes.put(name, c);

        return c;
    }

    public HashMap<String, Class<?>> getClasses() {
        return this.classes;
    }

    public URL findResource(final String name) {
        return ucp.findResource(name, true);
    }

}
