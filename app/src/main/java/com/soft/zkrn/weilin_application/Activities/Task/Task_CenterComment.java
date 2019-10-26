package com.soft.zkrn.weilin_application.Activities.Task;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soft.zkrn.weilin_application.Adapter.TaskAdapter;
import com.soft.zkrn.weilin_application.Class.Task;
import com.soft.zkrn.weilin_application.GsonClass.TaskData_PublishedOrReceived;
import com.soft.zkrn.weilin_application.R;

import java.util.ArrayList;
import java.util.List;

public class Task_CenterComment extends Fragment {

    private int tasksNum = 0;
    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter adapter;
    private RecyclerView recyclerView;

    private List<TaskData_PublishedOrReceived.Extend.Calls> dataList;
    private static final int MAXSIZE = 10;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_center_frag_comment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.rv_task_frag_comment);
    }

    /**
     * 处理来自activity的数据
     */
    public void dealWithWholeData(TaskData_PublishedOrReceived dataPublish,TaskData_PublishedOrReceived dataReceive){
        tasksNum = 0;
        adapter = null;
//        recyclerView = null;
        taskList.clear();
        System.out.println("deal");
        for(int i = 0; i < dataPublish.getExtend().getCalls().size(); i ++){
            if((dataPublish.getExtend().getCalls().get(i).getCallNow()).equals("3") ||(dataPublish.getExtend().getCalls().get(i).getCallNow()).equals("4")){
                Task tk = new Task(
                        dataPublish.getExtend().getCalls().get(i).getCallId(),
                        dataPublish.getExtend().getCalls().get(i).getSubId(),
                        dataPublish.getExtend().getCalls().get(i).getSubTime(),
                        dataPublish.getExtend().getCalls().get(i).getEndTime(),
                        dataPublish.getExtend().getCalls().get(i).getCallTitle(),
                        dataPublish.getExtend().getCalls().get(i).getCallDesp(),
                        dataPublish.getExtend().getCalls().get(i).getCallMoney(),
                        dataPublish.getExtend().getCalls().get(i).getCallNow(),
                        dataPublish.getExtend().getCalls().get(i).getRecId(),
                        dataPublish.getExtend().getCalls().get(i).getSubName(),
                        dataPublish.getExtend().getCalls().get(i).getRecName(),
                        dataPublish.getExtend().getCalls().get(i).getCallAddress()
                );
                tasksNum ++;
                taskList.add(tk);
                if(tasksNum >= MAXSIZE)
                    break;

            }
        }
        for(int i = 0; i < dataReceive.getExtend().getCalls().size(); i ++){
            if((dataReceive.getExtend().getCalls().get(i).getCallNow()).equals("3") || (dataReceive.getExtend().getCalls().get(i).getCallNow()).equals("4")){
                Task tk = new Task(
                        dataReceive.getExtend().getCalls().get(i).getCallId(),
                        dataReceive.getExtend().getCalls().get(i).getSubId(),
                        dataReceive.getExtend().getCalls().get(i).getSubTime(),
                        dataReceive.getExtend().getCalls().get(i).getEndTime(),
                        dataReceive.getExtend().getCalls().get(i).getCallTitle(),
                        dataReceive.getExtend().getCalls().get(i).getCallDesp(),
                        dataReceive.getExtend().getCalls().get(i).getCallMoney(),
                        dataReceive.getExtend().getCalls().get(i).getCallNow(),
                        dataReceive.getExtend().getCalls().get(i).getRecId(),
                        dataReceive.getExtend().getCalls().get(i).getSubName(),
                        dataReceive.getExtend().getCalls().get(i).getRecName(),
                        dataReceive.getExtend().getCalls().get(i).getCallAddress()
                );
                tasksNum ++;
                taskList.add(tk);
                if(tasksNum >= MAXSIZE)
                    break;
            }
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);
        if(adapter != null) {
            adapter.setOnItemClickLitener(new TaskAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position,int callId,String title,String desp,int money,long time,String state) {
                    Intent intent = new Intent(getActivity(),TaskIntroduction_Receive.class);
                    intent.putExtra("position",position);
                    intent.putExtra("callId",callId);
                    intent.putExtra("title",title);
                    intent.putExtra("desp",desp);
                    intent.putExtra("money",money);
                    intent.putExtra("time",time);
                    intent.putExtra("state",state);
                    getActivity().startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
}
