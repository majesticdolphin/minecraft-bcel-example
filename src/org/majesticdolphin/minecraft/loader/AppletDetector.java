package org.majesticdolphin.minecraft.loader;

import org.majesticdolphin.minecraft.MinecraftLauncher;
import org.majesticdolphin.minecraft.hook.MinecraftApplet;

import java.applet.Applet;
import java.lang.reflect.Field;
import java.util.logging.Logger;

public class AppletDetector implements Runnable {

    private Applet applet;
    private Field launcher;
    private Object obj;
    private MinecraftLauncher minecraftLauncher;
    private final static Logger logger = Logger.getLogger(AppletDetector.class.getName());

    public AppletDetector(MinecraftLauncher minecraftLauncher, Object obj, Field launcher) {
        this.obj = obj;
        this.launcher = launcher;
        this.minecraftLauncher = minecraftLauncher;
    }

    @Override
    public void run() {
        try {
            while(launcher.get(this.obj) == null) {
                Thread.sleep(1000);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(applet == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                MinecraftApplet minecraftApplet = (MinecraftApplet)launcher.get(this.obj);
                this.applet = minecraftApplet.getApplet();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        minecraftLauncher.hookApplet(this.applet);
    }
}
