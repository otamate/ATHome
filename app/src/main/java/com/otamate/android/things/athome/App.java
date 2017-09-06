/*
    A basic App launcher for Android Things by Carl Whalley

    This class holds the App information used in the RecyclerView
 */
package com.otamate.android.things.athome;

import android.graphics.drawable.Drawable;

class App {
    private String packageName;
    private String name;
    private Drawable icon;
    private boolean systemApp;

    String getPackageName() {
        return packageName;
    }

    void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    void setSystemApp(boolean systemApp) {
        this.systemApp = systemApp;
    }

    boolean isSystemApp() {
        return systemApp;
    }
}