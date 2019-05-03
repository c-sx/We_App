package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ReceiveSuccess extends AppCompatActivity {
    private Button btnList;
    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_success);

        btnList = (Button) findViewById(R.id.btn_receive_success_list);
        btnReturn = (Button) findViewById(R.id.btn_receive_success_return);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent close_intent = new Intent();
                close_intent.setAction("com.example.Close_TaskReceive");
                sendBroadcast(close_intent);

                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task_receive_success);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiveSuccess.this, TaskReceive.class);
                startActivity(intent);
            }
        });
    }
}
