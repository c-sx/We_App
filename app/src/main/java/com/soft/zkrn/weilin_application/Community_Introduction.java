package com.soft.zkrn.weilin_application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Community_Introduction extends AppCompatActivity {

    private Button bt_join;
    private TextView tv_type;
    private TextView tv_num;
    private TextView tv_name;

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
        setContentView(R.layout.activity_community_introduction);

        Intent intent = getIntent();
        int n = (int) intent.getIntExtra("extra_num",0);
        String s_type = intent.getStringExtra("extra_type");
        String s_num = String.valueOf(n);


        bt_join = (Button)findViewById(R.id.bt_communityintroduction);
        tv_type = (TextView)findViewById(R.id.communityIntroduction_type);
        tv_num = (TextView)findViewById(R.id.communityintroduction_num);

        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_Community");
        registerReceiver(close_receiver,close_intentFilter);

        tv_num.setText(s_num);
        tv_type.setText(s_type);

        bt_join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(Community_Introduction.this,"申请失败", Toast.LENGTH_SHORT).show();

                //页面跳转
                Intent intent = new Intent(Community_Introduction.this,Homepage.class);
                startActivity(intent);

                //广播退出社区页面
                Intent close_intent = new Intent();
                close_intent.setAction("com.example.Close_Community");
                sendBroadcast(close_intent);
//                finish();
            }
        });
    }
}
