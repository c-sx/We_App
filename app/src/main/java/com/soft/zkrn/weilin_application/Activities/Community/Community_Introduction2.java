package com.soft.zkrn.weilin_application.Activities.Community;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.Activities.Home.Homepage;
import com.soft.zkrn.weilin_application.GsonClass.CommunityData;
import com.soft.zkrn.weilin_application.GsonClass.StateData;
import com.soft.zkrn.weilin_application.GsonClass.UserInformationData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.Widget.ScreenUtils;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Delete;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Post;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Put;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import java.util.HashMap;
import java.util.List;

public class Community_Introduction2 extends AppCompatActivity {

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();
//    private String id_url = "http://119.23.190.83:8080/zhaqsq/user/get";
    private String quit_url = "http://119.23.190.83:8080/zhaqsq/unc/deleteunc";
//    private String quit_community_url = "http://119.23.190.83:8080/zhaqsq/unc/deletec";
//    private String quit_user_url = "http://119.23.190.83:8080/zhaqsq/unc/deleteu";
    private String number_url = "http://119.23.190.83:8080/zhaqsq/community/updatepic/{comId}";

    private Button bt_join;
    private TextView tv_type;
    private TextView tv_num;
    private TextView tv_name;
    private TextView tv_description;

    private int number;
    private int uId = 0;
    private int cId;
    private String userName;
    private String type;
    private String des;
    private String num;
    private String title;

    private AlertDialog dialog;
    private Button btn_cancel;
    private Button btn_agree;

    private static final int SUCCESS = 1;
    private static final int FAIL = 2;
    private static final int SUCCESS_ID = 3;
    private static final int SUCCESS_SUB = 4;
    private static final int COMMUNITY = 5;
    private static final int OUT = 6;
    private static final int SUCCESS_COMMUNITY = 7;
    private static final int SUCCESS_QUIT = 8;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
//                case SUCCESS_ID:
//                    showDialog();
//                    break;
                case SUCCESS_QUIT:
                    Toast.makeText(Community_Introduction2.this,"退出成功", Toast.LENGTH_SHORT).show();
                    callForNumber();
                    break;
                case FAIL:
                    Toast.makeText(Community_Introduction2.this,"退出失败，请重试", Toast.LENGTH_SHORT).show();
                    break;
//                case SUCCESS:
//                    callForNumber();
//                    break;
                case SUCCESS_SUB:
//                    Intent intent = new Intent(Community_Introduction2.this, Homepage.class);
//                    startActivity(intent);
                    Intent refresh_intent = new Intent();
                    refresh_intent.setAction("com.example.Refresh_MyCommunity");
                    sendBroadcast(refresh_intent);
                    finish();

                    //页面跳转
                    break;
                default:
                    break;
            }
        }
    };

    //获得后台String数据
    private String readPsw_String(String userName){
        SharedPreferences userSettings = (SharedPreferences) getSharedPreferences("setting",MODE_PRIVATE);
        return userSettings.getString(userName,"");
    }
    //获得后台int数据
    private int readPsw_Int(String userID){
        SharedPreferences userSettings = (SharedPreferences) getSharedPreferences("setting",MODE_PRIVATE);
        return userSettings.getInt(userID,0);
    }

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
        unregisterReceiver(close_receiver);
    }
//    private void getID(){
//        httpUtil.GET(Community_Introduction2.this,id_url, "userName", userName, new CallBack_Get() {
//            @Override
//            public void onFinish(String response) {
//                gsonUtil.translateJson(response, UserInformationData.class, new CallBackGson() {
//                    @Override
//                    public void onSuccess(Object obj) {
//                        Message msg = Message.obtain();
//                        UserInformationData data = (UserInformationData) obj;
//                        if(data.getCode() == 100){
//                            uId = data.getExtend().getUser().getUid();
//                            msg.what = SUCCESS_ID;
//                        }else{
//                            msg.what = FAIL;
//                        }
//                        handler.sendMessage(msg);
//                    }
//
//                    @Override
//                    public void onFail(Exception e) {
//                        Message msg = Message.obtain();
//                        msg.what = FAIL;
//                        handler.sendMessage(msg);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Message msg = Message.obtain();
//                msg.what = FAIL;
//                handler.sendMessage(msg);
//            }
//        });
//    }

    //请求后台修改社区人数
    private void callForNumber(){
//        comId	是	int	社区id
//        comTitle	否	string	社区标题
//        comCategory	否	string	社区种类
//        comNumber	否	int	社区人数
//        comDesp	否	string	社区描述
//        comAddress	否	string	社区地址
//        comPicture	否	byte[]	社区头像*/
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("comTitle", title);
        paramsMap.put("comId", String.valueOf(cId));
        paramsMap.put("comNumber" , String.valueOf(number - 1));
        Message msg = Message.obtain();
        httpUtil.PUT(Community_Introduction2.this,number_url, paramsMap, new CallBack_Put() {
            @Override
            public void onFinish(String response) {
                System.out.println(response);
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        msg.what = SUCCESS_SUB;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = SUCCESS_SUB;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAIL;
                System.out.println(4);
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community__introduction2);

        Intent intent = getIntent();
        cId = intent.getIntExtra("extra_id",0);
        number = intent.getIntExtra("extra_num",0);
        type = intent.getStringExtra("extra_type");
        des = intent.getStringExtra("extra_description");
        title = intent.getStringExtra("extra_title");
        num = String.valueOf(number);

        bt_join = findViewById(R.id.bt_communityintroduction);
        tv_type = findViewById(R.id.communityIntroduction_type);
        tv_num = findViewById(R.id.communityintroduction_num);
        tv_description = findViewById(R.id.tv_introduction_description);

        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_Community");
        registerReceiver(close_receiver,close_intentFilter);

        tv_num.setText(num);
        tv_type.setText(type);
        tv_description.setText(des);

        bt_join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                userName = readPsw_String("userName");
                uId = readPsw_Int("userID");

                showDialog();

                //广播退出社区页面
//                Intent close_intent = new Intent();
//                close_intent.setAction("com.example.Close_Community");
//                sendBroadcast(close_intent);
//                finish();
            }
        });
    }

    //展示对话框
    private void showDialog(){
        final View view = LayoutInflater.from(this).inflate(R.layout.community_introdution_quit_dialog,null,false);
        dialog = new AlertDialog.Builder(this).setView(view).create();

        btn_cancel = view.findViewById(R.id.bt_cancel);
        btn_agree = view.findViewById(R.id.bt_agree);

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
                btn_agree.setEnabled(false);
                btn_cancel.setEnabled(false);
                quit();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)), LinearLayout.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setLayout((ScreenUtils.getScreenHeight(this)), LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(false);
    }

    //请求删除用户社区关系
    private void quit(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("uId" , String.valueOf(uId));
        System.out.println("uid" + String.valueOf(uId));
        System.out.println("cId" + String.valueOf(cId));
        paramsMap.put("cId", String.valueOf(cId));
        Message msg = Message.obtain();
        httpUtil.DELETE(Community_Introduction2.this,quit_url, paramsMap, new CallBack_Delete() {
            @Override
            public void onFinish(String response) {
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        StateData data = (StateData) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS_QUIT;
                            System.out.println("!quit!");
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
}
