package com.soft.zkrn.weilin_application.Activities.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Setting_SeekHelp extends AppCompatActivity {
    private LinearLayout tianjia;
    private LinearLayout xuanze;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_seek_help);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar12);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tianjia=(LinearLayout) findViewById(R.id.tianjia);
        tianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting_SeekHelp.this, "添加紧急联系人", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Setting_SeekHelp.this, Setting_AddContact.class);
                startActivity(intent);
            }
        });
        xuanze=(LinearLayout) findViewById(R.id.xuanze);
       xuanze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting_SeekHelp.this, "选择紧急联系人", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Setting_SeekHelp.this, Setting_ChooseContact.class);
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
