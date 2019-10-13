package com.soft.zkrn.weilin_application.Activities.Task;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.Activities.User.UserInformation_Mainpage;
import com.soft.zkrn.weilin_application.GsonClass.TaskData_PublishedOrReceived;
import com.soft.zkrn.weilin_application.GsonClass.UserInformationData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.Widget.FragmentJudgement;
import com.soft.zkrn.weilin_application.Widget.FragmentSwitchTool;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


public class TaskCenter extends AppCompatActivity {

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();

    private static String publish_url = "http://119.23.190.83:8080/zhaqsq/call/getBysub";
    private static String complete_url = "http://119.23.190.83:8080/zhaqsq/call/getByrec";

    //用户ID
    private int uid = 0;
    //判断是否需要载入数据
    private boolean ifRefreshedWholeFragment = false;
    private boolean ifRefreshedPublishFragment = false;
    private boolean ifRefreshedReceiveFragment = false;
    private boolean ifRefreshedCommentFragment = false;
    //保存用户的任务数据
    private TaskData_PublishedOrReceived publishedContent = null;
    private TaskData_PublishedOrReceived receivedContent = null;

    private Task_CenterPublish task_centerPublish;
    private Task_CenterWhole task_centerWhole;
    private Task_CenterComment task_centerComment;
    private Task_CenterComplete task_centerComplete;

    private TextView tv_whole;
    private TextView tv_publish;
    private TextView tv_complete;
    private TextView tv_comment;

    private LinearLayout ll_whole;
    private LinearLayout ll_publish;
    private LinearLayout ll_complete;
    private LinearLayout ll_comment;

    private FragmentSwitchTool tool;
    private DrawerLayout mDrawerLayout;

//    private Task[] tasks = new Task[10];

    private static final int SUCCESS_PUBLISH = 1;
    private static final int SUCCESS_RECEIVE = 2;
    private static final int FAIL = 3;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case SUCCESS_PUBLISH:
                publishedContent = (TaskData_PublishedOrReceived)msg.obj;
                callTaskOfReceive();
                break;
            case SUCCESS_RECEIVE:
//                System.out.println("receive.size="+receivedContent.getExtend().getCalls().size());
                receivedContent = (TaskData_PublishedOrReceived)msg.obj;
//                Task_CenterWhole cw = (Task_CenterWhole)tool.getFragment(ll_whole);
//                cw.dealWithWholeData(publishedContent,receivedContent);
                System.out.println("receive.size="+receivedContent.getExtend().getCalls().size());
//                tool.changeTag(ll_whole);
                System.out.println("RECEIVE");

                tool.changeTag(ll_complete);
                tool.changeTag(ll_comment);
                tool.changeTag(ll_publish);
                tool.changeTag(ll_whole);

                task_centerWhole = (Task_CenterWhole)tool.getFragment(ll_whole);
                task_centerPublish = (Task_CenterPublish)tool.getFragment(ll_publish);
                task_centerComplete = (Task_CenterComplete)tool.getFragment(ll_complete);
                task_centerComment = (Task_CenterComment)tool.getFragment(ll_comment);

//        tool.changeTag(ll_whole);
//        tool.changeTag(ll_complete);
//        tool.changeTag(ll_comment);
//        tool.changeTag(ll_publish);

                if(task_centerWhole == null)System.out.println("no_llwh");
                if(task_centerPublish == null)System.out.println("no_llpb");
                if(task_centerComplete == null)System.out.println("no_llcl");
                if(task_centerComment == null)System.out.println("no_llcm");

                setToolInterface();

                tool.onClick(ll_whole);
                break;
            case FAIL:
                break;
            default:
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_task_center);

        tv_whole = findViewById(R.id.tv_center_whole);
        tv_publish = findViewById(R.id.tv_center_publish);
        tv_comment = findViewById(R.id.tv_center_comment);
        tv_complete = findViewById(R.id.tv_center_complete);

        ll_whole = findViewById(R.id.ll_center_whole);
        ll_publish = findViewById(R.id.ll_center_publish);
        ll_complete = findViewById(R.id.ll_center_complete);
        ll_comment = findViewById(R.id.ll_center_comment);

        uid = readPsw_Int("userID");

        tool = new FragmentSwitchTool(getFragmentManager(), R.id.flContainer);
        tool.setClickableViews(ll_whole, ll_publish, ll_complete ,ll_comment);
        tool
            .addSelectedViews(new View[]{tv_whole})
            .addSelectedViews(new View[]{tv_publish})
            .addSelectedViews(new View[]{tv_complete})
            .addSelectedViews(new View[]{tv_comment});

        tool.setFragments(
            Task_CenterWhole.class,
            Task_CenterPublish.class,
            Task_CenterComplete.class,
            Task_CenterComment.class
        );




//        tool.changeTag(ll_whole);
//        tool.changeTag(ll_complete);
//        tool.changeTag(ll_comment);
//        tool.changeTag(ll_publish);
        tool.changeTag(ll_whole);
        tool.changeTag(ll_complete);
        tool.changeTag(ll_comment);
        tool.changeTag(ll_publish);

