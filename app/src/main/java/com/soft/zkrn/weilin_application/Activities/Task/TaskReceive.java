package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.soft.zkrn.weilin_application.Adapter.TaskAdapter;
import com.soft.zkrn.weilin_application.Class.Task;
import com.soft.zkrn.weilin_application.GsonClass.TaskData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskReceive extends AppCompatActivity {

    private int tasksNum = 0;
    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter adapter;

    private RecyclerView recyclerView;


    private int pageNum;//当前页数
    private int pages; //总页数
    private int pageSize;//每页的数量
    private int size;//当前页的数量
    private boolean ifOK = false;//网络状态

    private static final int FORPAGE = 1;
    private static final int ADD = 2;
    private static final int FINISH = 3;

    private static final int MAXSIZE = 10;

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FORPAGE:
//                    String s = "";
//                    Log.d(s,"First_Handler");
                    TaskData dataForPage = (TaskData)msg.obj;
                    TaskData.Extend.Pageinfo pageinfo = dataForPage.getextend().getpageinfo();
                    pageNum = pageinfo.getpageNum();
//                    Log.d(s,"page一共"+pages);
                    pages = pageinfo.getpages();
                    pageSize = pageinfo.getpageSize();
                    size = pageinfo.getsize();
                    ifOK = true;
//                    initTaskSecond();
                    initDataSecond();
                    break;
                case ADD:
                    TaskData taskData = (TaskData) msg.obj;
                    List<TaskData.Extend.Pageinfo.TaskList> list = taskData.getextend().getpageinfo().getlist();
                    for(int i = 0; i < taskData.getextend().getpageinfo().getsize(); i ++){
                        Task tk = new Task(
                                list.get(i).getcallId(),
                                list.get(i).getsubId(),
                                list.get(i).getsubTime(),
                                list.get(i).getendTime(),
                                list.get(i).getcallTitle(),
                                list.get(i).getcallDesp(),
                                list.get(i).getcallMoney(),
                                list.get(i).getcallNow(),
                                list.get(i).getrecId(),
                                list.get(i).getsubName(),
                                list.get(i).getrecName(),
                                list.get(i).getCallAddress());
                        tasksNum ++;
                        taskList.add(tk);
                        if(tasksNum >= MAXSIZE)
                            break;
                    }
                    GridLayoutManager layoutManager = new GridLayoutManager(TaskReceive.this,1);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new TaskAdapter(taskList);
                    recyclerView.setAdapter(adapter);

                    if(adapter != null) {
                        adapter.setOnItemClickLitener(new TaskAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(View view, int position,int callId) {
//                                Intent intent = new Intent(getActivity(), Community_Introduction.class);
//                                intent.putExtra("extra_id",callId);
//                                getActivity().startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private TextView mTextMessage;
//    private Button btn_receive;
//    private RelativeLayout rtrc;
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

    /**
     * 录入卡片
     */
    private void initDataFirst(){
        httpUtil.GET("http://www.xinxianquan.xyz:8080/zhaqsq/call/calls", new CallBack_Get() {
            @Override
            public void onFinish(String response) {
                gsonUtil.translateJson(response, TaskData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        Message msg = Message.obtain();
                        msg.what = FORPAGE;
                        msg.obj = (TaskData)obj;
                        TaskData dataForPage = (TaskData)obj;
                        TaskData.Extend.Pageinfo pageinfo = dataForPage.getextend().getpageinfo();
                        pageNum = pageinfo.getpageNum();
                        pages = pageinfo.getpages();
                        pageSize = pageinfo.getpageSize();
                        size = pageinfo.getsize();
                        ifOK = true;
                        System.out.println("获得页数");
                        handler.sendMessage(msg);
                    }
                    @Override
                    public void onFail(Exception e) {
                    }
                });
            }
            @Override
            public void onError(Exception e) {
            }
        });
    }

    private void initDataSecond(){
        System.out.println("进入了initDataSecond");
        for(int i = 0; i < pages; i ++){
            httpUtil.GET("http://www.xinxianquan.xyz:8080/zhaqsq/call/calls", "pn",String.valueOf(i), new CallBack_Get() {
                @Override
                public void onFinish(String response) {
                    newTask(response);
                }
                @Override
                public void onError(Exception e) { }
            });
            if(tasksNum >= MAXSIZE)
                break;
        }
        Message msg = Message.obtain();
        msg.what = FINISH;
        handler.sendMessage(msg);

    }

    private void newTask(String res){
        gsonUtil.translateJson(res, TaskData.class, new CallBackGson() {
            @Override
            public void onSuccess(Object obj) {
                TaskData taskData = (TaskData) obj;
                if(taskData.getcode() == 100){
                    //正确返回数据
                    System.out.println("newTask OK");

                    Message msg = Message.obtain();
                    msg.what = ADD;
                    msg.obj = taskData;
                    handler.sendMessage(msg);
                }//否则不录入数据
            }

            @Override
            public void onFail(Exception e) { }
        });
    }

    /**
     * 接受关闭社区页面广播
     *
     */
    private BroadcastReceiver close_receiver =new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.example.Close_TaskReceive"))
                finish();
        }
    };
    /**
     * 注销广播
     *
     */
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(close_receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_receive);

        //关闭社区页面广播
        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_TaskReceive");
        registerReceiver(close_receiver,close_intentFilter);


        mTextMessage = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_task_receive);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        btn_receive = findViewById(R.id.btn_task_receive_confirm);
//        btn_receive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TaskReceive.this, ReceiveSuccess.class);
//                startActivity(intent);
//            }
//        });
//        rtrc = findViewById(R.id.rl_task_receive_content);
//        rtrc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TaskReceive.this, TaskReceiveDetails.class);
//                startActivity(intent);
//            }
//        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task_receive);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        /**
         * 卡片布局
         */
        taskList.clear();
        initDataFirst();

        /**
         * Navigaton finish
         */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
