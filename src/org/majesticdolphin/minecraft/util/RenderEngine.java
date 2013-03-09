package org.majesticdolphin.minecraft.util;

import org.lwjgl.opengl.GL11;

public class RenderEngine {

    public static void drawString(int x, int y, String str) {

    }

    public static void drawBlockESP(String settingName, double doubleX, double doubleY, double doubleZ, float r, float b, float g) {
        if(Settings.get(settingName) != null) {
            if(Settings.get(settingName).getFlag() == false)
                return;
        }


        float x = (float)doubleX;
        float y = (float)doubleY;
        float z = (float)doubleZ;
        float x2 =  x + 1.0f;
        float y2 = y + 1.0f;
        float z2 = z + 1.0f;
        GL11.glBlendFunc(770, 771);
        GL11.glColor3f(r, b, g);
        GL11.glLineWidth(2F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        GL11.glBegin(3);
        GL11.glVertex3f(x, y ,z);
        GL11.glVertex3f(x2, y ,z);
        GL11.glVertex3f(x2, y ,z2);
        GL11.glVertex3f(x, y ,z2);
        GL11.glVertex3f(x, y ,z);
        GL11.glEnd();

        GL11.glBegin(3);
        GL11.glVertex3f(x, y2 ,z);
        GL11.glVertex3f(x2, y2 ,z);
        GL11.glVertex3f(x2, y2 ,z2);
        GL11.glVertex3f(x, y2 ,z2);
        GL11.glVertex3f(x, y2 ,z);
        GL11.glEnd();

        GL11.glBegin(1);
        GL11.glVertex3f(x, y ,z);
        GL11.glVertex3f(x, y2 ,z);
        GL11.glVertex3f(x2, y ,z);
        GL11.glVertex3f(x2, y2 ,z);
        GL11.glVertex3f(x2, y ,z2);
        GL11.glVertex3f(x2, y2 ,z2);
        GL11.glVertex3f(x, y ,z2);
        GL11.glVertex3f(x, y2 ,z2);
        GL11.glEnd();

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void drawEntityESP(String settingName, double doubleX, double doubleY, double doubleZ, float r, float b, float g) {
        if(Settings.get(settingName) != null) {
            if(Settings.get(settingName).getFlag() == false)
                return;
        }
        float x = (float)(doubleX - 0.5);
        float y = (float)(doubleY - 1.5);
        float z = (float)(doubleZ - 0.5);
        float x2 =  x + 1.0f;
        float y2 = y + 2.0f;
        float z2 = z + 1.0f;
        GL11.glBlendFunc(770, 771);
        GL11.glColor3f(r, b, g);
        GL11.glLineWidth(2F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        GL11.glBegin(3);
        GL11.glVertex3f(x, y ,z);
        GL11.glVertex3f(x2, y ,z);
        GL11.glVertex3f(x2, y ,z2);
        GL11.glVertex3f(x, y ,z2);
        GL11.glVertex3f(x, y ,z);
        GL11.glEnd();

        GL11.glBegin(3);
        GL11.glVertex3f(x, y2 ,z);
        GL11.glVertex3f(x2, y2 ,z);
        GL11.glVertex3f(x2, y2 ,z2);
        GL11.glVertex3f(x, y2 ,z2);
        GL11.glVertex3f(x, y2 ,z);
        GL11.glEnd();

        GL11.glBegin(1);
        GL11.glVertex3f(x, y ,z);
        GL11.glVertex3f(x, y2 ,z);
        GL11.glVertex3f(x2, y ,z);
        GL11.glVertex3f(x2, y2 ,z);
        GL11.glVertex3f(x2, y ,z2);
        GL11.glVertex3f(x2, y2 ,z2);
        GL11.glVertex3f(x, y ,z2);
        GL11.glVertex3f(x, y2 ,z2);
        GL11.glEnd();

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

        /* GL CUBE
        //BOTTOM
        GL11.glVertex3f(x, y ,z);
        GL11.glVertex3f(x2, y, z);
        GL11.glVertex3f(x, y ,z2);
        GL11.glVertex3f(x2, y ,z2);

        //FRONT
        GL11.glVertex3f(x, y ,z);
        GL11.glVertex3f(x2, y ,z);
        GL11.glVertex3f(x, y2 ,z);
        GL11.glVertex3f(x2, y2 ,z);

        //BACK
        GL11.glVertex3f(x, y ,z2);
        GL11.glVertex3f(x, y2 ,z2);
        GL11.glVertex3f(x2, y ,z2);
        GL11.glVertex3f(x2, y2 ,z2);

        //RIGHT
        GL11.glVertex3f(x2, y ,z);
        GL11.glVertex3f(x2, y2 ,z);
        GL11.glVertex3f(x2, y ,z2);
        GL11.glVertex3f(x2, y2 ,z2);

        //LEFT
        GL11.glVertex3f(x, y ,z);
        GL11.glVertex3f(x, y2 ,z);
        GL11.glVertex3f(x, y ,z2);
        GL11.glVertex3f(x, y2 ,z2);

        //TOP
        GL11.glVertex3f(x, y2 ,z);
        GL11.glVertex3f(x2, y2, z);
        GL11.glVertex3f(x, y2 ,z2);
        GL11.glVertex3f(x2, y2 ,z2);*/
}
