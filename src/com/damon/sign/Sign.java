package com.damon.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.damon.JFrame.MyJProgressBar;
import com.damon.JFrame.MyLabel;
import com.damon.JFrame.MyTable;
import com.damon.Util.Log;
import com.damon.Util.Util;
import com.damon.sign.utils.ProgressHelper;
import com.damon.sign.utils.ProgressRequestListener;
import com.damon.sign.utils.ProgressResponseListener;
import okhttp3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @ClassName Sign
 * @Description TODO
 * @Author Damon
 * @Date 2018/12/8
 * @Version 1.0
 **/
public class Sign {
    public Sign(){
        Config.builder = new MyHttpClient().mOkHttpClient();
        Config.httpClient = Config.builder.build();
        Config.bundle = ResourceBundle.getBundle("interfaceMsg",Locale.CHINA);

        String signUrl = com.damon.Util.Config.signURL;
        if (signUrl==""){
            Config.url = Config.bundle.getString("url");
        }else {
            Config.url = signUrl;
        }
        Log.logger.info("签名地址："+Config.url);

        Config.getFileUri = Config.bundle.getString("getList.uri");
        Config.loginUri = Config.bundle.getString("login.uri");
        Config.signedDownloadUri = Config.bundle.getString("signedDownload.uri");
        Config.srcDownload = Config.bundle.getString("srcDownload.uri");
        Config.uploadUri = Config.bundle.getString("upload.uri");
        Config.userName = Config.bundle.getString("loginParam.userId");
        Config.password = Config.bundle.getString("loginParam.password");
    }


    //登陆，返回成功或者失败
    public Boolean login(){
        String testUrl = Config.url+Config.loginUri;
        FormBody body= new FormBody.Builder()
                .add("loginParam.userId",Config.userName)
                .add("loginParam.encode","")
                .add("loginParam.password",Config.password)
                .build();

        Request request = new Request.Builder()
                .url(testUrl)
                .post(body)
                .build();

        try {
            Log.logger.info("开始登陆...");
            Response response = Config.httpClient.newCall(request).execute();
            return response.body().string().contains("张雷");

        } catch (IOException e) {
            Log.logger.error(e);
            e.printStackTrace();
            MyLabel.uploadResult.setText("请求异常！");
            return false;
        }
    }

    //获取应用列表，返回json对象
    public JSONObject getFileList(){
        String testUrl = Config.url+Config.getFileUri;
        JSONObject result = null;
        FormBody body = new FormBody.Builder()
                .add("startUploadTime",getDate())
                .add("endUploadTime",getDate())
                .add("fileName","")
                .add("userName","undefined")
                .add("status","-1")
                .add("currPage","1")
                .add("pageSize","10")
                .add("sortName","uploadTime")
                .add("sortOrder","desc")
                .build();

        Request request = new Request.Builder()
                .url(testUrl)
                .post(body)
                .build();

        try {
            Log.logger.info("获取apk列表...");
            Response response = Config.httpClient.newCall(request).execute();
            String resultString = response.body().string();
            Log.logger.info("获取结果："+resultString);
            try {
                 result = JSON.parseObject(resultString);
            }catch (JSONException e){
                if (resultString.contains("用户名不能为空")){
                    result = new JSONObject();
                    result.put("result","登陆已过期！");
                }else {
                    MyLabel.uploadResult.setText("未知异常！");
                    Log.logger.error(resultString);
                }
            }
        } catch (IOException e) {
            Log.logger.error(e);
            e.printStackTrace();
            result = new JSONObject();
            result.put("result","获取失败！");
        }
        return result;

    }

    //上传，返回上传结果字符串
    public String upload(String signType, String filePath){
        String testUrl = Config.url+Config.uploadUri;
        File file = new File(filePath);

        final ProgressRequestListener progressRequestListener = new ProgressRequestListener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
//                Log.logger.info("bytesWrite:" + bytesWritten);
//                Log.logger.info("contentLength" + contentLength);
//                Log.logger.info("上传："+(100 * bytesWritten) / contentLength + " % done ");
//                Log.logger.info("done:" + done);
//                Log.logger.info("================================");
                MyJProgressBar.setUploadProgress((int)((100 * bytesWritten)/contentLength));
                if (done){
                    MyLabel.uploadResult.setText("正在签名...");
                }

            }
        };


        RequestBody fileBody = RequestBody.create(MediaType.parse("application/vnd.android.package-archive"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("certificate",signType)
                .addFormDataPart("upload",filePath,fileBody)
                .build();

        Request request = new Request.Builder()
                .url(testUrl)
                .post(ProgressHelper.addProgressRequestListener(requestBody,progressRequestListener))
                .build();

        try {
            Log.logger.info("开始上传...");
            MyLabel.uploadResult.setText("正在上传...");
            Response response = Config.httpClient.newCall(request).execute();

            String result = response.body().string();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Log.logger.error(e);
            return "签名失败";
        }

    }

    //下载
    public void signDownload(int id){
        Log.logger.info(Thread.currentThread().getId()+"开始下载："+id);
        //拼接URL
        String testUrl = Config.url+Config.signedDownloadUri;
        int downloadRowNumNow = com.damon.Util.Config.downloadRowNum;
        MyTable.signDTM.setValueAt("准备下载...",downloadRowNumNow,4);
        final ProgressResponseListener progressResponseListener = new ProgressResponseListener() {
            @Override
            public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
                MyTable.signDTM.setValueAt("已下载："+bytesRead/1024+"kb",
                        downloadRowNumNow,4);
            //    Log.logger.info(Thread.currentThread().getId()+"下载："+bytesRead/1024+"kb");
                if (done){
                    Log.logger.info(downloadRowNumNow+":下载完成！");
                    Log.logger.info(downloadRowNumNow+":文件大小："+bytesRead/1024+"kb");
                    MyTable.signDTM.setValueAt("下载完成！",downloadRowNumNow,4);

                }
            }
        };

        Request request = new Request.Builder()
                .url(testUrl+"?id="+id)
                .build();


        try {
            Response response = ProgressHelper.addProgressResponseListener(progressResponseListener).newCall(request).execute();

            try {
                String downloadFileName = response.header("Content-Disposition").split("=")[1];
                String path = getDownloadPath();
                Log.logger.info("下载路径:"+path);
                Log.logger.info("文件名称："+downloadFileName);

                InputStream is = response.body().byteStream();

                int len = 0;
                File file = new File(path,downloadFileName);
                byte[] buf = new byte[1024];
                FileOutputStream fos = new FileOutputStream(file);

                while ((len = is.read(buf)) != -1){
                    fos.write(buf,0,len);
                }
                fos.flush();
                fos.close();
                is.close();
            }catch (IndexOutOfBoundsException e){
                String result = response.body().string();
                if (result.contains("用户名不能为空")){
                    MyLabel.uploadResult.setText("登录过期！");
                }else {
                    MyLabel.uploadResult.setText("未知异常！");
                    Log.logger.error(result);
                }
            }

        } catch (IOException e) {
            Log.logger.error(e);
            MyLabel.uploadResult.setText("请求异常！");
            e.printStackTrace();

        }


    }



    //获取当前日期作为获取应用列表的参数
    private String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(System.currentTimeMillis());
    }

    //创建下载文件目录
    private String getDownloadPath(){
        String desktopPath = Util.getDesktopPath();
        String path = desktopPath + "\\签名文件\\";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }

}
