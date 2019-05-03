package com.soft.zkrn.weilin_application.Activities.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;

public class Homepage_Help extends AppCompatActivity {

    private Button bt_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        bt_cancel = findViewById(R.id.bt_help_cancel);

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage_Help.this, Homepage.class));
                finish();
            }
        });
    }
}
