package com.soft.zkrn.weilin_application.Activities.Task;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.Widget.PickerView;
import com.soft.zkrn.weilin_application.Widget.ScreenUtils;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TaskPublish extends AppCompatActivity {
    private String title;
    private String content;
    private String type;
    private int money;
    private long time;
    private long time_year;
    private long time_month;
    private long time_day;
    private long time_hour;

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();

    private Spinner spinner;
    private Button button;
    private EditText et_title;
    private EditText et_content;
    private Button bt_time;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return true;
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
            if(action.equals("com.example.Close_TaskPublish"))
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
        setContentView(R.layout.activity_task_publish);

        //关闭社区页面广播
        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_TaskPublish");
        registerReceiver(close_receiver,close_intentFilter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task_publish);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

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

        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        bt_time = findViewById(R.id.btn_publish_clock);
        //请求获取焦点
        et_content.requestFocus();
        //清除焦点
        et_content.clearFocus();
        //改变默认的单行模式
        et_content.setSingleLine(false);
        //水平滚动设置为False
        et_content.setHorizontallyScrolling(false);

        bt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForTime();
            }
        });




//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_task_publish);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        button = (Button) findViewById(R.id.btn_publish_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = et_title.getText().toString().trim();
                content = et_content.getText().toString().trim();
                time = 0;
                if(TextUtils.isEmpty(title)){
                    Toast.makeText(TaskPublish.this,"请输入标题",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(content)){
                    Toast.makeText(TaskPublish.this,"请输入任务内容",Toast.LENGTH_SHORT).show();
//                }else if(){
//
//                }else if (){
//
//                }else{
//
                }else{
                    dialogShow();
                }
//                Intent intent = new Intent(TaskPublish.this, PublishSuccess.class);
//                startActivity(intent);
            }
        });
    }

    //初始化并弹出对话框方法
    private void showDialogForTime(){
        final View view = LayoutInflater.from(this).inflate(R.layout.task_publish_dialog_time,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

//        final TextView tv = view.findViewById(R.id.tv_click);
//        Button btn_cancel_high_opion = view.findViewById(R.id.btn_cancel_high_opion);
        Button btn_agree_high_opion = view.findViewById(R.id.btn_agree_high_opion);

//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        final PickerView pv_month = view.findViewById(R.id.pv_month);
        final PickerView pv_day = view.findViewById(R.id.pv_day);
        final PickerView pv_hour = view.findViewById(R.id.pv_hour);


        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        String getMonth;
        String getDay;
        String getHour;

        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH)+1;
        int month = 11;
        System.out.println("year:"+year+"month:"+month);
        //是否日期底线到明年
        boolean nextYear;
        //今年是否为闰年
        boolean leapThis = judge(year);
        //明年是否为闰年
        boolean leapNext = judge(year + 1);

        final List<String> time_month = new ArrayList<String>();
        final List<String> time_month_int = new ArrayList<String>();
        if( month >= 7){
            nextYear = true;
            for(int i = month; i <= 12; i++){
                time_month.add( String.valueOf(i));
                time_month_int.add( String.valueOf(i));
            }
            for(int i = 1; i <= month - 6; i++){
                time_month.add("明年" + i );
                time_month_int.add( String.valueOf(i));
            }
        }else{
            nextYear = false;
            for(int i = month; i <= month + 7; i++){
                time_month.add( String.valueOf(i));
            }
        }

        int getMonthNum = pv_month.getmCurrentSelected();
        System.out.println("getmonthnum"+getMonthNum);
        getMonth = time_month.get(getMonthNum);
        List<String> time_day = setDayTime(month,getMonthNum,leapThis,leapNext);

        int getDayNum = pv_day.getmCurrentSelected();
        getDay = time_day.get(getDayNum);
        final List<String> time_hour = new ArrayList<String>();
        for (int i = 0; i <= 23; i++){
            time_hour.add(String.valueOf(i));
        }

        pv_month.setData(time_month);
        pv_day.setData(time_day);
        pv_hour.setData(time_hour);

        pv_month.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
//                int getMonthNum = ;
//                getMonth = ;
//                getMonth = pv_month.getmCurrentSelected();
//                System.out.println("Coco"+text);
//                char[] chrs = text;
//                String[] strs = text;
//                System.out.println("Coco"+pv_month.getmCurrentSelected());
                String s = text.toString().replace("明","9").replace("年","9");
//                time_month_int.get(pv_month.getmCurrentSelected())
                List<String> time_day = setDayTime(month, Integer.parseInt(s),leapThis,leapNext);
                if(time_day.size()!=0){
                    pv_day.setData(time_day);
                }
//                else{
//                    System.out.println("Coco fff");
//                }
            }
        });

        pv_day.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
            }
        });

        pv_hour.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
            }
        });

//        btn_cancel_high_opion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

        btn_agree_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int s = pv_month.getmCurrentSelected();
//                if()
//                time_month =
                dialog.dismiss();
            }
        });

        dialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)), LinearLayout.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setLayout((ScreenUtils.getScreenHeight(this)), LinearLayout.LayoutParams.MATCH_PARENT);
