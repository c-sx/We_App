package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;

public class PublishSuccess extends AppCompatActivity {

    private Button btnList;
    private Button btnReturn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_success);
        btnReturn = (Button) findViewById(R.id.btn_publish_success_return);



        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent close_intent = new Intent();
                close_intent.setAction("com.example.Close_TaskPublish");
                sendBroadcast(close_intent);

//                Intent intent = new Intent(PublishSuccess.this, TaskModule.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);

                finish();
            }
        });
    }
}
