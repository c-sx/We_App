package com.soft.zkrn.weilin_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TaskCategory extends AppCompatActivity implements View.OnClickListener {
    private Button button1, button2, button3, button4;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task_category);
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

//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_task_category);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //初始化View
        initView();
    }

    public void initView() {
        //find id
        button1 = (Button) findViewById(R.id.btn_category_1);
        button2 = (Button) findViewById(R.id.btn_category_2);
        button3 = (Button) findViewById(R.id.btn_category_3);
        button4 = (Button) findViewById(R.id.btn_category_4);
        //绑定监听器
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_category_1:
                Intent intent1 = new Intent(this, TaskPublish.class);
                startActivity(intent1);
                break;
            case R.id.btn_category_2:
                Intent intent2 = new Intent(this, TaskPublish.class);
                startActivity(intent2);
                break;
            case R.id.btn_category_3:
                Intent intent3 = new Intent(this, TaskPublish.class);
                startActivity(intent3);
                break;
            case R.id.btn_category_4:
                Intent intent4 = new Intent(this, TaskPublish.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }
}
