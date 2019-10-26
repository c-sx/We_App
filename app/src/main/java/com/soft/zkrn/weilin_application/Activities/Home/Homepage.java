package com.soft.zkrn.weilin_application.Activities.Home;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.soft.zkrn.weilin_application.Activities.Login.LoginActivity;
import com.soft.zkrn.weilin_application.Activities.Task.TaskCenter;
import com.soft.zkrn.weilin_application.Activities.Task.Task_CenterComment;
import com.soft.zkrn.weilin_application.Activities.Task.Task_CenterComplete;
import com.soft.zkrn.weilin_application.Activities.Task.Task_CenterPublish;
import com.soft.zkrn.weilin_application.Activities.Task.Task_CenterWhole;
import com.soft.zkrn.weilin_application.GsonClass.TaskData_PublishedOrReceived;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.soft.zkrn.weilin_application.okhttp.HttpUtil.FILE_NAME;

public class Homepage extends AppCompatActivity {

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();

//    private FragmentSwitchTool tool;
//    private DrawerLayout mDrawerLayout;
    private String url = "http://119.23.190.83:8080/zhaqsq/user/login";
    private String url_message = "http://119.23.190.83:8080/zhaqsq/call/getBysub";
    private SharedPreferences userSettings;
    private Refresh_Thread thread = new Refresh_Thread();

    private HomepageTask fragment_home_task;
    private HomepageMainpage fragment_home_main;
    private HomepageUser fragment_home_user;

    private View badge;

    private int uid;
    private boolean if_id = false;
    private int received_num;
    private int complete_num;
    private boolean ifChecked = false;
    private boolean ifUserFragmentOn = false;
    private boolean ifNew = false;
    //private boolean ifNew = false;
    private List<Boolean>statement;

    private static final int SUCCESS = 1;
    private static final int FAIL = 2;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    TaskData_PublishedOrReceived td = (TaskData_PublishedOrReceived)msg.obj;
                    List<TaskData_PublishedOrReceived.Extend.Calls>list = td.getExtend().getCalls();
                    int temp_received = 0;
                    int temp_completed = 0;
//                    1.未接取，2.未完成，3，未评价，4.已评价
                    for(int i = 0;i < list.size();i++){
                        if(list.get(i).getCallNow().equals("2")){
                            temp_received++;
                        }else if(list.get(i).getCallNow().equals("1")){

                        }else{
                            temp_completed++;
                        }
                    }
                    if(ifChecked == false){
                        ifChecked = true;
                        received_num = temp_received;
                        complete_num = temp_completed;
                    }else{
                        if(temp_completed + temp_received > received_num + complete_num){
                            //用户页面改变

                            if(fragment_home_user != null)
                                fragment_home_user.setNew(true);
                            ifNew = true;
                            if(!ifUserFragmentOn)
                                badge.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case FAIL:
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * BottomNavigation 底部菜单栏使用
     */
//    BottomNavigationItemView itemhome;
//    BottomNavigationItemView dashboard;
//    BottomNavigationItemView notifications;
//    Badge badge1;
//    Badge badge2;
//    Badge badge3;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ifUserFragmentOn = false;
                    if (fragment_home_main == null) {
                        fragment_home_main = new HomepageMainpage();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.ll_homepage, fragment_home_main).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_task:
                    ifUserFragmentOn = false;
                    if (fragment_home_task == null) {
                        fragment_home_task = new HomepageTask();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.ll_homepage, fragment_home_task).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_mine:
                    badge.setVisibility(View.INVISIBLE);
                    ifUserFragmentOn = true;
                    if (fragment_home_user == null) {
                        fragment_home_user = new HomepageUser();
                    }
                    fragment_home_user.setID(uid);
                    getSupportFragmentManager().beginTransaction().replace(R.id.ll_homepage, fragment_home_user).commitAllowingStateLoss();
    //                mListener.PassID(uid);
                    if(ifNew){
                        fragment_home_user.setNew(true);
                        ifNew = false;
                    }
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

    //接受消红点广播
    private BroadcastReceiver move_receiver =new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.example.Move_point")){
                SharedPreferences.Editor editor = userSettings.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(Homepage.this,LoginActivity.class));
                finish();
            }
        }
    };

    //接受退出广播
    private BroadcastReceiver receiver =new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals("com.example.SignOut")){
            SharedPreferences.Editor editor = userSettings.edit();
            editor.clear();
            editor.apply();
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
            if(action.equals("com.example.Close_Community")){
                thread.ifWork = false;
                finish();
            }
        }
    };

    //注销广播
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(close_receiver);
        unregisterReceiver(move_receiver);
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
//        tool = new FragmentSwitchTool(getFragmentManager(), R.id.flContainer);
//        tool.setClickableViews(ll_main, ll_task, ll_user);
//        tool.addSelectedViews(new View[]{bt_main, tv_main}).addSelectedViews(new View[]{bt_task, tv_task}).addSelectedViews(new View[]{bt_user, tv_user});
//        tool.setFragments(HomepageMainpage.class, HomepageTask.class, HomepageUser.class);
//        tool.changeTag(ll_main);
        received_num = 0;
        complete_num = 0;

        //实例化Fragment
        fragment_home_main = new HomepageMainpage();
        //把Fragment添加到Activity中,复写要调用commmit
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
        editor.apply();

