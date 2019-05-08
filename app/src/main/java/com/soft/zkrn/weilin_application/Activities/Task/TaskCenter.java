package com.soft.zkrn.weilin_application.Activities.Task;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soft.zkrn.weilin_application.Widget.FragmentSwitchTool;
import com.soft.zkrn.weilin_application.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class TaskCenter extends AppCompatActivity {

    private TextView tv_whole;
    private TextView tv_publish;
    private TextView tv_complete;
    private TextView tv_comment;

    private LinearLayout ll_whole;
    private LinearLayout ll_publish;
    private LinearLayout ll_complete;
    private LinearLayout ll_comment;

    private FragmentSwitchTool tool;
    private DrawerLayout mDrawerLayout;

//    private Task[] tasks = new Task[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_task_center);

        tv_whole = findViewById(R.id.tv_center_whole);
        tv_publish = findViewById(R.id.tv_center_publish);
        tv_comment = findViewById(R.id.tv_center_comment);
        tv_complete = findViewById(R.id.tv_center_complete);

        ll_whole = findViewById(R.id.ll_center_whole);
        ll_publish = findViewById(R.id.ll_center_publish);
        ll_complete = findViewById(R.id.ll_center_complete);
        ll_comment = findViewById(R.id.ll_center_comment);

        tool = new FragmentSwitchTool(getFragmentManager(), R.id.flContainer);
        tool.setClickableViews(ll_whole, ll_publish, ll_complete ,ll_comment);
        tool
                .addSelectedViews(new View[]{tv_whole})
                .addSelectedViews(new View[]{tv_publish})
                .addSelectedViews(new View[]{tv_complete})
                .addSelectedViews(new View[]{tv_comment});
        tool.setFragments(
                Task_CenterWhole.class,
                Task_CenterPublish.class,
                Task_CenterComplete.class,
                Task_CenterComment.class
        );
        tool.changeTag(ll_whole);

    }


}
