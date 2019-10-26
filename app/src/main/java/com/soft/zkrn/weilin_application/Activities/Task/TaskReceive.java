package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.soft.zkrn.weilin_application.Adapter.TaskAdapter;
import com.soft.zkrn.weilin_application.Class.Task;
import com.soft.zkrn.weilin_application.GsonClass.StateData;
import com.soft.zkrn.weilin_application.GsonClass.TaskData;
import com.soft.zkrn.weilin_application.GsonClass.TaskData_TaskReceive;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.Widget.ScreenUtils;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Put;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskReceive extends AppCompatActivity {

    private AlertDialog dialog;
    private int tasksNum = 0;
    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter adapter;
    private RecyclerView recyclerView;

    private int pageNum;//当前页数
    private int pages; //总页数
    private int pageSize;//每页的数量
    private int size;//当前页的数量
    private boolean ifOK = false;//网络状态
    private int uId;

//    private static final int FORPAGE = 1;
    private static final int ADD = 2;
    private static final int FINISH = 3;
    private static final int FAIL = 4;
    private static final int SUCCESS = 5;

    private static final int MAXSIZE = 10;

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();
//    private String get_url = "http://119.23.190.83:8080/zhaqsq/call/calls";
    private String get_url = "http://119.23.190.83:8080/zhaqsq/call/getcomcall";
    private String revamp_url = "http://119.23.190.83:8080/zhaqsq/call/update/{callId}";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FAIL:
                    dialog.dismiss();
                    Toast.makeText(TaskReceive.this,"网络状态异常，请重试",Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    dialog.dismiss();
                    Toast.makeText(TaskReceive.this,"接受成功",Toast.LENGTH_SHORT).show();
                    taskList.clear();
                    initData();
                    break;
                case ADD:
                    TaskData_TaskReceive taskData = (TaskData_TaskReceive) msg.obj;
                    List<TaskData_TaskReceive.Extend.Call> list = taskData.getExtend().getCalls();
                    if(list.size() == 0){
                        changeView();
                    }else{
                        for(int i = 0; i < list.size(); i ++){
                            Task tk = new Task(
                                    list.get(i).getCallId(),
                                    list.get(i).getSubId(),
                                    list.get(i).getSubTime(),
                                    list.get(i).getEndTime(),
                                    list.get(i).getCallTitle(),
                                    list.get(i).getCallDesp(),
                                    list.get(i).getCallMoney(),
                                    list.get(i).getCallNow(),
                                    list.get(i).getRecId(),
                                    list.get(i).getSubName(),
                                    list.get(i).getRecName(),
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
                                public void onItemClick(View view, int position,int callId,String title,String desp,int money,long time,String state) {
                                    showDialog(callId,title,desp,money,time);
//                                Intent intent = new Intent(getActivity(), Community_Introduction.class);
//                                intent.putExtra("extra_id",callId);
//                                getActivity().startActivity(intent);
//                                Toast.makeText(TaskReceive.this,"疼",Toast.LENGTH_SHORT).show();
                                }
                            });
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    break;
                default:
                    break;
            }
        }
    };

    private TextView mTextMessage;
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
    private void initData(){
//        uId	是	Int	用户id
//        callNow	是	Int	任务状态
//        状态，1.未接取，2.未完成，3，未评价，4.已评价
        List<String>names = new ArrayList<>();
        List<String>values = new ArrayList<>();
        names.add("uId");
        names.add("callNow");
        values.add(String.valueOf(uId));
        values.add("1");
        httpUtil.GET(TaskReceive.this,get_url,names,values, new CallBack_Get() {
            @Override
            public void onFinish(String response) {
                System.out.println(response);
                gsonUtil.translateJson(response, TaskData_TaskReceive.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
//                        Message msg = Message.obtain();
//                        msg.what = FORPAGE;
//                        msg.obj = obj;
//                        handler.sendMessage(msg);
                        newTask(response);
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

//    private void initDataSecond(){
//        System.out.println("进入了initDataSecond");
//        for(int i = 0; i < pages; i ++){
//            httpUtil.GET(TaskReceive.this,get_url, "pn",String.valueOf(i), new CallBack_Get() {
//                @Override
//                public void onFinish(String response) {
//                    newTask(response);
//                }
//                @Override
//                public void onError(Exception e) { }
//            });
//            if(tasksNum >= MAXSIZE)
//                break;
//        }
//        Message msg = Message.obtain();
//        msg.what = FINISH;
//        handler.sendMessage(msg);
//
//    }

    private void newTask(String res){
        gsonUtil.translateJson(res, TaskData_TaskReceive.class, new CallBackGson() {
            @Override
            public void onSuccess(Object obj) {
                TaskData_TaskReceive taskData = (TaskData_TaskReceive) obj;
                if(taskData.getCode() == 100){
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

        //获得用户ID
        uId = readPsw_Int("userID");


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

        //卡片布局
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        /**
         * 卡片布局
         */
        taskList.clear();
        initData();

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

    private int readPsw_Int(String userID){
        SharedPreferences userSettings = (SharedPreferences) getSharedPreferences("setting",MODE_PRIVATE);
        return userSettings.getInt(userID,0);
    }

    //请求接受
//    callNow	否	string	状态
    private void callForReceive(int cid){

        HashMap<String, String> paramsMap = new HashMap<>();
        System.out.println("recid="+uId);
        System.out.println("callid="+cid);
        paramsMap.put("recId",String.valueOf(uId));
        paramsMap.put("callId",String.valueOf(cid));
        paramsMap.put("callNow","2");
        Message msg = Message.obtain();
        httpUtil.PUT(TaskReceive.this,revamp_url, paramsMap, new CallBack_Put() {
            @Override
            public void onFinish(String response) {
//                if(response == "")System.out.println("空的了");
//                System.out.println(response);
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        StateData data = (StateData) obj;
//                        if(data == null)System.out.println("空的");
                        if(data.getCode() == 100){
                            System.out.println("接受成功");
                            msg.what = SUCCESS;
                            handler.sendMessage(msg);
                        }else{
                            msg.what = FAIL;
                            handler.sendMessage(msg);
                        }
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

    //展示任务详细内容
    private void showDialog(int callId,String title,String desp,int money,long time){
        final View view = LayoutInflater.from(this).inflate(R.layout.task_receive_dialog,null,false);
        dialog = new AlertDialog.Builder(this).setView(view).create();

        Button btn_cancel = view.findViewById(R.id.bt_cancel);
        Button btn_agree = view.findViewById(R.id.bt_agree);
        TextView tv_title = view.findViewById(R.id.tv_dialog_title);
        TextView tv_content = view.findViewById(R.id.tv_dialog_content);
        TextView tv_time = view.findViewById(R.id.tv_dialog_time);
        TextView tv_money = view.findViewById(R.id.tv_dialog_money);

        tv_title.setText(title);
        tv_content.setText(desp);
        tv_money.setText("￥"+money);
//        tv_time.setText(time+"前");


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.dismiss();
                callForReceive(callId);
                btn_agree.setEnabled(false);
                btn_cancel.setEnabled(false);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)), LinearLayout.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setLayout((ScreenUtils.getScreenHeight(this)), LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(false);
    }

    private void changeView(){
        setContentView(R.layout.activity_task_receive2);
    }

}
