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


public class TaskReceiveDetails extends AppCompatActivity {
    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_time;
    private TextView tv_type;
    private TextView tv_integral;
    private Button btn_receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_receive_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task_receive_details);
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

        /**
         * findId
         */
        tv_title = findViewById(R.id.tv_task_receive_details_title);
        tv_time = findViewById(R.id.tv_task_receive_details_time);
        tv_type = findViewById(R.id.tv_task_receive_details_type);
        tv_content = findViewById(R.id.tv_task_receive_details_content);
        tv_integral = findViewById(R.id.tv_task_receive_details_integral);
        btn_receive = findViewById(R.id.btn_task_receive_details);

        /**
         * button listener
         */
        btn_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskReceiveDetails.this, ReceiveSuccess.class);
                startActivity(intent);
            }
        });
    }

}
