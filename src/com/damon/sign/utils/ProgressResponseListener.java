package com.damon.sign.utils;

/**
 * 响应体进度回调接口，比如用于文件下载中
 * 参考:https://blog.csdn.net/rebirth_love/article/details/71791024
 * 
 */
public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}