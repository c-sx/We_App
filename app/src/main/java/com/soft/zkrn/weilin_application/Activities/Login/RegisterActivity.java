package com.soft.zkrn.weilin_application.Activities.Login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.GsonClass.RegisterData;
import com.soft.zkrn.weilin_application.GsonClass.StateData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Post;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import java.util.HashMap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegisterActivity extends AppCompatActivity {

    private GsonUtil gson = new GsonUtil();
    private HttpUtil httpUtil = new HttpUtil();

    private EditText et_user;
    private EditText et_tel;
    private EditText et_verCode;
    private EditText et_setPassword;
    private EditText et_conPassword;
    private Button btn_confirm;

    private String setName;
    private String setTel;
    private String setWord;

    private static final int SUCCESS = 1;
    private static final int FAIL = 2;
    private static final int ERROR = 3;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获取数据 //
            switch (msg.what){
                case SUCCESS:
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case FAIL:
                    Toast.makeText(RegisterActivity.this,"注册失败", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    Toast.makeText(RegisterActivity.this,"网络状态异常", Toast.LENGTH_SHORT).show();
                    break;
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
        setContentView(R.layout.activity_register);

        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_Community");
        registerReceiver(close_receiver,close_intentFilter);

        /**
         * set Toolbar
         */
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_register);
//        setSupportActionBar(toolbar);
//
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeButtonEnabled(true);
//        }
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        /**
         * set findId
         */
        et_user = findViewById(R.id.et_reg_user);
        et_tel = findViewById(R.id.et_reg_tel);
//        et_verCode = findViewById(R.id.et_reg_verCode);
        et_setPassword = findViewById(R.id.et_reg_set_password);
        et_conPassword = findViewById(R.id.et_reg_confirm_password);
        btn_confirm = findViewById(R.id.btn_reg_confirm);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setName = et_user.getText().toString().trim();
                setTel = et_tel.getText().toString().trim();
                setWord = et_setPassword.getText().toString().trim();
                String conWord = et_conPassword.getText().toString().trim();
                if(TextUtils.isEmpty(setWord)){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(setName)){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else if(!setWord.equals(conWord)){
                    Toast.makeText(RegisterActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(setTel)){
                    Toast.makeText(RegisterActivity.this,"请输入联系方式",Toast.LENGTH_SHORT).show();
                }else{
                    register();
                }
            }
        });
    }

    private void register(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userName",setName);
        paramsMap.put("userPassword",setWord);
        paramsMap.put("userPhonenumber",setTel);
        httpUtil.POST("http://www.xinxianquan.xyz:8080/zhaqsq/user/register", paramsMap, new CallBack_Post() {
            @Override
            public void onFinish(String response) {
                gson.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        Message msg = Message.obtain();
                        StateData data = (StateData)obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS;
                            msg.obj = data;
                            handler.sendMessage(msg);
                        }else{
                            msg.what = FAIL;
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        Message msg = Message.obtain();
                        msg.what = ERROR;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Message msg = Message.obtain();
                msg.what = ERROR;
                handler.sendMessage(msg);
            }
        });
    }


}
