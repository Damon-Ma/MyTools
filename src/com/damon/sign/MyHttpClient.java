package com.damon.sign;

import com.damon.Util.Log;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MyHttpClient
 * @Description TODO
 * @Author Damon
 * @Date 2018/12/7
 * @Version 1.0
 **/
public class MyHttpClient {
    public OkHttpClient.Builder mOkHttpClient(){

        //初始化日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new Log());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //初始化cookiejar
        final Map<String, List<Cookie>> cookieMap = new HashMap<String, List<Cookie>>();

        Config.cookieJar = new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                cookieMap.put(httpUrl.host(),list);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                if (cookieMap.get(httpUrl.host())==null){
                    return new ArrayList<Cookie>();
                }else {
                    return cookieMap.get(httpUrl.host());
                }
            }
        };


        //获取HttpClient.builder对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cookieJar(Config.cookieJar)
       //         .addNetworkInterceptor(loggingInterceptor)    //上传监控和日志拦截器冲突，屏蔽！
                .connectTimeout(1000,TimeUnit.SECONDS)
                .readTimeout(1000,TimeUnit.SECONDS)
                .writeTimeout(1000,TimeUnit.SECONDS);


        return builder;

    }
}
