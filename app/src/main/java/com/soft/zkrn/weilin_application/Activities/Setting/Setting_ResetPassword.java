package com.soft.zkrn.weilin_application.Activities.Setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.Activities.User.UserInformation_Mainpage;
import com.soft.zkrn.weilin_application.GsonClass.StateData;
import com.soft.zkrn.weilin_application.GsonClass.UserInformationData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Put;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

public class Setting_ResetPassword extends AppCompatActivity {

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();

    private TextView tv_phone;
    private Button bt_confirm;
    private EditText et_new;
    private EditText et_repeat;
    private int userId;
    private String userPhone;
    private String new_pw;
    private String repeat_pw;

    private final String url_change = "http://119.23.190.83:8080/zhaqsq/user/{uid}";

    private static final int SUCCESS = 0;
    private static final int FAIL = 1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    Toast.makeText(Setting_ResetPassword.this,"修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case FAIL:
                    Toast.makeText(Setting_ResetPassword.this, "密码修改失败，请检查网络状况", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_reset_password);

        tv_phone = findViewById(R.id.tv_phone);
        bt_confirm = findViewById(R.id.bt_change);
        et_new = findViewById(R.id.et_new_password);
        et_repeat = findViewById(R.id.et_repeat_password);

        userId = readPsw("userID");
        Intent intent = getIntent();
        userPhone = intent.getStringExtra("userPhone");

        String display_number = "";
        int i;
        for(i = 0; i < 3; i++){
            display_number = display_number + userPhone.charAt(i);
        }
        for(i = 3; i < userPhone.length() - 4; i++){
            display_number = display_number + "*";
        }
        for(i = userPhone.length() - 4; i < userPhone.length(); i++){
            display_number = display_number + userPhone.charAt(i);
        }
        tv_phone.setText(display_number);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar8);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pw = et_new.getText().toString().trim();
                repeat_pw = et_repeat.getText().toString().trim();
                checkPW();
            }
        });
    }

    private int readPsw(String parm){
        SharedPreferences userSettings = (SharedPreferences) getSharedPreferences("setting",MODE_PRIVATE);
        return userSettings.getInt(parm,0);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkPW(){
        if(new_pw.length() == 0){
            Toast.makeText(Setting_ResetPassword.this,"请输入新密码",Toast.LENGTH_SHORT).show();
        }else{
            if(repeat_pw.length() == 0){
                Toast.makeText(Setting_ResetPassword.this,"请重复新密码",Toast.LENGTH_SHORT).show();
            }else{
                if(new_pw.length() < 8 || new_pw.length() > 20){
                    Toast.makeText(Setting_ResetPassword.this,"密码格式有误",Toast.LENGTH_SHORT).show();
                }else{
                    if(new_pw.equals(repeat_pw) == true){
                        callForChangePW();
                    }else{
                        Toast.makeText(Setting_ResetPassword.this,"新密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void callForChangePW(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("uid" , String.valueOf(userId));
        paramsMap.put("userPassword", new_pw);
        Message msg = Message.obtain();
        httpUtil.PUT(Setting_ResetPassword.this,url_change, paramsMap, new CallBack_Put() {
            @Override
            public void onFinish(String response) {
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        StateData data = (StateData) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS;
                        }else{
                            msg.what = FAIL;
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = FAIL;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAIL;
                handler.sendMessage(msg);
            }
        });
    }


}