//        if(tool.getFragment(ll_whole) == null)System.out.println("no_ll");
//        task_centerWhole = (Task_CenterWhole)tool.getFragment(ll_whole);
//        task_centerPublish = (Task_CenterPublish)tool.getFragment(ll_publish);
//        task_centerComplete = (Task_CenterComplete)tool.getFragment(ll_complete);
//        task_centerComment = (Task_CenterComment)tool.getFragment(ll_comment);

//        tool.changeTag(ll_whole);
//        tool.changeTag(ll_complete);
//        tool.changeTag(ll_comment);
//        tool.changeTag(ll_publish);

//        if(task_centerWhole == null)System.out.println("no_llwh");
//        if(task_centerPublish == null)System.out.println("no_llpb");
//        if(task_centerComplete == null)System.out.println("no_llcl");
//        if(task_centerComment == null)System.out.println("no_llcm");
        callTaskOfPublish();
    }

    private void setToolInterface(){
        tool.judgement = new FragmentJudgement[]{
                new FragmentJudgement() {
                    @Override
                    public void judge() {
//                    if(tool.getFragment(ll_whole) == null)System.out.println("no_llwh");
                        if(ifRefreshedWholeFragment == false){
                            ifRefreshedWholeFragment = true;
                            taskOfWhole();
                        }
                    }
                },
                new FragmentJudgement() {
                    @Override
                    public void judge() {
//                    if(tool.getFragment(ll_publish) == null)System.out.println("no_llpb");
                        if(ifRefreshedPublishFragment == false){
                            ifRefreshedPublishFragment = true;
                            taskOfPublish();
                        }
                    }
                },
                new FragmentJudgement() {
                    @Override
                    public void judge() {
//                    if(tool.getFragment(ll_complete) == null)System.out.println("no_llcl");
                        if(ifRefreshedReceiveFragment == false){
                            ifRefreshedReceiveFragment = true;
                            System.out.println("receive.size="+receivedContent.getExtend().getCalls().size());
                            taskOfReceive();
                        }
                    }
                },
                new FragmentJudgement() {
                    @Override
                    public void judge() {
//                    if(tool.getFragment(ll_comment) == null)System.out.println("no_llcm");
                    }
                }
        };
    }

    private void changeifRefreshedReceiveFragment(boolean ifFreshed){
        ifRefreshedReceiveFragment = ifFreshed;
    }

    private void changeifRefreshedPublishFragment(boolean ifFreshed){
        ifRefreshedPublishFragment = ifFreshed;
    }

    /**
     * 获得数据库bool类型
     */
    private boolean readPsw_Boolean(String s) {

        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        return sp.getBoolean(s,false);
    }

    /**
     * 获得数据库int类型
     */
    private int readPsw_Int(String s) {
        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        return sp.getInt(s,0);
    }

    /**
     * 载入whole碎片的数据
     */
    public void taskOfWhole(){
//        callTaskOfPublish();
        task_centerWhole.dealWithWholeData(publishedContent,receivedContent);
    }

    /**
     * 载入complete碎片的数据
     */
    public void taskOfReceive(){
        System.out.println("content.size=" + String.valueOf(receivedContent.getExtend().getCalls().size()));
//        Task_CenterComplete tc = (Task_CenterComplete)tool.getFragment(ll_complete);
        task_centerComplete.dealWithData(receivedContent);
    }

    /**
     *载入publish碎片的数据
     */
    private void taskOfPublish(){
//        Task_CenterPublish tp = (Task_CenterPublish)tool.getFragment(ll_publish);
        task_centerPublish.dealWithData(publishedContent);
    }

    /**
     * 载入comment碎片的数据
     */
    private void taskOfComment(TaskData_PublishedOrReceived data){
    }

    /**
     * 获得发送任务的数据
     */
    private void callTaskOfPublish(){
        Message msg = Message.obtain();
        httpUtil.GET(TaskCenter.this,publish_url, "subId", String.valueOf(uid), new CallBack_Get() {
            @Override
            public void onFinish(String response) {
//                System.out.println(response);
                gsonUtil.translateJson(response, TaskData_PublishedOrReceived.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        TaskData_PublishedOrReceived data = (TaskData_PublishedOrReceived) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS_PUBLISH;
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
                Message msg = Message.obtain();
                msg.what = FAIL;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 获得接收任务的数据
     */
    private void callTaskOfReceive(){
        Message msg = Message.obtain();
        httpUtil.GET(TaskCenter.this,complete_url, "recId", String.valueOf(uid), new CallBack_Get() {
            @Override
            public void onFinish(String response) {
                System.out.println(response);
                gsonUtil.translateJson(response, TaskData_PublishedOrReceived.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        TaskData_PublishedOrReceived data = (TaskData_PublishedOrReceived) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS_RECEIVE;
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
                Message msg = Message.obtain();
                msg.what = FAIL;
                handler.sendMessage(msg);
            }
        });
    }


}
