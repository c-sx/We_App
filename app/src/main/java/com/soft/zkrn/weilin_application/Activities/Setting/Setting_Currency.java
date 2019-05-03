package com.soft.zkrn.weilin_application.Activities.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Setting_Currency extends AppCompatActivity {
      private LinearLayout jiemiansetting;
      private LinearLayout messagesetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_currency);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar9);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        jiemiansetting=(LinearLayout) findViewById(R.id.jiemiansetting);
      jiemiansetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Setting_Currency.this,"界面设置",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Setting_Currency.this, Setting_Interface.class);
                startActivity(intent);
            }
        });
        messagesetting=(LinearLayout) findViewById(R.id.messagesetting);
        messagesetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Setting_Currency.this,"消息设置",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Setting_Currency.this, Setting_Message.class);
                startActivity(intent);
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
