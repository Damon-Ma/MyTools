package com.damon.Util;

public enum  Keys {

    DEVICES("devices"),
    SP_DEVICES("spdevices"),
    KILL_SERVER("kill_server"),
    LOGCAT("logcat"),
    INSTALL("install"),
    SIDELOAD("sideload"),
    RECOVERY("recovery"),
    CLEAN_LOG("cleanLog"),
    SEND("send"),
    PACKAGE("getpackage"),
    APN_PACKAGE_NAME("package"),
    APK_ACTIVITY_NAME("activity"),
    APK_NAME("apkName"),
    APK_VERSION("appVersion"),
    MAIN_ACTIVITY("toHome"),
    IS_SIDELOAD("isSideload"),
    DDMS("monitor"),
    SHELL("shell"),
    SCREEN("screen"),
    INSTALL_CELL_APK("installCellAKP");

    private final String name;

    private Keys(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