//        thread.setDaemon(true);
//        thread.start();

        //广播退出账号
        IntentFilter intentFilter=new IntentFilter("com.example.SignOut");
        registerReceiver(receiver,intentFilter);
        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_Community");
        registerReceiver(close_receiver,close_intentFilter);
        IntentFilter move_intentFilter=new IntentFilter("com.example.Move_point");
        registerReceiver(move_receiver,move_intentFilter);
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
        //添加的Tab
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        System.out.println("num=" + menuView.getChildCount());
        //在用户图标上加Tab
        View tab = menuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) tab;
        //新创建的一个布局作为角标
        badge = LayoutInflater.from(this).inflate(R.layout.im_badge,menuView,false);
        //添加到Tab上
        itemView.addView(badge);
//        TextView textView = badge.findViewById(R.id.texT);
//        textView.setText(String.valueOf(1));
//        //无消息时可以将它隐藏即可
        //itemView.setVisibility(View.VISIBLE);
        badge.setVisibility(View.INVISIBLE);

        //载入用户信息
        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        if(sp.contains("ifID") == true){
            if_id = readPsw_Boolean("ifID");
        }
        if(if_id == false){
            Toast.makeText(Homepage.this, "登录状态过期，请重新登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Homepage.this,LoginActivity.class));
            finish();
        }else{
            uid = readPsw_Int("userID");
        }


        Refresh_Thread thread = new Refresh_Thread();
        thread.setDaemon(true);
        thread.start();



    }

//    if (menuView != null) {
//        int dp8 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
//        dotViews = new DotView[menuView.getChildCount()];
//        for (int i = 0; i < menuView.getChildCount(); i++) {
//            BottomNavigationItemView.LayoutParams params = new BottomNavigationItemView.LayoutParams(i == menuView.getChildCount() - 1 ? dp8 : dp8 * 2, 0);
//            params.gravity = Gravity.CENTER_HORIZONTAL;
//            params.leftMargin = dp8 * 3;
//            params.topMargin = dp8 / 2;
//            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
//            DotView dotView = new DotView(this);
//            itemView.addView(dotView, params);
//            if (i < menuView.getChildCount() - 1) {
//                dotView.setUnReadCount(new Random().nextInt(20));
//            }
//            dotViews[i] = dotView;
//        }
//    }
//    private BottomNavigationView navigation;
//    BottomNavigationMenuView menuView = null;
//        for (int i = 0; i < navigation.getChildCount(); i++) {
//        View child = navigation.getChildAt(i);
//        if (child instanceof BottomNavigationMenuView) {
//            menuView = (BottomNavigationMenuView) child;
//            break;
//        }
//    }

    private void callTaskOfPublish(){
        Message msg = Message.obtain();
        httpUtil.GET(Homepage.this,url_message, "subId", String.valueOf(uid), new CallBack_Get() {
            @Override
            public void onFinish(String response) {
//                System.out.println(response);
                gsonUtil.translateJson(response, TaskData_PublishedOrReceived.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        TaskData_PublishedOrReceived data = (TaskData_PublishedOrReceived) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS;
                            msg.obj = obj;
                        }else{
                            msg.what = FAIL;
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = FAIL;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAIL;
                handler.sendMessage(msg);
            }
        });
    }

    private class Refresh_Thread extends Thread{

        public volatile boolean ifWork = true;

        @Override
        public void run() {
            super.run();
            while(ifWork){
                if(isForeground()){
                    callTaskOfPublish();
                    System.out.println("onCall");
                }else{
                    System.out.println("NO");
                }
                try {
                    //1000 * 30
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //获得本活动是否为前台
        private boolean isForeground() {
            return isForeground(Homepage.this, Homepage.this.getClass().getName());
        }

        private boolean isForeground(Activity context, String className) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
            if (list != null && list.size() > 0) {
                ComponentName cpn = list.get(0).topActivity;
                if (className.equals(cpn.getClassName()))
                    return true;
            }
            return false;
        }
    }

}
