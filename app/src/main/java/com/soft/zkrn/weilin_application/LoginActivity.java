package com.soft.zkrn.weilin_application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.soft.zkrn.weilin_application.okhttp.CallBackUtil;
import com.soft.zkrn.weilin_application.okhttp.OkhttpUtil;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;


public class LoginActivity extends AppCompatActivity{

    Gson gson = new Gson();

    private String userName, userPW;
    private Button btn_login;
    private EditText et_user;
    private EditText et_pw;
//    private TextView tv_log_reg;
//    private TextView tv_log_forget;


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
//        tv_log_reg = findViewById(R.id.btn_login_reg);
//        tv_log_forget = findViewById(R.id.btn_login_forget);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        userName = et_user.getText().toString().trim();
        userPW = et_pw.getText().toString().trim();

        String url = "http://www.xinxianquan.xyz:8080/zhaqsq/user/login";
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userName" ,userName);
        paramsMap.put("userPassword",userPW);
        OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
//                String s1 = "";
//                Log.d(s1,"post失败了");
            }

            @Override
            public void onResponse(String response) {
                processJson(response);
            }
        });
    }

    private void processJson(String res){
        Json_Login login_json = gson.fromJson(res,Json_Login.class);
        if(login_json.getCode() == 100){
            //需要另加新用户选项
            Intent intent_ToHomepage = new Intent(LoginActivity.this,Homepage.class);
            intent_ToHomepage.putExtra("userName",userName);
            intent_ToHomepage.putExtra("userPW",userPW);
//            intent_ToHomepage.putExtra("url","http://www.xinxianquan.xyz:8080/zhaqsq/user/login");
            startActivity(intent_ToHomepage);

        }else{
            Toast.makeText(LoginActivity.this,"登录失败", Toast.LENGTH_SHORT).show();
        }
    }
}
