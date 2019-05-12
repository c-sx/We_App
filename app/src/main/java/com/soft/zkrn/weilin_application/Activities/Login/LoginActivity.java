package com.soft.zkrn.weilin_application.Activities.Login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.Activities.Home.Homepage;
import com.soft.zkrn.weilin_application.GsonClass.LoginData;
import com.soft.zkrn.weilin_application.GsonClass.UserInformationData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Post;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity{


    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();

    private String userPhonenumber, userPW,userName;
    private int uId;
    private Button btn_login;
    private EditText et_user;
    private EditText et_pw;
    private TextView tv_register;
//    private TextView tv_forgeter;

    private static final int SUCCESS = 1;
    private static final int FAIL = 2;
    private static final int ERROR = 3;
    private static final int SUCCESSID = 4;
    private static final int FAILID = 5;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获取数据 //
            switch (msg.what){
                case SUCCESS:
                    LoginData data = (LoginData) msg.obj;
//                    System.out.println("data:" + data.getCode() + " + " + data.getExtend().getUser().getUserName());
                    userName = data.getExtend().getUser().getUserName();
                    System.out.println("userName:" + userName);
                    uId = data.getExtend().getUser().getUid();
                    SharedPreferences userSettings = getSharedPreferences("setting", MODE_PRIVATE);
                    SharedPreferences.Editor editors = userSettings.edit();
                    editors.putString("userName",userName)
                            .putString("userPW",userPW)
                            .putString("userPhone",userPhonenumber)
                            .putInt("userID",uId)
                            .putString("url","http://www.xinxianquan.xyz:8080/zhaqsq/user/login").commit();

                    Intent intent1 = new Intent(LoginActivity.this, Homepage.class);
                    intent1.putExtra("ifID",true);
                    intent1.putExtra("userID",uId);
                    startActivity(intent1);
                    finish();
//                    getId();
                    break;
                case FAIL:
                    Toast.makeText(LoginActivity.this,"账号信息有误", Toast.LENGTH_SHORT).show();
                    btn_login.setEnabled(true);
                    break;
                case ERROR:
                    Toast.makeText(LoginActivity.this,"网络状态异常", Toast.LENGTH_SHORT).show();
                    btn_login.setEnabled(true);
                    break;
//                case SUCCESSID:
//                    SharedPreferences userSetting = getSharedPreferences("setting", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = userSetting.edit();
//                    editor.putInt("userID",uId);
//                    editor.commit();
//                    Intent intent1 = new Intent(LoginActivity.this, Homepage.class);
//                    intent1.putExtra("ifID",true);
//                    intent1.putExtra("userID",uId);
//                    startActivity(intent1);
//                    finish();
//                    break;
//                case FAILID:
//                    Intent intent2 = new Intent(LoginActivity.this, Homepage.class);
//                    intent2.putExtra("ifID",false);
//                    startActivity(intent2);
//                    finish();
//                    Toast.makeText(LoginActivity.this,"网络状态异常", Toast.LENGTH_SHORT).show();
//                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 接受关闭社区页面广播
     *
     */
    private BroadcastReceiver close_receiver =new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.example.Close_Community"))
                finish();
        }
    };

    /**
     * 注销广播
     *
     */
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(close_receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_Community");
        registerReceiver(close_receiver,close_intentFilter);

        btn_login = findViewById(R.id.btn_login);
        et_user = findViewById(R.id.et_log_user);
        et_pw = findViewById(R.id.et_log_pw);
        tv_register = findViewById(R.id.btn_login_reg);
//        tv_log_forget = findViewById(R.id.btn_login_forget);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setEnabled(false);
                login();
            }
        });
    }

    private void login(){
        userPhonenumber = et_user.getText().toString().trim();
        userPW = et_pw.getText().toString().trim();

        Message msg = Message.obtain();

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userPhonenumber" ,userPhonenumber);
        paramsMap.put("userPassword",userPW);
        httpUtil.POST("http://www.xinxianquan.xyz:8080/zhaqsq/user/login", paramsMap, new CallBack_Post() {
            @Override
            public void onFinish(String response) {
                gsonUtil.translateJson(response, LoginData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        LoginData data = (LoginData)obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS;
                            msg.obj = obj;
                            handler.sendMessage(msg);
//                            System.out.println(response);
//                            startActivity(new Intent(LoginActivity.this, Homepage.class));
                        }else if(data.getCode() == 200){
                            msg.what = FAIL;
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = ERROR;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = ERROR;
                handler.sendMessage(msg);
            }
        });
    }

//    private void getId(){
//        httpUtil.GET("http://www.xinxianquan.xyz:8080/zhaqsq/user/get", "userName", userName, new CallBack_Get() {
//            @Override
//            public void onFinish(String response) {
//                gsonUtil.translateJson(response, UserInformationData.class, new CallBackGson() {
//                    @Override
//                    public void onSuccess(Object obj) {
//                        Message msg = Message.obtain();
//                        UserInformationData data = (UserInformationData) obj;
//                        if(data.getCode() == 100){
//                            uId = data.getExtend().getUser().getUid();
//                            msg.what = SUCCESSID;
//                        }else{
//                            msg.what = FAILID;
//                        }
//                        handler.sendMessage(msg);
//                    }
//
//                    @Override
//                    public void onFail(Exception e) {
//                        Message msg = Message.obtain();
//                        msg.what = FAILID;
//                        handler.sendMessage(msg);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Message msg = Message.obtain();
//                msg.what = FAILID;
//                handler.sendMessage(msg);
//            }
//        });
//    }

//    private void processJson(String res){
//        Json_Login login_json = gson.fromJson(res,Json_Login.class);
//        if(login_json.getCode() == 100){
//            //需要另加新用户选项
//            Intent intent_ToHomepage = new Intent(LoginActivity.this,Homepage.class);
//            intent_ToHomepage.putExtra("userName",userName);
//            intent_ToHomepage.putExtra("userPW",userPW);
////            intent_ToHomepage.putExtra("url","http://www.xinxianquan.xyz:8080/zhaqsq/user/login");
//            startActivity(intent_ToHomepage);
//
//        }else{
//            Toast.makeText(LoginActivity.this,"登录失败", Toast.LENGTH_SHORT).show();
//        }
//    }
}
