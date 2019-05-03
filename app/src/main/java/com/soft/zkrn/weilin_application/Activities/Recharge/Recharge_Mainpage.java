package com.soft.zkrn.weilin_application.Activities.Recharge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;

public class Recharge_Mainpage extends AppCompatActivity {

    private Button bt_recharge;
    private Button bt_withdraw;
    private TextView tv_detail;
    private TextView tv_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge__mainpage);

        bt_recharge = findViewById(R.id.bt_recharge);
        bt_withdraw = findViewById(R.id.bt_withdraw);

        tv_detail = findViewById(R.id.tv_recharge_detail);
        tv_number = findViewById(R.id.tv_recharge_number);

        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Recharge_Mainpage.this,Recharge_Detail.class));
            }
        });
    }
}
