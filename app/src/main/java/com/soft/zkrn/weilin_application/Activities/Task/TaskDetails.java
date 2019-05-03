package com.soft.zkrn.weilin_application.Activities.Task;

import android.os.Bundle;
import android.widget.TextView;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TaskDetails extends AppCompatActivity {
    private TextView tv_task_details_content;
    private TextView tv_task_details_title;
    private TextView tv_task_details_time;
    private TextView tv_task_details_type;
    private TextView tv_task_details_integral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        tv_task_details_content = findViewById(R.id.tv_task_details_content);
        tv_task_details_title = findViewById(R.id.tv_task_details_title);
        tv_task_details_time = findViewById(R.id.tv_task_details_time);
        tv_task_details_type = findViewById(R.id.tv_task_details_type);
        tv_task_details_integral = findViewById(R.id.tv_task_details_integral);

        /**
         * Toolbar及ActionBar功能
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task_details);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
