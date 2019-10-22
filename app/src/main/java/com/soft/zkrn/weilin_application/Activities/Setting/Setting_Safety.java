package com.soft.zkrn.weilin_application.Activities.Setting;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.Activities.Home.Homepage;
import com.soft.zkrn.weilin_application.Activities.Login.LoginActivity;
import com.soft.zkrn.weilin_application.GsonClass.LoginData;
import com.soft.zkrn.weilin_application.GsonClass.UserInformationData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.Widget.PickerView;
import com.soft.zkrn.weilin_application.Widget.ScreenUtils;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Post;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Setting_Safety extends AppCompatActivity {
    private String userPhonenumber;
    private String userName;

    private LinearLayout code;
    private TextView tv_userName;
    private TextView tv_userPhone;

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();
    private String url = "http://119.23.190.83:8080/zhaqsq/user/login";

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
                    Toast.makeText(Setting_Safety.this,"验证成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Setting_Safety.this, Setting_ResetPassword.class);
                    intent.putExtra("userPhone",userPhonenumber);
                    startActivity(intent);
                    break;
                case FAIL:
                    Toast.makeText(Setting_Safety.this,"密码有误", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    Toast.makeText(Setting_Safety.this,"网络状态异常", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    private String readPsw(String userName){
        SharedPreferences userSettings = (SharedPreferences) getSharedPreferences("setting",MODE_PRIVATE);
        return userSettings.getString(userName,"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_safety);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_my_community);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.phone);
        tv_userName = findViewById(R.id.tv_name);
        tv_userPhone = findViewById(R.id.tv_number);

        userName = readPsw("userName");
        userPhonenumber = readPsw("userPhone");

        tv_userName.setText(userName);
        tv_userPhone.setText(userPhonenumber);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        code=(LinearLayout)findViewById(R.id.code);
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(){
        final View view = LayoutInflater.from(this).inflate(R.layout.setting_safety_dialog,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        Button btn_agree = view.findViewById(R.id.btn_agree);
        EditText password = view.findViewById(R.id.et_password);

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                check_password(String.valueOf(password.getText()));
            }
        });

        dialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)), LinearLayout.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setLayout((ScreenUtils.getScreenHeight(this)), LinearLayout.LayoutParams.MATCH_PARENT);
//        dialog.setCanceledOnTouchOutside(false);
    }

    private void check_password(String input){
        Message msg = Message.obtain();
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userPhonenumber" ,userPhonenumber);
        paramsMap.put("userPassword",input);
        httpUtil.POST_WITH_COOKIE(Setting_Safety.this,url, paramsMap, new CallBack_Post() {
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
}
