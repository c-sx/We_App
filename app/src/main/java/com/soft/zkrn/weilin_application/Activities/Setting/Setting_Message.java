package com.soft.zkrn.weilin_application.Activities.Setting;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.slideswitch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Setting_Message extends AppCompatActivity {
   slideswitch slideswitch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_message);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar11);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        slideswitch1=(slideswitch)findViewById(R.id.sli);
        slideswitch1.setOnStateChangedListener(new slideswitch.OnStateChangedListener() {
            @Override
            public void onStateChangedListener(boolean state) {
                if(true == state)
                {
                    Toast.makeText(Setting_Message.this, "开关打开", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Setting_Message.this, "开关关闭", Toast.LENGTH_SHORT).show();
                }

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
