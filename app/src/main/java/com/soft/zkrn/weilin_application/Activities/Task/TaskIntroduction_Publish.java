package com.soft.zkrn.weilin_application.Activities.Task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soft.zkrn.weilin_application.GsonClass.StateData;
import com.soft.zkrn.weilin_application.GsonClass.TaskData_Certain;
import com.soft.zkrn.weilin_application.GsonClass.TaskData_PublishedOrReceived;
import com.soft.zkrn.weilin_application.GsonClass.UserInformationData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Put;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.HashMap;

public class TaskIntroduction_Publish extends AppCompatActivity {

    private long time;
    private String desp;
    private int callId;
    private int money;
    private String title;
    private String state;
    private int my_point;
    private int receiver_point;
    private int receiver_id;
    private int my_id;
    private int change_point;

    private Button bt_finish;
    private TextView tv_desp;
    private TextView tv_state;
    private TextView tv_title;
    private TextView tv_money;

    private String url_finish = "http://119.23.190.83:8080/zhaqsq/call/update/{callId}";
    private String url_task = "http://119.23.190.83:8080/zhaqsq/call/get/";
    private String url_receiver = "http://119.23.190.83:8080/zhaqsq/user/get";

    private String url_change_point = "http://119.23.190.83:8080/zhaqsq/user/{uid}";

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();

    private static final int SUCCESS_TASK = 1;
    private static final int FAIL = 2;
    private static final int SUCCESS_RECEIVER = 3;
    private static final int SUCCESS_FINISH = 4;
    private static final int SUCCESS_POINT_H = 5;
    private static final int SUCCESS_POINT_I = 6;
    private static final int FAIL_CHANGE = 7;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS_TASK:
                    TaskData_Certain data = (TaskData_Certain) msg.obj;
//                    recId	int	接收人id
                    change_point = data.getExtend().getCall().getCallMoney();
                    receiver_id = data.getExtend().getCall().getRecId();
                    callForReceiverInformation();
                    break;
                case FAIL:
                    Toast.makeText(TaskIntroduction_Publish.this, "网络状态异常",Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS_RECEIVER:
                    UserInformationData uData = (UserInformationData)msg.obj;
                    receiver_point = uData.getExtend().getUser().getUserPoint();
                    System.out.println("phoneNumber");
                    tv_state.setText("已被接收，等待完成中。\n接收人的电话为" + uData.getExtend().getUser().getUserPhonenumber());
                    bt_finish.setEnabled(true);
                    break;
                case SUCCESS_FINISH:
                    Toast.makeText(TaskIntroduction_Publish.this,"任务完成",Toast.LENGTH_SHORT).show();
                    tv_state.setText("已完成，等待评价中");
                    bt_finish.setEnabled(false);
                    callForChangeReceiversPoint();
                    break;
                case SUCCESS_POINT_H:
                    callForChangeMyPoint();
                    break;
                case SUCCESS_POINT_I:
                    Intent intent = new Intent();
                    intent.setAction("com.example.Refresh_TaskCenter");
                    sendBroadcast(intent);
                    break;
                case FAIL_CHANGE:
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_introduction);

        Intent intent = getIntent();
        time = intent.getLongExtra("time",0);
        callId = intent.getIntExtra("callId",0);
        money = intent.getIntExtra("money",0);
        desp = intent.getStringExtra("desp");
        title = intent.getStringExtra("title");
        state = intent.getStringExtra("state");
        my_id = intent.getIntExtra("uid",0);
        my_point = intent.getIntExtra("upoint",0);
//        intent.putExtra("position",position);
//        intent.putExtra("callId",callId);
//        intent.putExtra("title",title);
//        intent.putExtra("desp",desp);
//        intent.putExtra("money",money);
//        intent.putExtra("time",time);
//        intent.putExtra("state",state);
//        intent.putExtra("uid",id);
//        intent.putExtra("upoint",point);

        bt_finish = findViewById(R.id.bt_finish);
        tv_desp = findViewById(R.id.tv_introduction_description);
        tv_money = findViewById(R.id.tv_introduction_money);
        tv_title = findViewById(R.id.tv_introduction_title);
        tv_state = findViewById(R.id.tv_introduction_state);

