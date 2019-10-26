package com.soft.zkrn.weilin_application.okhttp;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static okhttp3.internal.Util.EMPTY_REQUEST;

public class HttpUtil {

    private OkHttpClient client = new OkHttpClient();/*.newBuilder().cookieJar(new CookieJar() {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
//            return null;
        }
    }).build();*/
    private List<String> cookies;
    private String cookie = "";

    private String getJson;
    private String postJson;
    private String postResponse;
    public static final String FILE_NAME = "setting";


    //仅参数没参数名的GET方法
    public <T> void GET(final Context context,final String url,final T parmValue, final CallBack_Get callBack_get){
        SharedPreferences userSettings = context.getSharedPreferences("setting",MODE_PRIVATE);//(FILE_NAME, MODE_PRIVATE);
        int length = userSettings.getInt("cookies_length",0);
        for(int i=0;i<length;i++){
            cookie += userSettings.getString("cookies_"+i,"");
        }

        new Thread() {
            public void run() {
                try {
                    Request request = new Request.Builder().url((url + parmValue).replaceFirst("\"","")).addHeader("Cookie", String.valueOf(cookie)).build();
                    Response response = client.newCall(request).execute();
                    callBack_get.onFinish(response.body().string());
                } catch (IOException e) {
//                    e.printStackTrace();
                    callBack_get.onError(e);
                }
            }
        }.start();
    }


    //有一组参数的GET方法
    public void GET(final Context context,final String url, final String parmName, final String parmValue, final CallBack_Get callBack_get){
        SharedPreferences userSettings = context.getSharedPreferences("setting",MODE_PRIVATE);//(FILE_NAME, MODE_PRIVATE);
        int length = userSettings.getInt("cookies_length",0);
        for(int i=0;i<length;i++){
            cookie += userSettings.getString("cookies_"+i,"");
        }

        new Thread() {
            public void run() {
                try {
                    Request request = new Request.Builder().url(url+"?"+parmName+"="+parmValue).addHeader("Cookie", String.valueOf(cookie)).build();
                    Response response = client.newCall(request).execute();
                    callBack_get.onFinish(response.body().string());
                } catch (IOException e) {
//                    e.printStackTrace();
                    callBack_get.onError(e);
                }
            }
        }.start();
    }

    //有多组参数的GET方法
    public void GET(final Context context,final String url, final List<String> parmNames,final List<String> parmValues, final CallBack_Get callBack_get){
        SharedPreferences userSettings = context.getSharedPreferences("setting",MODE_PRIVATE);//(FILE_NAME, MODE_PRIVATE);
        int length = userSettings.getInt("cookies_length",0);
        for(int i=0;i<length;i++){
            cookie += userSettings.getString("cookies_"+i,"");
        }

        new Thread() {
            public void run() {
                try {
                    String s = url;
                    s = s + "?" + parmNames.get(0) + "=" + parmValues.get(0);
                    //////这里待补充
                    for(int i=1;i<parmNames.size();i++) {
                        s = s + "&" + parmNames.get(i) + "=" + parmValues.get(i);
                    }
                    Request request = new Request.Builder().url(s).addHeader("Cookie", String.valueOf(cookie)).build();
                    Response response = client.newCall(request).execute();
                    callBack_get.onFinish(response.body().string());
                } catch (IOException e) {
//                    e.printStackTrace();
                    callBack_get.onError(e);
                }
            }
        }.start();
    }

    //无参数的GET方法
    public void GET(final Context context,final String url, final CallBack_Get callBack_get){
        SharedPreferences userSettings = context.getSharedPreferences("setting",MODE_PRIVATE);//(FILE_NAME, MODE_PRIVATE);
        int length = userSettings.getInt("cookies_length",0);
        for(int i=0;i<length;i++){
            cookie += userSettings.getString("cookies_"+i,"");
        }

        new Thread() {
            public void run() {
                try {
                    Request request = new Request.Builder().url(url).addHeader("Cookie", String.valueOf(cookie)).build();
                    Response response = client.newCall(request).execute();
                    callBack_get.onFinish(response.body().string());
                } catch (IOException e) {
//                    e.printStackTrace();
                    callBack_get.onError(e);
                }
            }
        }.start();
    }


