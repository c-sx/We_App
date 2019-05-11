package com.soft.zkrn.weilin_application.Activities.Task;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.soft.zkrn.weilin_application.GsonClass.StateData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.Widget.PickerView;
import com.soft.zkrn.weilin_application.Widget.ScreenUtils;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Post;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private long SetTime = 0;
    private long ThisTime = 0;
    private String getSetTime = "";
    private String getThisTime = "";
    private int YearTime;
    private int MonthTime;
    private int DayTime;
    private int HourTime;

    private boolean ifTime = false;

    private static final int SUCCESS = 1;
    private static final int FAIL = 2;
    private static final int CHANGE_MONTH = 3;
    private static final int CHANGE_DAY = 4;
    private static final int CHANGE_HOUR = 5;
    private static final int FAIL200 = 6;
    private static final int FAILGSON = 7;
    private static final int FAILHTTP = 8;
//    private

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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获取数据 //
            switch (msg.what){
                case SUCCESS:
                    Toast.makeText(TaskPublish.this,"任务发布成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case FAIL:
                    Toast.makeText(TaskPublish.this,"任务发布失败，请重试", Toast.LENGTH_SHORT).show();
                    break;
                case CHANGE_MONTH:
                    break;
                case CHANGE_DAY:
                    break;
                case CHANGE_HOUR:
                    break;
                case FAIL200:
                    Toast.makeText(TaskPublish.this,"200",Toast.LENGTH_SHORT).show();
                    break;
                case FAILGSON:
                    Toast.makeText(TaskPublish.this,"gson",Toast.LENGTH_SHORT).show();
                    break;
                case FAILHTTP:
                    Toast.makeText(TaskPublish.this,"http",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
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

        spinner = findViewById(R.id.spi_publish);
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        bt_time = findViewById(R.id.btn_publish_clock);
        button = findViewById(R.id.btn_publish_confirm);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = et_title.getText().toString().trim();
                content = et_content.getText().toString().trim();
//                SetTime = 0;
                if(TextUtils.isEmpty(title)){
                    Toast.makeText(TaskPublish.this,"请输入标题",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(content)){
                    Toast.makeText(TaskPublish.this,"请输入任务内容",Toast.LENGTH_SHORT).show();
                }else if (ifTime == false){
                    Toast.makeText(TaskPublish.this,"请选择截止时间",Toast.LENGTH_SHORT).show();
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
        ifTime = true;
        final View view = LayoutInflater.from(this).inflate(R.layout.task_publish_dialog_time,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        Button btn_agree_high_opion = view.findViewById(R.id.btn_agree_high_opion);

        final PickerView pv_month = view.findViewById(R.id.pv_month);
        final PickerView pv_day = view.findViewById(R.id.pv_day);
        final PickerView pv_hour = view.findViewById(R.id.pv_hour);

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        //获得当前时间
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        System.out.println("year:" + cal.get(Calendar.YEAR));
        System.out.println("month:" + cal.get(Calendar.MONTH));
        System.out.println("day:" + cal.get(Calendar.DATE));
        System.out.println("hour:" + cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("minute:" + cal.get(Calendar.MINUTE));

        //今年是否为闰年
        boolean leapThis = judge(year);
        //明年是否为闰年
        boolean leapNext = judge(year + 1);

        /*
         *设定月份栏
         */
        final List<String> time_month = new ArrayList<String>();
        if( month >= 7){
            for(int i = month; i <= 12; i++){
                time_month.add( String.valueOf(i));
            }
            for(int i = 1; i <= month - 6; i++){
                time_month.add("明年" + i );
            }
        }else{
            for(int i = month; i <= month + 7; i++){
                time_month.add( String.valueOf(i));
            }
        }
        pv_month.setData(time_month);

        /*
         *设定日期栏
         */
        String getMonth = pv_month.getText();
        if(getMonth.length() >= 3)
            getMonth = getMonth.toString().replace("明","9").replace("年","9");
        System.out.println("getMonth:" + getMonth);
        final List<String> time_day = setDayTime(month, Integer.parseInt(getMonth), leapThis, leapNext,day);
        pv_day.setData(time_day);

        /*
         *设定小时栏
         */
        final List<String> time_hour = setHourTime(day,Integer.valueOf(pv_day.getText()),month,Integer.valueOf(getMonth),hour);
        pv_hour.setData(time_hour);

        final int[] i_month = new int[1];
        final int[] i_day = new int[1];
        final int[] i_hour = new int[1];

        pv_month.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                String s1;
                if(text.length() >= 3)
                    s1 = text.toString().replace("明","9").replace("年","9");
                else
                    s1 = text;

                List<String> time_day = setDayTime(month, Integer.parseInt(s1),leapThis,leapNext,day);
                if(time_day.size()!=0){
                    pv_day.setData(time_day);
                }
            }
        });

        pv_day.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                String getMonth = pv_month.getText();
                if(getMonth.length() == 1){
                    List<String> time_hour = setHourTime(day, Integer.valueOf(text), month, Integer.valueOf(getMonth), hour);
                    pv_hour.setData(time_hour);
                }
            }
        });

        pv_hour.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
            }
        });

        btn_agree_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i_month[0] = Integer.valueOf(pv_month.getText());
                i_day[0] = Integer.valueOf(pv_day.getText());
                i_hour[0] = Integer.valueOf(pv_hour.getText());

                if(i_month[0] >= 900){
                    YearTime = year + 1;
                    i_month[0] -= 990;
                }else{
                    YearTime = year;
                }
                MonthTime = i_month[0];
                DayTime = i_day[0];
                HourTime = i_hour[0];

                getSetTime = getTime(YearTime,MonthTime,DayTime,HourTime,0,0);
                try {
                    SetTime = dateToStamp(getSetTime);
                } catch (ParseException e) {
                    SetTime = 0;
                }
                System.out.println("getSetTime" + getSetTime);
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

        System.out.println("read:" + readPsw_Int("userID"));

        TextView tv_title = v.findViewById(R.id.dialog_publish_tv_title);
        TextView tv_category = v.findViewById(R.id.dialog_publish_tv_category);
        TextView tv_time = v.findViewById(R.id.dialog_publish_tv_time);
        TextView tv_content = v.findViewById(R.id.dialog_publish_tv_content);
        TextView tv_money = v.findViewById(R.id.dialog_publish_tv_money);

        String s_money = (String) spinner.getSelectedItem();
        if(s_money.equals("积分"))
            s_money = "0";
        money = Integer.valueOf(s_money);

        tv_title.setText(title);
        tv_content.setText(content);
        tv_money.setText(String.valueOf(money));
        tv_time.setText(String.valueOf(getSetTime));
        switch (type){
            case "receive":
                tv_category.setText("代收");
                break;
            case "send":
                tv_category.setText("代送");
                break;
            case "borrow":
                tv_category.setText("借物");
                break;
            case "other":
                tv_category.setText("其他");
                break;
            default:
                break;
        }


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
                publish();
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

    private List setHourTime(int today,int getDay,int month,int getMonth,int now){
        List<String> time_hour = new ArrayList<String>();
        if(today == getDay && month == getMonth){
            for(int i = now + 1 ;i <= 23;i ++){
                time_hour.add(String.valueOf(i));
            }
        }else{
            for(int i = 0 ;i <= 23;i ++){
                time_hour.add(String.valueOf(i));
            }
        }
        return time_hour;
    }

    private List setDayTime(int month,int getMonth ,boolean leapThis ,boolean leapNext,int today){
        System.out.println("month"+month+"get:"+getMonth);
        List<String> time_day = new ArrayList<String>();
        boolean leap;
        int start;
        if(getMonth >= 992){
            leap = leapNext;
        }else{
            leap = leapThis;
        }
        if(getMonth == month){
            start = today ;
        }else{
            start = 1;
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
                for(int i = start ;i <= 31;i ++){
                    time_day.add(String.valueOf(i));
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                for(int i = start;i <= 30;i ++){
                    time_day.add(String.valueOf(i));
                }
                break;
            case 2:
                if(leap == true){
                    for(int i = start;i <= 29;i ++){
                        time_day.add(String.valueOf(i));
                    }
                }else{
                    for(int i = start;i <= 28;i ++){
                        time_day.add(String.valueOf(i));
                    }
                }
                break;
            default:
                break;
        }
        return time_day;
    }

    private int readPsw_Int(String s) {
        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        return sp.getInt(s,0);
    }

    private String readPsw_String(String s) {
        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        return sp.getString(s,"");
    }

    public long dateToStamp(String s) throws ParseException {
//        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = simpleDateFormat.parse(s);
        System.out.println("date:" + date);
        long ts = date.getTime();
        System.out.println("原ts:" + date.getTime());
//        res = String.valueOf(ts);
        return ts;
    }

    //post与后端未能对接
    private void publish(){
//        subId	是	int	发布人id
//        subTime	是	date	发布时间
//        endTime	否	date	截止时间
//        callTitle	是	string	标题
//        callDesp	是	string	描述
//        callMoney	否	int	金额
//        callNow	是	string	状态
//        recId	否	int	接收人id
//        subName	是	string	发布人昵称
//        recName	否	string	接收人昵称
//        callAddress	否	string	发布人地址

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        getThisTime = getTime(year,month,day,hour,minute,second);
        try {
            ThisTime = dateToStamp(getThisTime);
        } catch (ParseException e) {
            ThisTime = 0;
        }

//        subId	是	int	发布人id
//        subTime	是	date	发布时间
//        endTime	否	date	截止时间
//        callTitle	是	string	标题
//        callDesp	是	string	描述
//        callMoney	否	int	金额
//        callNow	是	string	状态
//        recId	否	int	接收人id
//        subName	是	string	发布人昵称
//        recName	否	string	接收人昵称
//        callAddress	否	string	发布人地址

        System.out.println("sub:"+ String.valueOf(SetTime) + " end:" + String.valueOf(ThisTime));
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("subId", String.valueOf(readPsw_Int("userID")));
//        paramsMap.put("subTime",String.valueOf(ThisTime));
//        paramsMap.put("endTime",String.valueOf(SetTime));
//        paramsMap.put("subTime","1557600053000");
//        paramsMap.put("endTime","1570819253000");
        paramsMap.put("callTitle",title);
        paramsMap.put("callDesp",content);
        paramsMap.put("callMoney",String.valueOf(money));
        paramsMap.put("callNow","y");
        paramsMap.put("recId","");
        paramsMap.put("subName",readPsw_String("userName"));
        paramsMap.put("recName","");
        paramsMap.put("callAddress","");
////        paramsMap.put("cal
        Message msg = Message.obtain();
        for(int i=0;i<paramsMap.size();i++){
            System.out.println(paramsMap.get(i));
        }
        httpUtil.POST("http://www.xinxianquan.xyz:8080/zhaqsq/call/save", paramsMap, new CallBack_Post() {
            @Override
            public void onFinish(String response) {
                System.out.println("gson:" + response);
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        StateData data = (StateData)obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS;
                            handler.sendMessage(msg);
                        }else{
                            msg.what = FAIL200;
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = FAILGSON;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAILHTTP;
                handler.sendMessage(msg);
            }
        });
    }

    private String getTime(int year,int month,int day,int hour,int minute,int second){
        String s = year + "-";
        s = add(month,s);
        s += "-";
        s = add(day,s);
        s += " ";
        s = add(hour,s);
        s += ":";
        s = add(minute,s);
        s += ":";
        s = add(second,s);
        System.out.println("time为" + s);
        return s;
    }

    private String add(int n ,String s){
        String rst = s;
        if(n <= 9)
            rst += "0" + n;
        else
            rst += n;
        return rst;
    }
}
