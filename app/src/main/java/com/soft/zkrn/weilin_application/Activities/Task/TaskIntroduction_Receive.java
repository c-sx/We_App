package com.soft.zkrn.weilin_application.Activities.Task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.GsonClass.TaskData_Certain;
import com.soft.zkrn.weilin_application.GsonClass.UserInformationData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Put;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

public class TaskIntroduction_Receive extends AppCompatActivity {

    private long time;
    private String desp;
    private int callId;
    private int money;
    private String title;
    private String state;

    private TextView tv_desp;
    private TextView tv_state;
    private TextView tv_title;
    private TextView tv_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_introduction__receive);

        Intent intent = getIntent();
        time = intent.getLongExtra("time",0);
        callId = intent.getIntExtra("callId",0);
        money = intent.getIntExtra("money",0);
        desp = intent.getStringExtra("desp");
        title = intent.getStringExtra("title");
        state = intent.getStringExtra("state");

        tv_desp = findViewById(R.id.tv_introduction_description);
        tv_money = findViewById(R.id.tv_introduction_money);
        tv_title = findViewById(R.id.tv_introduction_title);
        tv_state = findViewById(R.id.tv_introduction_state);

        tv_title.setText(title);
        tv_money.setText(String.valueOf(money));
        tv_desp.setText(desp);


        if(callId == 0){
            Toast.makeText(TaskIntroduction_Receive.this,"网络异常",Toast.LENGTH_SHORT).show();
        }else{
//        1.未接取，2.未完成，3，未评价，4.已评价
            switch (state){
                case "1":
                    tv_state.setText("等待被接收中");
                    break;
                case "2":
                    tv_state.setText("已被接收，等待完成中");
                    break;
                case "3":
                    tv_state.setText("已完成，等待评价中");
                    break;
                case "4":
                    tv_state.setText("已结束");
                    break;
                default:
                    break;
            }
        }


    }


}
