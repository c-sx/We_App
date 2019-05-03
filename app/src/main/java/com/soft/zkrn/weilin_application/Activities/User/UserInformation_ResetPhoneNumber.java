package com.soft.zkrn.weilin_application.Activities.User;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;

public class UserInformation_ResetPhoneNumber extends AppCompatActivity {

    private Button bt_finish;
    private EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information__reset_phone_number);

        bt_finish = findViewById(R.id.bt_UserPhone_Finish);
        et_number = findViewById(R.id.et_UserPhone_Number);

        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = et_number.getText().toString().trim();

                if(phoneNumber.length() != 10){
                    Toast.makeText(UserInformation_ResetPhoneNumber.this,"号码错误", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UserInformation_ResetPhoneNumber.this,"修改完毕", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(UserInformation_ResetPhoneNumber.this, UserInformation_Mainpage.class);
//                    intent.putExtra("extra_number",phoneNumber);
//                    startActivity(intent);
                    finish();
                }


            }
        });
    }
}