        tv_title.setText(title);
        tv_money.setText(String.valueOf(money));
        tv_desp.setText(desp);

        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_finish.setEnabled(false);
                callForFinish();
            }
        });

        Intent intent2 = new Intent();
        intent2.setAction("com.example.Refresh_TaskCenter");
        sendBroadcast(intent2);

        if(callId == 0){
            Toast.makeText(TaskIntroduction_Publish.this,"网络异常",Toast.LENGTH_SHORT).show();
        }else{
//        1.未接取，2.未完成，3，未评价，4.已评价
            switch (state){
                case "1":
                    tv_state.setText("等待被接收中");
                    bt_finish.setEnabled(false);
                    break;
                case "2":
                    bt_finish.setEnabled(false);
                    callForMore();
                    break;
                case "3":
                    tv_state.setText("已完成，等待评价中");
                    bt_finish.setEnabled(false);
                    break;
                case "4":
                    tv_state.setText("已结束");
                    bt_finish.setEnabled(false);
                    break;
                default:
                    bt_finish.setEnabled(false);
                    break;
            }
        }
    }

//    callId	是	Int	任务id
    private void callForMore(){
        Message msg = Message.obtain();
        System.out.println("callId=" + callId);
        httpUtil.GET(TaskIntroduction_Publish.this,url_task,callId, new CallBack_Get() {
            @Override
            public void onFinish(String response) {
                System.out.println(response);
                gsonUtil.translateJson(response, TaskData_Certain.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        TaskData_Certain data = (TaskData_Certain) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS_TASK;
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

//    uid	是	int	用户id
    private void callForReceiverInformation(){
        Message msg = Message.obtain();
        httpUtil.GET(TaskIntroduction_Publish.this,url_receiver, "uid", String.valueOf(receiver_id), new CallBack_Get() {
            @Override
            public void onFinish(String response) {
                System.out.println(response);
                gsonUtil.translateJson(response, UserInformationData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        UserInformationData data = (UserInformationData) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS_RECEIVER;
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

//    callId	是	int	任务id
//    subId	否	int	发布人id
//    subTime	否	date	发布时间
//    endTime	否	date	截止时间
//    callTitle	否	string	标题
//    callDesp	否	string	描述
//    callMoney	否	int	金额
//    callNow	否	string	状态
//    recId	否	int	接收人id
//    subName	否	string	发布人昵称
//    recName	否	string	接收人昵称
//    callAddress	否	string	发布人地址
    private void callForFinish(){
        HashMap<String, String> paramsMap = new HashMap<>();
        System.out.println("callid="+callId);
        paramsMap.put("callNow","3");
        paramsMap.put("callId",String.valueOf(callId));
        Message msg = Message.obtain();
        httpUtil.PUT(TaskIntroduction_Publish.this, url_finish,paramsMap, new CallBack_Put() {
            @Override
            public void onFinish(String response) {
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        StateData data = (StateData) obj;
//                        if(data == null)System.out.println("空的");
                        if(data.getCode() == 100){
                            System.out.println("接受成功");
                            msg.what = SUCCESS_FINISH;
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

//    userPoint	否	int	积分
    private void callForChangeReceiversPoint(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("uid" , String.valueOf(receiver_id));
        paramsMap.put("userPoint", String.valueOf(receiver_point + change_point));
        Message msg = Message.obtain();
        httpUtil.PUT(TaskIntroduction_Publish.this,url_change_point, paramsMap, new CallBack_Put() {
            @Override
            public void onFinish(String response) {
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        StateData data = (StateData) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS_POINT_H;
                        }else{
                            msg.what = FAIL_CHANGE;
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = FAIL_CHANGE;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAIL_CHANGE;
                handler.sendMessage(msg);
            }
        });
    }

//    userPoint	否	int	积分
    private void callForChangeMyPoint(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("uid" , String.valueOf(my_id));
        paramsMap.put("userPoint", String.valueOf(my_point - change_point));
        Message msg = Message.obtain();
        httpUtil.PUT(TaskIntroduction_Publish.this,url_change_point, paramsMap, new CallBack_Put() {
            @Override
            public void onFinish(String response) {
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        StateData data = (StateData) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS_POINT_I;
                        }else{
                            msg.what = FAIL_CHANGE;
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = FAIL_CHANGE;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAIL_CHANGE;
                handler.sendMessage(msg);
            }
        });
    }
}
