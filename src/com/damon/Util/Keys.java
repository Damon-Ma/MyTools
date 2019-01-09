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
    NOSIGN_TO_SIGN(""),
    SIGN_TO_NOSIGN(""),
    SCREEN("screen"),
    INSTALL_CELL_APK("installCellAKP"),
    SIGN_LOGIN(""),
    GET_APK_LIST(""),
    UPLOAD_APK(""),
    DOWNLOAD_SRC_APK(""),
    DOWNLOAD_SIGNED_APK(""),
    FASTBOOT("");

    private final String name;

    private Keys(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
