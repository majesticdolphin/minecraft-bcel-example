package org.majesticdolphin.minecraft.util;

public class Setting {

    private String name;
    private boolean flag;

    public Setting(String name, boolean flag) {
        this.name = name;
        this.flag = flag;
    }

    public final void inverseFlag() {
        this.flag = !this.flag;
    }

    public final void setFlag(boolean flag) {
        this.flag = flag;
    }

    public final String getName() {
        return this.name;
    }

    public final boolean getFlag() {
        return this.flag;
    }
}
