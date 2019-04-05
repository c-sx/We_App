package com.soft.zkrn.weilin_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Setting_Safety extends AppCompatActivity {
    private LinearLayout code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_safety);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.phone);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting_Safety.this,"绑定",Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(Setting_Safety.this,bangding.class);
//                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        code=(LinearLayout)findViewById(R.id.code);
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting_Safety.this,"修改密码",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Setting_Safety.this, Setting_ResetPassword.class);
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
