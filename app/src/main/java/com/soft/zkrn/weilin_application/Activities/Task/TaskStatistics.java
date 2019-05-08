package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TaskStatistics extends AppCompatActivity {
    static TextView tv_list;
    static TextView tv_publish_list;
    static TextView tv_receive_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_statistics);

        /**
         * button onclick
         */
        tv_list = findViewById(R.id.tv_task_statistics_list);
        tv_publish_list = findViewById(R.id.tv_task_statistics_publish);
        tv_receive_list = findViewById(R.id.tv_task_statistics_receive);
        tv_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskStatistics.this, TaskStatistics.class);
                startActivity(intent);
            }
        });
        tv_publish_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskStatistics.this, StatisticsPublish.class);
                startActivity(intent);
            }
        });
        tv_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskStatistics.this, StatisticsReceive.class);
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
