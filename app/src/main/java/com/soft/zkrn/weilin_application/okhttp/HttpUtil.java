package com.soft.zkrn.weilin_application.okhttp;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    private OkHttpClient client = new OkHttpClient();

    private String getJson;
    private String postJson;
    private String postResponse;

    public void GET(final String url, final String parmName, final String parmValue, final CallBack_Get callBack_get){

        new Thread() {
            public void run() {
                try {
                    Request request = new Request.Builder().url(url+"?"+parmName+"="+parmValue).build();
                    Response response = client.newCall(request).execute();
                    callBack_get.onFinish(response.body().string());
                } catch (IOException e) {
//                    e.printStackTrace();
                    callBack_get.onError(e);
                }
            }
        }.start();
    }

    public void GET(final String url,final CallBack_Get callback){

        new Thread() {
            public void run() {
                try {
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    callback.onFinish(response.body().string());
                } catch (IOException e) {
                    callback.onError(e);
                }
            }
        }.start();
    }

//    public void POST(final String url,final HashMap<String, String> paramsMap_1,final HashMap<String,Integer> paramsMap_2,final HashMap<String,Long> paramsMap_3,final CallBack_Post callBack_post){
//        FormBody body_1= getFormBody(paramsMap_1);
//        FormBody body_2= getFormBody(paramsMap_2);
//        FormBody body_3= getFormBody(paramsMap_3);
//
////        String s3 ="";
////        Log.d(s3,String.valueOf(body));
//        Request request=new Request.Builder().post(body).post().url(url).build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                callBack_post.onError(e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                callBack_post.onFinish(response.body().string());
//            }
//        });
//    }

    public void POST(final String url,final HashMap<String, String> paramsMap,final CallBack_Post callBack_post){
        FormBody body= getFormBody(paramsMap);
//        String s3 ="";
//        Log.d(s3,String.valueOf(body));
        Request request=new Request.Builder().post(body).url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack_post.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack_post.onFinish(response.body().string());
            }
        });
    }

    private FormBody getFormBody(HashMap<String, String> paramsMap) {

        FormBody.Builder formBody = new FormBody.Builder();
        if(paramsMap != null) {
            for (String key : paramsMap.keySet()) {
                formBody.add(key, paramsMap.get(key));
            }
        }
        return formBody.build();
    }

    public String getGetJson() {
        return getJson;
    }
}
