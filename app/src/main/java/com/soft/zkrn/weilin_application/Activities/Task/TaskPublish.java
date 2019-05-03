package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.soft.zkrn.weilin_application.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TaskPublish extends AppCompatActivity {
    private String title;
    private String content;

    private Button button;
    private EditText et_title;
    private EditText et_content;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return true;
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
            if(action.equals("com.example.Close_TaskPublish"))
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


    private List<String> getData_endTime_year() {
        List<String> list = new ArrayList<>();
        list.add("5百米以内");
        list.add("5百米至2千米");
        list.add("2千米至1万米");
        list.add("其他");
        return list;
    }

    private List<String> getData_endTime_month() {
        List<String> list = new ArrayList<>();
        list.add("5百米以内");
        list.add("5百米至2千米");
        list.add("2千米至1万米");
        list.add("其他");
        return list;
    }

    private List<String> getData_endTime_day() {
        List<String> list = new ArrayList<>();
        list.add("5百米以内");
        list.add("5百米至2千米");
        list.add("2千米至1万米");
        list.add("其他");
        return list;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_publish);

        //关闭社区页面广播
        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_TaskPublish");
        registerReceiver(close_receiver,close_intentFilter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task_publish);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        //请求获取焦点
        et_content.requestFocus();
        //清除焦点
        et_content.clearFocus();
        //改变默认的单行模式
        et_content.setSingleLine(false);
        //水平滚动设置为False
        et_content.setHorizontallyScrolling(false);


//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_task_publish);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        button = (Button) findViewById(R.id.btn_publish_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = et_title.getText().toString().trim();
                content = et_content.getText().toString().trim();
                if(TextUtils.isEmpty(title)){
                    Toast.makeText(TaskPublish.this,"请输入标题",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(content)){
                    Toast.makeText(TaskPublish.this,"请输入任务内容",Toast.LENGTH_SHORT).show();
//                }else if(){
//
//                }else if (){
//
//                }else{
//
                }
                Intent intent = new Intent(TaskPublish.this, PublishSuccess.class);
                startActivity(intent);
            }
        });
    }
}
