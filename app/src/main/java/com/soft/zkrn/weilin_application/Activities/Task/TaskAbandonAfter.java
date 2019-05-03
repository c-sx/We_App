package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;

public class TaskAbandonAfter extends AppCompatActivity {
    private TextView tv_task_abandon_after_title;
    private TextView tv_task_abandon_after_content;
    private TextView tv_task_abandon_after_price;
    private Button btn_task_abandon_after_confirm;
    private Button btn_task_abandon_after_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_abandon_after);

        tv_task_abandon_after_title = findViewById(R.id.tv_task_abandon_after_title);
        tv_task_abandon_after_content = findViewById(R.id.tv_task_abandon_after_content);
        tv_task_abandon_after_price = findViewById(R.id.tv_task_abandon_after_price);
        btn_task_abandon_after_confirm = findViewById(R.id.btn_task_abandon_after_confirm);
        btn_task_abandon_after_cancel = findViewById(R.id.btn_task_abandon_after_cancel);

        btn_task_abandon_after_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskAbandonAfter.this, TaskAbandonAfterSuccess.class);
                startActivity(intent);
            }
        });
        btn_task_abandon_after_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskAbandonAfter.this, TaskCenter.class);
                startActivity(intent);
            }
        });
    }
}
