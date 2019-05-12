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

    public void PUT(final String url,final HashMap<String,String> paramsMap,final CallBack_Put callBack_put){
//        FormBody body = new FormBody.Builder();
        FormBody body = getFormBody(paramsMap);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack_put.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack_put.onFinish(response.body().string());
            }
        });
    }


//    public void PUT(final String url,final HashMap<String, String> paramsMap,final CallBack_Put callBack_put){
//        FormBody build = new FormBody.Builder()
//                .add("Rfid", Rfid)
//                .build();
//        String format = String.format(url,Rfid, 1, username, key, current_timestamp);
//        Request build1 = new Request.Builder()
//                .url(format)
//                .put(build)
//                .build();
//
//        client.newCall(build1).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String string = response.body().string();
//                if (string != null) {
//                    try {
//                        final JSONObject jsonObject = new JSONObject(string);
//                        int status = jsonObject.getInt("status");
//                        if (status == 0) {
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(Home_Thelibrary.this, "修改状态成功！", Toast.LENGTH_SHORT).show();
//                                    show();
//                                }
//                            });
//                        }else {
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(Home_Thelibrary.this, "修改状态失败，请稍后重试！", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } finally {
//                        progressDlgEx.closeHandleThread();
//                    }
//                }
//            }
//        });
//    }

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
