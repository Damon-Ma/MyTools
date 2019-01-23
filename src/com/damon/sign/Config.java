package com.damon.sign;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @ClassName Config
 * @Description 静态变量
 * @Author Damon
 * @Date 2018/12/7
 * @Version 1.0
 **/
public class Config {
    public static OkHttpClient.Builder builder;
    static OkHttpClient httpClient;
    public static CookieJar cookieJar;
    static ResourceBundle bundle;
    static String url;
    static String loginUri;
    static String getFileUri;
    static String signedDownloadUri;
    static String srcDownload;
    static String uploadUri;
    static String userName;
    static String password;
}
