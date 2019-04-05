package com.soft.zkrn.weilin_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Setting_Mainpage extends AppCompatActivity {

    private Button bt_currency;
    private Button bt_seekhelp;
    private Button bt_feedback;
    private Button bt_about;
    private Button bt_signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_mainpage);


        bt_currency = (Button)findViewById(R.id.tongyong);
        bt_seekhelp=(Button)findViewById(R.id.qiujiu);
        bt_feedback=(Button)findViewById(R.id.fankui);
        bt_about = (Button)findViewById(R.id.guanyu);
        bt_signout = findViewById(R.id.bt_SettingMainpage_signout);

        /*
        引用toolbar
         */
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Button button1=(Button)findViewById(R.id.zhanghu);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Settngs.this,"账户安全",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Setting_Mainpage.this, Setting_Safety.class);
                startActivity(intent);
            }
        });

        bt_signout.setOnClickListener(new View.OnClickListener() {
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

        bt_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Setting_Mainpage.this, "通用", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Setting_Mainpage.this, Setting_Currency.class);
                startActivity(intent);
            }
        });

        bt_seekhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Setting_Mainpage.this, "一键求救服务", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Setting_Mainpage.this, Setting_SeekHelp.class);
                startActivity(intent);
            }
        });

       bt_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Setting_Mainpage.this, "意见反馈", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Setting_Mainpage.this, Setting_FeedBack.class);
                startActivity(intent);
            }
        });

        bt_about.setOnClickListener(new View.OnClickListener() {
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
