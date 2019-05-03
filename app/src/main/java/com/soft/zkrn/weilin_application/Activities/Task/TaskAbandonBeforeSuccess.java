package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;

public class TaskAbandonBeforeSuccess extends AppCompatActivity {
    private Button btn_task_abandon_before_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_abandon_before_success);

        btn_task_abandon_before_success = findViewById(R.id.btn_task_abandon_before_success);
        btn_task_abandon_before_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskAbandonBeforeSuccess.this, TaskCenter.class);
                startActivity(intent);
            }
        });
    }
}
