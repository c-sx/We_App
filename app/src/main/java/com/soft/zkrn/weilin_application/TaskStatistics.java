package com.soft.zkrn.weilin_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TaskStatistics extends AppCompatActivity {
    static Button btn_list;
    static Button btn_publish_list;
    static Button btn_receive_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_statistics);

        /**
         * button onclick
         */
        btn_list = findViewById(R.id.btn_task_statistics_list);
        btn_publish_list = findViewById(R.id.btn_task_statistics_publish);
        btn_receive_list = findViewById(R.id.btn_task_statistics_receive);
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskStatistics.this, TaskStatistics.class);
                startActivity(intent);
            }
        });
        btn_publish_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskStatistics.this, TaskStatistics.class);
                startActivity(intent);
            }
        });
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskStatistics.this, TaskStatistics.class);
                startActivity(intent);
            }
        });

/*        BackToolbar backToolbar = null;
        backToolbar.setBackToolbar(R.id.toolbar_task_statistics);*/
        /**
         * toolbar component
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task_statistics);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        /**
         * navigation finish
         */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
