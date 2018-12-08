package com.damon.sign.utils;

/**
 * 请求体进度回调接口，比如用于文件上传中
 *
 * 参考:https://blog.csdn.net/rebirth_love/article/details/71791024
 */
public interface ProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}