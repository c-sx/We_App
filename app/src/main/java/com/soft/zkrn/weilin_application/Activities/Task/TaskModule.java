package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.soft.zkrn.weilin_application.Activities.Login.LoginActivity;
import com.soft.zkrn.weilin_application.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class TaskModule extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout button_publish;
    private LinearLayout button_receive;
    private TextView button_center;
    private TextView button_login_test;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            /*            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }*/
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_module);

//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_task_module);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        button_publish = findViewById(R.id.btn_task_publish);
        button_publish.setOnClickListener(this);
        button_receive = findViewById(R.id.btn_task_receive);
        button_receive.setOnClickListener(this);
        button_center = findViewById(R.id.btn_task_center);
        button_center.setOnClickListener(this);
        button_login_test = findViewById(R.id.btn_login_test);
        button_login_test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_task_publish:
                Intent intent1 = new Intent(TaskModule.this, TaskCategory.class);
                startActivity(intent1);
                break;
            case R.id.btn_task_receive:
                Intent intent2 = new Intent(TaskModule.this, TaskReceive.class);
                startActivity(intent2);
                break;
            case R.id.btn_task_center:
                Intent intent3 = new Intent(TaskModule.this, TaskCenter.class);
                startActivity(intent3);
                break;
            case R.id.btn_login_test:
                Intent intent4 = new Intent(TaskModule.this, LoginActivity.class);
                startActivity(intent4);
                break;
        }
    }

}
