package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;

public class TaskAbandonBefore extends AppCompatActivity {
    private TextView tv_task_abandon_before_title;
    private TextView tv_task_abandon_before_content;
    private TextView tv_task_abandon_before_price;
    private Button btn_task_abandon_before_confirm;
    private Button btn_task_abandon_before_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_abandon_before);

        tv_task_abandon_before_title = findViewById(R.id.tv_task_abandon_before_title);
        tv_task_abandon_before_content = findViewById(R.id.tv_task_abandon_before_content);
        tv_task_abandon_before_price = findViewById(R.id.tv_task_abandon_before_price);
        btn_task_abandon_before_confirm = findViewById(R.id.btn_task_abandon_before_confirm);
        btn_task_abandon_before_cancel = findViewById(R.id.btn_task_abandon_before_cancel);

        btn_task_abandon_before_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskAbandonBefore.this, TaskAbandonBeforeSuccess.class);
                startActivity(intent);
            }
        });
        btn_task_abandon_before_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskAbandonBefore.this, TaskCenter.class);
                startActivity(intent);
            }
        });
    }
}
