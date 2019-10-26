package com.soft.zkrn.weilin_application.Activities.Task;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soft.zkrn.weilin_application.Adapter.TaskAdapter;
import com.soft.zkrn.weilin_application.Class.Task;
import com.soft.zkrn.weilin_application.GsonClass.TaskData;
import com.soft.zkrn.weilin_application.GsonClass.TaskData_PublishedOrReceived;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class Task_CenterPublish extends Fragment {

    private int tasksNum = 0;
    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter adapter;
    private RecyclerView recyclerView;

    private int id;
    private int point;

    private static final int GET = 1;
    private static final int MAXSIZE = 10;

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case GET:
//                    System.out.println("GET");
//                    displayRecyclerView();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_center_frag_publish,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView)getActivity().findViewById(R.id.rv_task_frag_publish);
    }

    public void displayRecyclerView(){

//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList);
//                adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new TaskAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position, int callId, String title, String desp, int money, long time,String state) {
                Intent intent = new Intent(getActivity(),TaskIntroduction_Publish.class);
                intent.putExtra("position",position);
                intent.putExtra("callId",callId);
                intent.putExtra("title",title);
                intent.putExtra("desp",desp);
                intent.putExtra("money",money);
                intent.putExtra("time",time);
                intent.putExtra("state",state);
                intent.putExtra("uid",id);
                intent.putExtra("upoint",point);
                getActivity().startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
//            }
//        });

    }

    public void dealWithData(TaskData_PublishedOrReceived pr){
//        recyclerView = null;
        tasksNum = 0;
        adapter = null;
        List<TaskData_PublishedOrReceived.Extend.Calls> list = pr.getExtend().getCalls();
        System.out.println("size = " + list.size());
        taskList.clear();
        for(int i = 0; i < pr.getExtend().getCalls().size(); i ++){
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

        displayRecyclerView();
    }

    public void getInformation(int uid,int upoint){
        id = uid;
        point = upoint;
    }

}
