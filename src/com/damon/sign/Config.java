package com.damon.sign;

import okhttp3.OkHttpClient;

import java.util.ResourceBundle;

/**
 * @ClassName Config
 * @Description TODO
 * @Author Damon
 * @Date 2018/12/7
 * @Version 1.0
 **/
public class Config {
    static OkHttpClient httpClient;
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
