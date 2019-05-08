package com.soft.zkrn.weilin_application.Activities.Community;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.Activities.Home.Homepage;
import com.soft.zkrn.weilin_application.GsonClass.StateData;
import com.soft.zkrn.weilin_application.GsonClass.UserInformationData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Post;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class Community_Introduction extends AppCompatActivity {

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();

    private Button bt_join;
    private TextView tv_type;
    private TextView tv_num;
    private TextView tv_name;
    private TextView tv_description;

    private int uId;
    private int cId;
    private String userName;
    private String type;
    private String des;
    private String num;

    private static final int SUCCESS = 1;
    private static final int FAIL = 2;
    private static final int SUCCESSID = 3;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESSID:
                    CallForJoin();
                    break;
                case FAIL:
                    Toast.makeText(Community_Introduction.this,"申请失败", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    //页面跳转
                    Toast.makeText(Community_Introduction.this,"申请成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Community_Introduction.this, Homepage.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    private String readPsw(String userName){
        SharedPreferences userSettings = (SharedPreferences) getSharedPreferences("setting",MODE_PRIVATE);
        return userSettings.getString(userName,"");
    }

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
        unregisterReceiver(close_receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_introduction);

        Intent intent = getIntent();
        cId = intent.getIntExtra("extra_id",0);
        int n = intent.getIntExtra("extra_num",0);
        type = intent.getStringExtra("extra_type");
        des = intent.getStringExtra("extra_description");
        num = String.valueOf(n);

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
                userName = readPsw("userName");
                httpUtil.GET("http://www.xinxianquan.xyz:8080/zhaqsq/user/get", "userName", userName, new CallBack_Get() {
                    @Override
                    public void onFinish(String response) {
                        gsonUtil.translateJson(response, UserInformationData.class, new CallBackGson() {
                            @Override
                            public void onSuccess(Object obj) {
                                Message msg = Message.obtain();
                                UserInformationData data = (UserInformationData) obj;
                                if(data.getCode() == 100){
                                    uId = data.getExtend().getUser().getUid();
                                    msg.what = SUCCESSID;
                                }else{
                                    msg.what = FAIL;
                                }
                                handler.sendMessage(msg);
                            }

                            @Override
                            public void onFail(Exception e) {
                                Message msg = Message.obtain();
                                msg.what = FAIL;
                                handler.sendMessage(msg);
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        Message msg = Message.obtain();
                        msg.what = FAIL;
                        handler.sendMessage(msg);
                    }
                });

                //广播退出社区页面
                Intent close_intent = new Intent();
                close_intent.setAction("com.example.Close_Community");
                sendBroadcast(close_intent);
//                finish();
            }
        });
    }

    private void CallForJoin(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("uId" , String.valueOf(uId));
        paramsMap.put("cId", String.valueOf(cId));
        httpUtil.POST("http://www.xinxianquan.xyz:8080/zhaqsq/unc/insert", paramsMap, new CallBack_Post() {
            @Override
            public void onFinish(String response) {
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        Message msg = Message.obtain();
                        StateData data = (StateData) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS;
                        }else{
                            msg.what = FAIL;
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFail(Exception e) {
                        Message msg = Message.obtain();
                        msg.what = FAIL;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Message msg = Message.obtain();
                msg.what = FAIL;
                handler.sendMessage(msg);
            }
        });
    }
}
