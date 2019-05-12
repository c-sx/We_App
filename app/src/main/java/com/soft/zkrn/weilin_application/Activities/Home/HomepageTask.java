package com.soft.zkrn.weilin_application.Activities.Home;

import androidx.fragment.app.Fragment;
//import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.soft.zkrn.weilin_application.Activities.Task.TaskCategory;
import com.soft.zkrn.weilin_application.Activities.Task.TaskCenter;
import com.soft.zkrn.weilin_application.Activities.Task.TaskReceive;
import com.soft.zkrn.weilin_application.Activities.Task.TaskStatistics;
import com.soft.zkrn.weilin_application.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HomepageTask extends Fragment {

    private LinearLayout button_publish;
    private LinearLayout button_receive;
    private LinearLayout button_center;
    private LinearLayout button_statistic;
//    private Button button_login_test;
//    private Button bt_center;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            /*            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }*/
            return true;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homepage_frag_task,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        bt_center = (Button) getActivity().findViewById(R.id.bt_HomepageTask_center);
        button_publish = getActivity().findViewById(R.id.btn_task_publish);
        button_receive = getActivity().findViewById(R.id.btn_task_receive);
        button_center = getActivity().findViewById(R.id.btn_task_center);
        button_statistic = getActivity().findViewById(R.id.btn_task_statistics);
//        button_login_test = getActivity().findViewById(R.id.btn_login_test);

        button_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), TaskCategory.class);
                startActivity(intent1);
            }
        });

        button_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), TaskReceive.class);
                startActivity(intent2);
            }
        });

        button_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getActivity(), TaskCenter.class);
                startActivity(intent3);
            }
        });

        button_statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getActivity(), TaskStatistics.class);
                startActivity(intent4);
            }
        });

//        button_login_test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent4 = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent4);
//            }
//        });

//        BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_task_module);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        bt_center.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(),"待补充", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