    public void POST(final Context context,final String url,final HashMap<String, String> paramsMap,final CallBack_Post callBack_post){
        SharedPreferences userSettings = context.getSharedPreferences("setting",MODE_PRIVATE);//(FILE_NAME, MODE_PRIVATE);
        int length = userSettings.getInt("cookies_length",0);
        for(int i=0;i<length;i++){
            cookie += userSettings.getString("cookies_"+i,"");
        }

        FormBody body= getFormBody(paramsMap);
        Request request=new Request.Builder().post(body).url(url).addHeader("Cookie", String.valueOf(cookie)).build();
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

    public void POST_WITH_COOKIE(final Context context,final String url,final HashMap<String, String> paramsMap,final CallBack_Post callBack_post){

        FormBody body= getFormBody(paramsMap);
//        String s3 ="";
//        Log.d(s3,String.valueOf(body));
        Request request=new Request.Builder().post(body).url(url).build();
        Call call = client.newCall(request);
//        cookies = request.header("Cookie");
//        System.out.println("cookie是"+cookies);
//        SharedPreferences userSettings = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editors = userSettings.edit();
//        editors.putString("cookie",cookies).commit();
//        for(int i=0;i<request.headers().size();i++){
//            cookies = request.;
//        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Log.d("LoginActidvity","OnError");
//                e.printStackTrace();
                callBack_post.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                cookies = response.headers("Set-Cookie");
                System.out.println("cookie="+cookies);
                SharedPreferences userSettings = context.getSharedPreferences("setting",MODE_PRIVATE);
                SharedPreferences.Editor editors = userSettings.edit();
                for(int i=0;i<cookies.size();i++){
                    editors.putString("cookies_"+i,cookies.get(i).replaceAll("path=/",""));
                }
                editors.putInt("cookies_length",cookies.size()).commit();

                SharedPreferences userSettingss = context.getSharedPreferences("setting",MODE_PRIVATE);
                int length = userSettingss.getInt("cookies_length",0);
                System.out.println("length="+length);
//                if(cookies.isEmpty() == false){
//                    cookies.clear();
//                }
                for(int i=0;i<length;i++){
                    System.out.println(userSettingss.getString("cookies_"+i,""));
                }
                callBack_post.onFinish(response.body().string());
            }
        });
    }

    public void PUT(final Context context,final String url,final HashMap<String,String> paramsMap,final CallBack_Put callBack_put){
        SharedPreferences userSettings = context.getSharedPreferences("setting",MODE_PRIVATE);//(FILE_NAME, MODE_PRIVATE);
        int length = userSettings.getInt("cookies_length",0);
        for(int i=0;i<length;i++){
            cookie += userSettings.getString("cookies_"+i,"");
        }

        FormBody body = getFormBody(paramsMap);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("Cookie", String.valueOf(cookie))
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

    public void DELETE(final Context context,final String url,final HashMap<String,String> paramsMap,final CallBack_Delete callBack_delete){
        SharedPreferences userSettings = context.getSharedPreferences("setting",MODE_PRIVATE);//(FILE_NAME, MODE_PRIVATE);
        int length = userSettings.getInt("cookies_length",0);
        for(int i=0;i<length;i++){
            cookie += userSettings.getString("cookies_"+i,"");
        }

        System.out.println("3");
        String s = url;
        boolean which = false;
        for (String key : paramsMap.keySet()) {
            System.out.println(key + "=" + paramsMap.get(key));
            if(which == false){
                s = s + "?" + key + "=" + paramsMap.get(key);
                which = true;
            }else{
                s = s + "&" + key + "=" + paramsMap.get(key);
            }
        }

//        FormBody body = getFormBody(paramsMap);
        Request request = new Request.Builder()
                .url(s)
                .delete()
                .addHeader("Cookie", String.valueOf(cookie))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack_delete.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack_delete.onFinish(response.body().string());
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

    public HttpUtil(){
        this.cookies = new ArrayList<String>();
    }
}