//        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 自定义布局
     * setView()只会覆盖AlertDialog的Title与Button之间的那部分，而setContentView()则会覆盖全部，
     * setContentView()必须放在show()的后面
     */
    private void dialogShow() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(TaskPublish.this);
        LayoutInflater inflater = LayoutInflater.from(TaskPublish.this);

        View v = inflater.inflate(R.layout.dialog_task_publish, null);
        /**
         * setText
         */
        TextView tv_title = v.findViewById(R.id.dialog_publish_tv_title);
        TextView tv_category = v.findViewById(R.id.dialog_publish_tv_category);
        TextView tv_time = v.findViewById(R.id.dialog_publish_tv_time);
        TextView tv_content = v.findViewById(R.id.dialog_publish_tv_content);
        TextView tv_money = v.findViewById(R.id.dialog_publish_tv_money);

        ///money控件用法不会，待补充
//        String s_money = (String) spinner.getSelectedItem();
        money = 10;

        tv_title.setText(title);
        tv_content.setText(content);
        tv_money.setText(String.valueOf(money));
        tv_time.setText(String.valueOf(time));
        tv_category.setText(type);

//        publish();

        /**
         * setButton
         */
        Button btn_confirm = v.findViewById(R.id.dialog_publish_btn_confirm);
        Button btn_change = v.findViewById(R.id.dialog_publish_btn_change);
        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置背景为透明才使得背景不可见，否则有白边
        dialog.setCanceledOnTouchOutside(false);//触摸外部消失
        dialog.getWindow().setGravity(Gravity.CENTER);
        //自定义布局应该在这里添加，要在dialog.show()的后面
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                Toast.makeText(TaskPublish.this, "ok", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(TaskPublish.this, PublishSuccess.class);
//                startActivity(intent);
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

    }

    private boolean judge(int year){
        boolean rst;

        if(year % 4 != 0){
            rst = false;
        }else{
            if(year % 100 != 0){
                rst = true;
            }else{
                if(year % 400 != 0){
                    rst = false;
                }else{
                    rst = true;
                }
            }
        }

        return rst;
    }

    List setDayTime(int month,int getMonth ,boolean leapThis ,boolean leapNext){
        System.out.println("month"+month+"get:"+getMonth);
        List<String> time_day = new ArrayList<String>();
        boolean leap;
        if(getMonth >= 992){
            leap = leapNext;
        }else{
            leap = leapThis;
        }
        if(getMonth >= 900)
            getMonth -= 990;
        switch ((getMonth)%12){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 0:
                for(int i = 1 ;i <= 31;i ++){
                    time_day.add(String.valueOf(i));
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                for(int i = 1;i <= 30;i ++){
                    time_day.add(String.valueOf(i));
                }
                break;
            case 2:
                if(leap == true){
                    for(int i = 1;i <= 29;i ++){
                        time_day.add(String.valueOf(i));
                    }
                }else{
                    for(int i = 1;i <= 28;i ++){
                        time_day.add(String.valueOf(i));
                    }
                }
                break;
            default:
                break;
        }
        return time_day;
    }

//    List setDayTime(int month,int getMonthNum ,boolean leapThis ,boolean leapNext){
//        System.out.println("month"+month+"get:"+getMonthNum);
//        List<String> time_day = new ArrayList<String>();
//        boolean leap;
//        if(month + getMonthNum >= 14){
//            leap = leapNext;
//        }else{
//            leap = leapThis;
//        }
//        switch ((month + getMonthNum)%12){
//            case 1:
//            case 3:
//            case 5:
//            case 7:
//            case 8:
//            case 10:
//            case 0:
//                for(int i = 1 ;i <= 31;i ++){
//                    time_day.add(String.valueOf(i));
//                }
//                break;
//            case 4:
//            case 6:
//            case 9:
//            case 11:
//                for(int i = 1;i <= 30;i ++){
//                    time_day.add(String.valueOf(i));
//                }
//                break;
//            case 2:
//                if(leap == true){
//                    for(int i = 1;i <= 29;i ++){
//                        time_day.add(String.valueOf(i));
//                    }
//                }else{
//                    for(int i = 1;i <= 28;i ++){
//                        time_day.add(String.valueOf(i));
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//        return time_day;
//    }

//    private void publish(){
////        subId	是	int	发布人id
////        subTime	是	date	发布时间
////        endTime	否	date	截止时间
////        callTitle	是	string	标题
////        callDesp	是	string	描述
////        callMoney	否	int	金额
////        callNow	是	string	状态
////        recId	否	int	接收人id
////        subName	是	string	发布人昵称
////        recName	否	string	接收人昵称
////        callAddress	否	string	发布人地址
//        HashMap<String, String> paramsMap = new HashMap<>();
//        paramsMap.put("subId",);
//        paramsMap.put("subTime",);
//        paramsMap.put("endTime",);
//        paramsMap.put("callTitle",);
//        paramsMap.put("callDesp",);
//        paramsMap.put("callMoney",);
//        paramsMap.put("callNow",);
//        paramsMap.put("recId",);
//        paramsMap.put("subName",);
//        paramsMap.put("recName",);
//        paramsMap.put("callAddress",);
//        httpUtil.POST("http://www.xinxianquan.xyz:8080/zhaqsq/call/save",);
//    }


}
