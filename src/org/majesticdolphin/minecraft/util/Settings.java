package org.majesticdolphin.minecraft.util;

public class Settings {
    private static Setting[] settings = {new Setting("CHEST_ESP", true), new Setting("PLAYER_ESP", true)};

    public static Setting get(String settingName) {
        for(int i = 0; i < settings.length; i++)
            if(settings[i].getName().equals(settingName))
                return settings[i];
        return null;
    }
}
