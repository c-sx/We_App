package com.soft.zkrn.weilin_application.Activities.Task;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.Activities.Community.Community_Introduction;
import com.soft.zkrn.weilin_application.Adapter.TaskAdapter;
import com.soft.zkrn.weilin_application.Class.Task;
import com.soft.zkrn.weilin_application.GsonClass.TaskData;
import com.soft.zkrn.weilin_application.GsonClass.TaskData_PublishedOrReceived;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Task_CenterWhole extends Fragment {

    private int tasksNum = 0;
    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter adapter;
    private RecyclerView recyclerView;

    private List<TaskData_PublishedOrReceived.Extend.Calls> dataList;
    private static final int MAXSIZE = 10;

    private String url = "http://119.23.190.83:8080/zhaqsq/call/calls";

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case FORPAGE:
////                    String s = "";
////                    Log.d(s,"First_Handler");
//                    TaskData dataForPage = (TaskData)msg.obj;
//                    TaskData.Extend.Pageinfo pageinfo = dataForPage.getextend().getpageinfo();
//                    pageNum = pageinfo.getpageNum();
//                    pages = pageinfo.getpages();
//                    pageSize = pageinfo.getpageSize();
//                    size = pageinfo.getsize();
//                    ifOK = true;
//                    initDataSecond();
//                    break;
//                case ADD:
//                    TaskData taskData = (TaskData) msg.obj;
//                    List<TaskData.Extend.Pageinfo.TaskList> list = taskData.getextend().getpageinfo().getlist();
//                    for(int i = 0; i < taskData.getextend().getpageinfo().getsize(); i ++){
//                    Task tk = new Task(
//                            list.get(i).getcallId(),
//                            list.get(i).getsubId(),
//                            list.get(i).getsubTime(),
//                            list.get(i).getendTime(),
//                            list.get(i).getcallTitle(),
//                            list.get(i).getcallDesp(),
//                            list.get(i).getcallMoney(),
//                            list.get(i).getcallNow(),
//                            list.get(i).getrecId(),
//                            list.get(i).getsubName(),
//                            list.get(i).getrecName(),
//                            list.get(i).getCallAddress());
//                        tasksNum ++;
//                        taskList.add(tk);
//                        if(tasksNum >= MAXSIZE)
//                            break;
//                    }
//                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
//                    recyclerView.setLayoutManager(layoutManager);
//                    adapter = new TaskAdapter(taskList);
//                    recyclerView.setAdapter(adapter);
//
//                    if(adapter != null) {
//                        adapter.setOnItemClickLitener(new TaskAdapter.OnItemClickLitener() {
//                            @Override
//                            public void onItemClick(View view, int position,int callId,String title,String desp,int money,long time) {
////                                Intent intent = new Intent(getActivity(), Community_Introduction.class);
////                                intent.putExtra("extra_id",callId);
////                                getActivity().startActivity(intent);
//
//                            }
//                        });
//                        recyclerView.setAdapter(adapter);
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

//    /**
//     * 录入卡片
//     */
//    private void initDataFirst(){
//        httpUtil.GET(getActivity(),url, new CallBack_Get() {
//            @Override
//            public void onFinish(String response) {
//                gsonUtil.translateJson(response, TaskData.class, new CallBackGson() {
//                    @Override
//                    public void onSuccess(Object obj) {
//                        Message msg = Message.obtain();
//                        msg.what = FORPAGE;
//                        msg.obj = (TaskData)obj;
//                        TaskData dataForPage = (TaskData)obj;
//                        TaskData.Extend.Pageinfo pageinfo = dataForPage.getextend().getpageinfo();
//                        pageNum = pageinfo.getpageNum();
//                        pages = pageinfo.getpages();
//                        pageSize = pageinfo.getpageSize();
//                        size = pageinfo.getsize();
//                        ifOK = true;
//                        System.out.println("获得页数");
//                        handler.sendMessage(msg);
//                    }
//                    @Override
//                    public void onFail(Exception e) {
//                    }
//                });
//            }
//            @Override
//            public void onError(Exception e) {
//            }
//        });
//    }
//
//    private void initDataSecond(){
//        System.out.println("进入了initDataSecond");
//        for(int i = 0; i < pages; i ++){
//            httpUtil.GET(getActivity(),url, "pn",String.valueOf(i), new CallBack_Get() {
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
//
//    private void newTask(String res){
//        gsonUtil.translateJson(res, TaskData.class, new CallBackGson() {
//            @Override
//            public void onSuccess(Object obj) {
//                TaskData taskData = (TaskData) obj;
//                if(taskData.getcode() == 100){
//                    //正确返回数据
//                    System.out.println("newTask OK");
//
//                    Message msg = Message.obtain();
//                    msg.what = ADD;
//                    msg.obj = taskData;
//                    handler.sendMessage(msg);
//                }//否则不录入数据
//            }
//
//            @Override
//            public void onFail(Exception e) { }
//        });
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_center_frag_whole,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.rv_task_frag_whole);
//        Toast.makeText(getActivity(),"whole",Toast.LENGTH_SHORT).show();
    }

    /**
     * 处理来自activity的数据
     */
    public void dealWithWholeData(TaskData_PublishedOrReceived dataPublish,TaskData_PublishedOrReceived dataReceive){
        dataList = dataReceive.getExtend().getCalls() ;
        dataList.addAll(dataPublish.getExtend().getCalls());

        System.out.println("deal");
        for(int i = 0; i < dataList.size(); i ++){
            Task tk = new Task(
                dataList.get(i).getCallId(),
                dataList.get(i).getSubId(),
                dataList.get(i).getSubTime(),
                dataList.get(i).getEndTime(),
                dataList.get(i).getCallTitle(),
                dataList.get(i).getCallDesp(),
                dataList.get(i).getCallMoney(),
                dataList.get(i).getCallNow(),
                dataList.get(i).getRecId(),
                dataList.get(i).getSubName(),
                dataList.get(i).getRecName(),
                dataList.get(i).getCallAddress()
            );
            tasksNum ++;
            taskList.add(tk);
            if(tasksNum >= MAXSIZE)
                break;
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);
//        if(adapter != null) {
//            adapter.setOnItemClickLitener(new TaskAdapter.OnItemClickLitener() {
//                @Override
//                public void onItemClick(View view, int position,int callId,String title,String desp,int money,long time) {
////                    Intent intent = new Intent(getActivity(), Community_Introduction.class);
////                    intent.putExtra("extra_id",callId);
////                    getActivity().startActivity(intent);
//                }
//            });
//            recyclerView.setAdapter(adapter);
//        }
    }
}
