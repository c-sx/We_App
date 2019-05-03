package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmComplete extends AppCompatActivity {
    private Button btn_comfirm_complete_comment;
    private Button btn_comfirm_complete_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_complete);

        btn_comfirm_complete_comment = findViewById(R.id.btn_comfirm_complete_comment);
        btn_comfirm_complete_return = findViewById(R.id.btn_comfirm_complete_return);

        btn_comfirm_complete_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmComplete.this, TaskComment.class);
                startActivity(intent);
            }
        });
        btn_comfirm_complete_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
