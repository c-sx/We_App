package com.soft.zkrn.weilin_application.Activities.Setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.GsonClass.UserInformationData;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Setting_Safety extends AppCompatActivity {
    private String userPhonenumber;
    private String userName;

    private LinearLayout code;
    private TextView tv_userName;
    private TextView tv_userPhone;

    private static final int SUCCESS = 1;
    private static final int FAIL = 2;

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
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting_Safety.this,"绑定",Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(Setting_Safety.this,bangding.class);
//                startActivity(intent);
            }
        });
        tv_userName = findViewById(R.id.tv_name);
        tv_userPhone = findViewById(R.id.tv_number);

        userName = readPsw("userName");
        userPhonenumber = readPsw("userPhone");

        tv_userName.setText(userName);
        tv_userPhone.setText(userPhonenumber);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        code=(LinearLayout)findViewById(R.id.code);
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting_Safety.this,"修改密码",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Setting_Safety.this, Setting_ResetPassword.class);
                startActivity(intent);
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
}
