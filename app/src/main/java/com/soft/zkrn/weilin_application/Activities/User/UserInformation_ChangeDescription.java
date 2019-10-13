package com.soft.zkrn.weilin_application.Activities.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.soft.zkrn.weilin_application.R;
import androidx.appcompat.app.AppCompatActivity;

public class UserInformation_ChangeDescription extends AppCompatActivity {

    private Button bt_confirm;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information__change_description);

        editText = findViewById(R.id.et_content);
        bt_confirm = findViewById(R.id.bt_confirm);

        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_content();
            }
        });
    }

    private void get_content(){
        Intent data = new Intent();
        data.putExtra("content",editText.getText());
        setResult(10,data);
        finish();
    }

}
