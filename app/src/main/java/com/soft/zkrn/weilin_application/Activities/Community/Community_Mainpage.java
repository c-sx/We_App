package com.soft.zkrn.weilin_application.Activities.Community;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.soft.zkrn.weilin_application.Adapter.CommunityAdapter;
import com.soft.zkrn.weilin_application.Adapter.TaskAdapter;
import com.soft.zkrn.weilin_application.Class.Task;
import com.soft.zkrn.weilin_application.GsonClass.CommunityData;
import com.soft.zkrn.weilin_application.GsonClass.TaskData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.Class.community;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Community_Mainpage extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private community[] communities;
    private List<community> communityList = new ArrayList<>();

    private CommunityAdapter adapter;
    private TextView tvType;
    private TextView tvNum;
    private TextView tvDistance;
    private TextView btSearch;
    private Button btNext;
    private RecyclerView recyclerView;

    private int communityNum = 0;



    ///////////////////////////

//
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

    private static final int FORDATA = 1;
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
                case FORDATA:
//                    String s = "";
//                    Log.d(s,"First_Handler");
                    System.out.println("handler");
                    CommunityData data = (CommunityData)msg.obj;
                    List<CommunityData.Extend.Communities> list= data.getExtend().getCommunities();
//                    TaskData.Extend.Pageinfo pageinfo = dataForPage.getextend().getpageinfo();
//                    pageNum = pageinfo.getpageNum();
////                    Log.d(s,"page一共"+pages);
//                    pages = pageinfo.getpages();
//                    pageSize = pageinfo.getpageSize();
//                    size = pageinfo.getsize();
//                    ifOK = true;
//                    initTaskSecond();
//                    initData();
//                    break;
//                case ADD:
//                    TaskData taskData = (TaskData) msg.obj;
//                    List<TaskData.Extend.Pageinfo.TaskList> list = taskData.getextend().getpageinfo().getlist();
                    for(int i = 0; i < list.size(); i ++){//int id,String title,String type,int num,String description,String location,byte[]image
                        System.out.println("标题为"+list.get(i).getComTitle());
                        community cm = new community(
                                list.get(i).getComId(),
                                list.get(i).getComTitle(),
                                list.get(i).getComCategory(),
                                list.get(i).getComNumber(),//人数
                                list.get(i).getComDesp(),
                                list.get(i).getComAddress(),//地点
                                list.get(i).getComPicture()
                        );
                        communityNum ++;
                        communityList.add(cm);
                        if(communityNum >= MAXSIZE)
                            break;
                    }
                    recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
                    GridLayoutManager layoutManager = new GridLayoutManager(Community_Mainpage.this,1);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new CommunityAdapter(communityList);
                    recyclerView.setAdapter(adapter);

                    if(adapter != null) {
                        adapter.setOnItemClickLitener(new CommunityAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(View view, int position,int callId,int num,String type,String title,String description) {
                                Intent intent = new Intent(Community_Mainpage.this,Community_Introduction.class);
                                intent.putExtra("extra_id",callId);
                                intent.putExtra("extra_num",num);
                                intent.putExtra("extra_type",type);
                                intent.putExtra("extra_title",title);
                                intent.putExtra("extra_description",description);

                                startActivity(intent);
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

    /**
     * 录入卡片
     */
//    private community[] initData(){
//        community[] cmy = {
//            new community(0,12,112322,"音乃木坂","学校"),
//                    new community(0,43,114514,"下北沢","池沼"),
//                    new community(0,1111,212111,"红场","广场"),
//                    new community(0,2,322222,"新日暮里","圣地"),
//                    new community(0,22,343322,"圣米歇尔山","景点")
//        };
//        return cmy;
//    }

    private void initCommunity(){
        communityList.clear();
        httpUtil.GET("http://www.xinxianquan.xyz:8080/zhaqsq/community/all", new CallBack_Get() {
            @Override
            public void onFinish(String response) {
                System.out.println(response);
                gsonUtil.translateJson(response, CommunityData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        CommunityData data = (CommunityData)obj;
                        if(data.getCode() == 100){
                            Message msg = Message.obtain();
                            msg.what = FORDATA;
                            msg.obj = data;
//                        TaskData dataForPage = (TaskData)obj;
//                        TaskData.Extend.Pageinfo pageinfo = dataForPage.getextend().getpageinfo();
//                        System.out.println("获得页数");
                            handler.sendMessage(msg);
                        }else{
                            System.out.println(3);
                        }
                    }
                    @Override
                    public void onFail(Exception e) {
                        System.out.println(1);
//                        Toast.makeText(Community_Mainpage.this,"1", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onError(Exception e) {
                System.out.println(2);
//                Toast.makeText(Community_Mainpage.this,"2", Toast.LENGTH_SHORT).show();
            }
        });
//        communityList.clear();
//        communities = initData();
//        for(int i = 0 ; i < 10 ; i++ ){
//            communityList.add(communities[i % 5]);
//        }
    }

    /**
     * 展示选择器
     */
    private void showPickerView(int n , final TextView tv) {
//      要展示的数据
        final List<String> listData ;
        switch (n){
            case 0:
                listData = getData_type();
                break;
            case 1:
                listData = getData_num();
                break;
            case 2:
                listData = getData_distance();
                break;
            default:
                listData = null;
                break;
        }
//      监听选中
        OptionsPickerView pvOptions = new OptionsPickerBuilder(Community_Mainpage.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
//               返回的分别是三个级别的选中位置
//              展示选中数据
                tv.setText(listData.get(options1));
            }
        })
                .setSelectOptions(0)//设置选择第一个
                .setOutSideCancelable(false)//点击背的地方不消失
                .build();//创建
//      把数据绑定到控件上面
        pvOptions.setPicker(listData);
//      展示
        pvOptions.show();
    }

    /**
     * 数据
     */
    private List<String> getData_distance() {
        List<String> list = new ArrayList<>();
        list.add("5百米以内");
        list.add("5百米至2千米");
        list.add("2千米至1万米");
        list.add("其他");
        return list;
    }

    private List<String> getData_type() {
        List<String> list = new ArrayList<>();
        list.add("学校");
        list.add("生活小区");
        list.add("工作单位");
        list.add("娱乐场所");
        list.add("其他");
        return list;
    }

    private List<String> getData_num() {
        List<String> list = new ArrayList<>();
        list.add("十人以内");
        list.add("十人以上且五百人以内");
        list.add("五百人以上");
        list.add("其他");
        return list;
    }

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


//    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals("action.refreshActivity_A"))
//            {
//                initRefresh();
//            }
//        }
//    };
        // broadcast receiver
//        private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String action = intent.getAction();
//                if (action.equals("action.refreshFriend"))
//                {
//                    reFreshFrinedList();
//                }
//            }
//        };
//    private void refresh(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    Thread.sleep(10);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        initCommunity();
//                        adapter.notifyDataSetChanged();
//
//                    }
//                });
//            }
//        }).start();
//    }


    /**
     * 注销广播
     *
     */
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(close_receiver);
    }

//    protected void onDestroy() {
//        super.onDestroy();
//
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycommunity_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        NavigationView navView = (NavigationView)findViewById(R.id.menu_view);
//        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//        tvType = findViewById(R.id.mycommunity_type);
//        tvNum = findViewById(R.id.mycommunity_num);
//        tvDistance = findViewById(R.id.mycommunity_distance);
        btNext = findViewById(R.id.bt_mycommunity_next);
//        btSearch = findViewById(R.id.bt_mycommunity_searching);

        //////////
        //////////////

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //刷新页面广播
        IntentFilter intentFilter=new IntentFilter("com.example.Refresh_MyCommunity");
        registerReceiver(receiver,intentFilter);
        //关闭社区页面广播
        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_Community");
        registerReceiver(close_receiver,close_intentFilter);



        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Community_Mainpage.this, Community_Creator.class);
                startActivity(intent);
            }
        });

