package com.soft.zkrn.weilin_application.Activities.Setting;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Setting_FeedBack extends AppCompatActivity {


    private EditText et;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_feedback);

        bt = findViewById(R.id.bt_finish);
        et = findViewById(R.id.et_content);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et.getText().toString().isEmpty()){
                    Toast.makeText(Setting_FeedBack.this,"请填写描述",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Setting_FeedBack.this,"已提交，感谢您的反馈",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
