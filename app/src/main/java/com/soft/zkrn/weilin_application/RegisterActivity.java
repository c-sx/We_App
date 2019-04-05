package com.soft.zkrn.weilin_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_user;
    private EditText et_tel;
    private EditText et_verCode;
    private EditText et_setPassword;
    private EditText et_conPassword;
    private Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /**
         * set Toolbar
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_register);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * set findId
         */
        et_user = findViewById(R.id.et_reg_user);
        et_tel = findViewById(R.id.et_reg_tel);
        et_verCode = findViewById(R.id.et_reg_verCode);
        et_setPassword = findViewById(R.id.et_reg_set_password);
        et_conPassword = findViewById(R.id.et_reg_confirm_password);
        btn_confirm = findViewById(R.id.btn_reg_confirm);
    }
}
