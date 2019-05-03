package com.soft.zkrn.weilin_application.Activities.Community;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.soft.zkrn.weilin_application.Activities.Home.Homepage;
import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessfulCreation extends AppCompatActivity {

    private Button bt;

    /**
     * 接受关闭社区页面广播
     *
     */
    private BroadcastReceiver close_receiver =new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.example.Close_Community"))
                finish();
        }
    };

    /**
     * 注销广播
     *
     */
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(close_receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_creation);

        bt = findViewById(R.id.bt_confirm);

        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_Community");
        registerReceiver(close_receiver,close_intentFilter);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(SuccessfulCreation.this,"到此为止",Toast.LENGTH_SHORT).show();


                //广播退出社区页面
                Intent close_intent = new Intent();
                close_intent.setAction("com.example.Close_Community");
                sendBroadcast(close_intent);

                Intent intent = new Intent(SuccessfulCreation.this, Homepage.class);
                startActivity(intent);

//                finish();


            }
        });
    }
}
