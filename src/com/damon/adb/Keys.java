package com.damon.adb;

public enum Keys {
    DEVICES("devices"),spdevices("spdevices"),kill_server("kill_server"),logcat("logcat"),install("install"),
    sideload("sideload"),recovery("recovery"),cleanLog("cleanLog"),send("send"),getpackage("getpackage"),
    getAPKPackageName("package"),getAPKActivity("activity"),getAPKName("apkName"),getAPKVersion("appVersion");

    private final String name;

    private Keys(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
