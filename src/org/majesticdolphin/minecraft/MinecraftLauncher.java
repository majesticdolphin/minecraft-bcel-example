package org.majesticdolphin.minecraft;

import org.majesticdolphin.minecraft.loader.AppletDetector;
import org.majesticdolphin.minecraft.loader.MinecraftClassLoader;
import org.majesticdolphin.minecraft.util.Settings;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Emulates the minecraftlauncher, but allows hijacking.
 */
public class MinecraftLauncher implements ActionListener, ItemListener {

    private Frame frame;
    private AppletDetector appletDetector;
    private Applet applet;
    private static final Logger logger = Logger.getLogger(MinecraftLauncher.class.getName());
    private static MinecraftClassLoader minecraftClassLoader;
    private static Frame staticFrame;
    private CheckboxMenuItem chest, player;

    public MinecraftLauncher() {
        try {
            MinecraftLauncher.minecraftClassLoader = new MinecraftClassLoader(new URL("jar", "", "file:" + new File("data/minecraft.jar").getAbsolutePath() + "!/"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        initLauncher();
        gui();
        Thread thread = new Thread(this.appletDetector);
        thread.start();
    }

    public void initLauncher() {
        URL url;
        File jarFile = new File("data/minecraft_launcher.jar");
        try {
            url = new URL("jar", "", "file:" + jarFile.getAbsolutePath() + "!/");
            logger.log(Level.INFO, "Jar Loaded From: " + url.getFile());
            MinecraftClassLoader loader = new MinecraftClassLoader(url);
            Class minecraftFrame = loader.loadClass("net.minecraft.LauncherFrame");
            this.frame = (Frame)minecraftFrame.newInstance();
            MinecraftLauncher.staticFrame = this.frame;
            this.appletDetector = new AppletDetector(this, this.frame, this.frame.getClass().getDeclaredField("launcher"));
            logger.log(Level.INFO, "Attaching Launcher!");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void gui() {
        MenuBar menuBar = new MenuBar();

        Menu file = new Menu("File");
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(this);
        file.add(exit);

        Menu settings = new Menu("Settings");

        this.chest = new CheckboxMenuItem("Chest ESP");
        this.chest.setState(Settings.get("CHEST_ESP").getFlag());
        this.chest.addItemListener(this);
        settings.add(this.chest);

        this.player = new CheckboxMenuItem("Player ESP");
        this.player.setState(Settings.get("PLAYER_ESP").getFlag());
        this.player.addItemListener(this);
        settings.add(this.player);

        menuBar.add(file);
        menuBar.add(settings);

        this.frame.setMenuBar(menuBar);

        this.frame.setVisible(true);
    }

    public void hookApplet(Applet applet) {
        this.applet = applet;
        this.frame.setTitle("Minecraft Hack Client v0.0.1");
        logger.log(Level.INFO, "Applet initiated, hooking Applet now.");
    }

    public static Class<?> getClass(String name) {
        return minecraftClassLoader.getClasses().get(name);
    }

    public static Class<?> loadClass(String className) {
        return MinecraftLauncher.minecraftClassLoader.loadClass(className);
    }

    public static void main(String[] args) {
        new MinecraftLauncher();
    }

    public static int getWidth() {
        return MinecraftLauncher.staticFrame.getWidth();
    }

    public static int getHeight() {
        return MinecraftLauncher.staticFrame.getHeight();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource().equals(this.chest)) {
            Settings.get("CHEST_ESP").inverseFlag();
        } else if(e.getSource().equals(this.player)) {
            Settings.get("PLAYER_ESP").inverseFlag();
        }
    }
}