//        btSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Community_Mainpage.this,"木大", Toast.LENGTH_SHORT).show();
//            }
//        });

//        /**
//         * 刷新
//         */
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("action.refreshFriend");


//        /**
//         * 悬浮按钮
//         */
//        fab.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Toast.makeText(Community_Mainpage.this,"木大",Toast.LENGTH_SHORT).show();
////                Intent intent_zhu = new Intent(MainActivity.this, Main2Activity.class);
////                startActivityForResult(intent_zhu, 1);
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

        /**
         * 卡片布局
         */
        initCommunity();
//         = (RecyclerView)findViewById(R.id.recycler_view);
//        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new CommunityAdapter(communityList);
//        recyclerView.setAdapter(adapter);


//        adapter.setOnItemClickLitener(new CommunityAdapter.OnItemClickLitener() {
//            @Override
//            public void onItemClick(View view, int position, int num, String type) {
//                Intent intent = new Intent(Community_Mainpage.this, Community_Introduction.class);
//                intent.putExtra("extra_num",num);
//                intent.putExtra("extra_type",type);
//                startActivity(intent);
//            }
//        });
//        recyclerView.setAdapter(adapter);

//        /**
//         * 选项卡
//         */
//        tvType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPickerView(0,tvType);
//            }
//        });
//
//        tvNum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPickerView(1,tvNum);
//            }
//        });
//
//        tvDistance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPickerView(2,tvDistance);
//            }
//        });



    }
}
