package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.soft.zkrn.weilin_application.R;

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
        Intent intent = new Intent(this, TaskPublish.class);

        switch (v.getId()) {
            case R.id.btn_category_1:
                intent.putExtra("type","receive");
                break;
            case R.id.btn_category_2:
                intent.putExtra("type","send");
                break;
            case R.id.btn_category_3:
                intent.putExtra("type","borrow");
                break;
            case R.id.btn_category_4:
                intent.putExtra("type","other");
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
