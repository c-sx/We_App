package com.soft.zkrn.weilin_application.Activities.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Setting_ChooseContact extends AppCompatActivity {
   private ImageView delete1;
    private ImageView delete2;
    private ImageView delete3;
    private Button finish2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_choose_contacts);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar14);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        delete1=(ImageView)findViewById(R.id.delete1);
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting_ChooseContact.this,"删除",Toast.LENGTH_SHORT).show();
                findViewById(R.id.information1).setVisibility(View.GONE);
            }
        });
        delete2=(ImageView)findViewById(R.id.delete2);
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting_ChooseContact.this,"删除",Toast.LENGTH_SHORT).show();
                findViewById(R.id.information2).setVisibility(View.GONE);
            }
        });
        delete3=(ImageView)findViewById(R.id.delete3);
        delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting_ChooseContact.this,"删除",Toast.LENGTH_SHORT).show();
                findViewById(R.id.information3).setVisibility(View.GONE);
            }
        });
        finish2=(Button)findViewById(R.id.finish2);
        finish2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting_ChooseContact.this, "返回一键求救界面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Setting_ChooseContact.this, Setting_SeekHelp.class);
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
