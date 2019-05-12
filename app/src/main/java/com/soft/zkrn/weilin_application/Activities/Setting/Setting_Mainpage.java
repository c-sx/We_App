package com.soft.zkrn.weilin_application.Activities.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Setting_Mainpage extends AppCompatActivity {

    private TextView tv_safety;
    private TextView tv_currency;
    private TextView tv_seekhelp;
    private TextView tv_feedback;
    private TextView tv_about;
    private TextView tv_signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_mainpage);


        tv_currency = findViewById(R.id.tv_setting_main_currency);
        tv_seekhelp= findViewById(R.id.tv_setting_main_help);
        tv_feedback= findViewById(R.id.tv_setting_main_suggestion);
        tv_about = findViewById(R.id.tv_setting_main_about);
        tv_signout = findViewById(R.id.tv_setting_main_out);
        tv_safety= findViewById(R.id.tv_setting_main_safety);
        /*
        引用toolbar
         */
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_my_community);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        tv_safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Settngs.this,"账户安全",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Setting_Mainpage.this, Setting_Safety.class);
                startActivity(intent);
            }
        });

        tv_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Setting_Mainpage.this,"已退出账号，这里再补一个登录页面然后start", Toast.LENGTH_SHORT).show();
                ////之后补充一个登录页面的跳转////
                Intent intent = new Intent();
                intent.setAction("com.example.SignOut");
                sendBroadcast(intent);

                finish();
            }
        });

        tv_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Setting_Mainpage.this, "通用", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Setting_Mainpage.this, Setting_Currency.class);
                startActivity(intent);
            }
        });

        tv_seekhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Setting_Mainpage.this, "一键求救服务", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Setting_Mainpage.this, Setting_SeekHelp.class);
                startActivity(intent);
            }
        });

       tv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Setting_Mainpage.this, "意见反馈", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Setting_Mainpage.this, Setting_FeedBack.class);
                startActivity(intent);
            }
        });

        tv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Setting_Mainpage.this, "关于", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Setting_Mainpage.this, Setting_About.class);
                startActivity(intent);
            }
        });
    }

    //设置箭头的可点击
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
