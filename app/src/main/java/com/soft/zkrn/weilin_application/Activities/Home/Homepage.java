package com.soft.zkrn.weilin_application.Activities.Home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.soft.zkrn.weilin_application.Activities.Login.LoginActivity;
import com.soft.zkrn.weilin_application.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.soft.zkrn.weilin_application.okhttp.HttpUtil.FILE_NAME;

public class Homepage extends AppCompatActivity {

//    private FragmentSwitchTool tool;
//    private DrawerLayout mDrawerLayout;
    private String url = "http://119.23.190.83:8080/zhaqsq/user/login";
    private SharedPreferences userSettings;

    private HomepageTask fragment_home_task;
    private HomepageMainpage fragment_home_main;
    private HomepageUser fragment_home_user;

    private int uid;
    private boolean if_id = false;

//    private PassIDListener mListener;


    /**
     * BottomNavigation 底部菜单栏使用
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

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
                    fragment_home_user.setID(uid);
                    getSupportFragmentManager().beginTransaction().replace(R.id.ll_homepage, fragment_home_user).commitAllowingStateLoss();
    //                mListener.PassID(uid);
                    return true;
                default:
                    break;
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

    //接受退出广播
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

    //接受关闭社区页面广播
    private BroadcastReceiver close_receiver =new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals("com.example.Close_Community"))
            finish();
        }
    };

    //注销广播
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(close_receiver);
    }

    //获得数据库bool类型
    private boolean readPsw_Boolean(String s) {

        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        return sp.getBoolean(s,false);
    }

    //获得数据库int类型
    private int readPsw_Int(String s) {
        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        return sp.getInt(s,0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_homepage);

//        List<String> cookies = new ArrayList<String>();
//        SharedPreferences userSettingss = getSharedPreferences("setting",MODE_PRIVATE);
//        int length = userSettingss.getInt("cookies_length",0);
//        System.out.println("length="+length);
//        if(cookies.isEmpty() == false){
//            cookies.clear();
//        }
//        for(int i=0;i<length;i++){
//            cookies.add(userSettingss.getString("cookies"+i,""));
//        }
//        System.out.println("cookies="+cookies);


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
        editor.putString("url",url);
        editor.commit();

        //广播退出账号
        IntentFilter intentFilter=new IntentFilter("com.example.SignOut");
        registerReceiver(receiver,intentFilter);

        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_Community");
        registerReceiver(close_receiver,close_intentFilter);

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

        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        if(sp.contains("ifID") == true){
            if_id = readPsw_Boolean("ifID");
        }

        if(if_id == false){
            startActivity(new Intent(Homepage.this,LoginActivity.class));
            finish();
        }else{
            uid = readPsw_Int("userID");
        }

    }

//    public int getID(){
//        return uid;
//    }
//
//    public interface PassIDListener{
//        public void PassID(int id);
//    }
//    public void setPassIDListener(PassIDListener passIDListener){
//        this.mListener = passIDListener;
//    }


}
