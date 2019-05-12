package com.soft.zkrn.weilin_application.Activities.Home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.soft.zkrn.weilin_application.Activities.Login.LoginActivity;
import com.soft.zkrn.weilin_application.Widget.FragmentSwitchTool;
import com.soft.zkrn.weilin_application.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

//import android.widget.Button;



////////////////////////
//import android.widget.Button;
//import com.google.gson.Gson;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
///////////////////////

public class Homepage extends AppCompatActivity {

//    private LinearLayout ll_main, ll_task, ll_user;
//    private ImageView bt_main, bt_task, bt_user;
//    private TextView tv_main, tv_task, tv_user;
//    private FragmentSwitchTool tool;
//
//    private DrawerLayout mDrawerLayout;

    private SharedPreferences userSettings;
    /**
     * Fragment
     */
    private HomepageTask fragment_home_task;
    private HomepageMainpage fragment_home_main;
    private HomepageUser fragment_home_user;

    ////////////////////
//    private OkHttpClient client = new OkHttpClient();
//    public static final MediaType JSON             = MediaType.get("application/json; charset=utf-8");
//    private Button btn_json;
//    private Button btn_get;
//    private Button btn_post;
//    private TextView tv_tips1;
//    private TextView tv_json;
//    private TextView tv_tips2;
//    private TextView tv_gson;
//    private LinearLayout linearLayout1;
//    private LinearLayout linearLayout2;

    /**
     * BottomNavigation 底部菜单栏使用
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (fragment_home_main == null) {
                        fragment_home_main = new HomepageMainpage();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.ll_homepage, fragment_home_main).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_task:
                    if (fragment_home_task == null) {
                        fragment_home_task = new HomepageTask();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.ll_homepage, fragment_home_task).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_mine:
                    if (fragment_home_user == null) {
                        fragment_home_user = new HomepageUser();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.ll_homepage, fragment_home_user).commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };



//    /**
//     * 菜单
//     */
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch(item.getItemId()){
//            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                break;
//            default:
//        }
//        return true;
//    }

    /**
     * 接受退出广播
     *
     */
    private BroadcastReceiver receiver =new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.example.SignOut")){

                SharedPreferences.Editor editor = userSettings.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(Homepage.this,LoginActivity.class));
                finish();
            }

        }
    };

    /**
     * 接受关闭社区页面广播
     *
     */
    private BroadcastReceiver close_receiver =new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.example.Close_Community"))
                finish();
        }
    };

    /**
     * 注销广播
     *
     */
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(close_receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_homepage);

//        ll_main = (LinearLayout) findViewById(R.id.ll_main);
//        ll_task = (LinearLayout) findViewById(R.id.ll_task);
//        ll_user = (LinearLayout) findViewById(R.id.ll_user);
//
//        bt_main = (ImageView) findViewById(R.id.bt_main);
//        bt_task = (ImageView) findViewById(R.id.bt_task);
//        bt_user = (ImageView) findViewById(R.id.bt_user);
//
//        tv_main = (TextView) findViewById(R.id.tv_main);
//        tv_task = (TextView) findViewById(R.id.tv_task);
//        tv_user = (TextView) findViewById(R.id.tv_user);
//
//        tool = new FragmentSwitchTool(getFragmentManager(), R.id.flContainer);
//        tool.setClickableViews(ll_main, ll_task, ll_user);
//        tool.addSelectedViews(new View[]{bt_main, tv_main}).addSelectedViews(new View[]{bt_task, tv_task}).addSelectedViews(new View[]{bt_user, tv_user});
//        tool.setFragments(HomepageMainpage.class, HomepageTask.class, HomepageUser.class);
//        tool.changeTag(ll_main);


        /**
         * Fragment使用
         */

        //实例化Fragment_Home_Main
        fragment_home_main = new HomepageMainpage();
        //把Fragment添加到Activity中,复写不要忘了调用commmit
        getSupportFragmentManager().beginTransaction().add(R.id.ll_homepage, fragment_home_main).commitAllowingStateLoss();




//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        NavigationView navView = (NavigationView)findViewById(R.id.menu_view);
//        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//
//        Intent intent = getIntent();
//        String s_userName = intent.getStringExtra("userName");
//        String s_userPW = intent.getStringExtra("userPW");
        userSettings = getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = userSettings.edit();
//        editor.putString("userName",s_userName);
//        editor.putString("userPW",s_userPW);
        editor.putString("url","http://www.xinxianquan.xyz:8080/zhaqsq/user/login");
        editor.commit();

        //广播退出账号
        IntentFilter intentFilter=new IntentFilter("com.example.SignOut");
        registerReceiver(receiver,intentFilter);

        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_Community");
        registerReceiver(close_receiver,close_intentFilter);

//        bt_mainpage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        /**
//         * 菜单
//         */
//        if(actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.menu);
//        }
////        navView.setCheckedItem(R.id.);
//        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                mDrawerLayout.closeDrawers();
//                return true;
//            }
//        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_homepage);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

//    private void initView() {
//
//    }


}
