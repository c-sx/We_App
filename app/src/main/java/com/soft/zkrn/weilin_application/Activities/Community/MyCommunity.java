package com.soft.zkrn.weilin_application.Activities.Community;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.soft.zkrn.weilin_application.Adapter.CommunityAdapter;
import com.soft.zkrn.weilin_application.Class.community;
import com.soft.zkrn.weilin_application.GsonClass.CommunityData;
import com.soft.zkrn.weilin_application.GsonClass.CommunityData_User;
import com.soft.zkrn.weilin_application.GsonClass.PublicData_CommunityBasic;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class MyCommunity extends AppCompatActivity {

    private List<community> communityList = new ArrayList<>();
    private CommunityAdapter adapter;
    private RecyclerView recyclerView;
    private Button bt_create;
    private Button bt_join;
    private TextView tv_state;

//    private boolean ifEmpty = false;
    private static final int FORDATA = 1;
    private static final int MAXSIZE = 2;

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FORDATA:
                    System.out.println("handler");
                    CommunityData_User data = (CommunityData_User)msg.obj;
                    List<CommunityData_User.Extend.Communities> list= data.getExtend().getCommunities();

                    if(list.size() == 0){
                        Toast.makeText(MyCommunity.this,"您还没有加入任何社区哦",Toast.LENGTH_SHORT).show();
                    }else{
                        tv_state.setText("我加入的社区");
                        for(int i = 0; i < list.size(); i ++){//int id,String title,String type,int num,String description,String location,byte[]image
//                        System.out.println("标题为"+list.get(i).getComTitle());
                            PublicData_CommunityBasic cb = list.get(i).getCommunityBasic();
                            community cm = new community(
                                    cb.getComId(),
                                    cb.getComTitle(),
                                    cb.getComCategory(),
                                    cb.getComNumber(),
                                    cb.getComDesp(),
                                    cb.getComAddress(),
                                    cb.getComPicture()
                            );
                            communityList.add(cm);
                        }
                    }
                    recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
                    GridLayoutManager layoutManager = new GridLayoutManager(MyCommunity.this,1);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new CommunityAdapter(communityList);
                    recyclerView.setAdapter(adapter);

                    adapter.setOnItemClickLitener(new CommunityAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position,int callId,int num,String type,String title,String description) {
                            Intent intent = new Intent(MyCommunity.this,Community_Introduction.class);
                            intent.putExtra("extra_id",callId);
                            intent.putExtra("extra_num",num);
                            intent.putExtra("extra_type",type);
                            intent.putExtra("extra_title",title);
                            intent.putExtra("extra_description",description);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    break;
            }
        }
    };

    /**
     * 接受刷新广播
     *
     */
    private BroadcastReceiver receiver =new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.example.Refresh_MyCommunity"))
                finish();
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
        setContentView(R.layout.activity_my_community);

        tv_state = findViewById(R.id.tv_my_community_state);
        bt_create = findViewById(R.id.bt_mycommunity_create);
        bt_join = findViewById(R.id.bt_mycommunity_join);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_my_community);
        setSupportActionBar(toolbar);

        //刷新页面广播
        IntentFilter intentFilter=new IntentFilter("com.example.Refresh_MyCommunity");
        registerReceiver(receiver,intentFilter);
        //关闭社区页面广播
        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_Community");
        registerReceiver(close_receiver,close_intentFilter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 卡片布局
         */
        initCommunity();

        bt_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCommunity.this,Community_Mainpage.class));
            }
        });

        bt_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCommunity.this,Community_Creation.class));
            }
        });

    }

    private int readPsw(String username) {
        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        return sp.getInt(username,0);
    }

    private void initCommunity(){
        communityList.clear();

        httpUtil.GET("http://www.xinxianquan.xyz:8080/zhaqsq/unc/getc","uId", String.valueOf(readPsw("userID")),new CallBack_Get() {
            @Override
            public void onFinish(String response) {
//                System.out.println(response);
                gsonUtil.translateJson(response, CommunityData_User.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        CommunityData_User data = (CommunityData_User)obj;
                        if(data.getCode() == 100){
                            Message msg = Message.obtain();
                            msg.what = FORDATA;
                            msg.obj = data;
                            handler.sendMessage(msg);
                        }
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
}
