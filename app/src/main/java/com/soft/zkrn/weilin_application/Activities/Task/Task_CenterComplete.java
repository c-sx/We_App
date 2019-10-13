package com.soft.zkrn.weilin_application.Activities.Task;

import android.app.Fragment;
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

public class Task_CenterComplete extends Fragment {

    private int tasksNum = 0;
    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter adapter;
    private RecyclerView recyclerView;

    private static final int MAXSIZE = 10;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_center_frag_complete,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView)getActivity().findViewById(R.id.rv_task_frag_complete);
    }

    /**
     * 处理来自activity的数据
     */
    public void dealWithData(TaskData_PublishedOrReceived pr){
        TaskData_PublishedOrReceived taskData = pr;
        List<TaskData_PublishedOrReceived.Extend.Calls> list = taskData.getExtend().getCalls();
        for(int i = 0; i < taskData.getExtend().getCalls().size(); i ++){
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

//        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);

        if(adapter != null) {
            adapter.setOnItemClickLitener(new TaskAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position,int callId,String title,String desp,int money,long time) {
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
}
