package com.soft.zkrn.weilin_application.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soft.zkrn.weilin_application.Class.Task;
import com.soft.zkrn.weilin_application.GsonClass.TaskData;
import com.soft.zkrn.weilin_application.R;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context mContext;
    private List<Task> mTaskList;
    private OnItemClickLitener mOnItemClickLitener;


    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView taskState;
        TextView taskTitle;
        TextView taskDesp;
        TextView taskMoney;
        LinearLayout taskItem;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            taskState = (TextView) view.findViewById(R.id.tv_task_state);
            taskTitle = (TextView) view.findViewById(R.id.tv_task_title);
            taskDesp = (TextView) view.findViewById(R.id.tv_task_description);
            taskMoney = (TextView) view.findViewById(R.id.tv_task_money);
            taskItem = (LinearLayout)view.findViewById(R.id.task_item);
        }
    }

        /*
         * 构造函数
         */
        public TaskAdapter(List<Task> taskList){
            this.mTaskList = taskList;
    //        this.mContext = context;
    //        this.mDatas = mDatas;
        }

        @Override
        public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            if(mContext == null){
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.task_item,parent,false);
            return new TaskAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final TaskAdapter.ViewHolder holder, final int position){//////

            final Task task = mTaskList.get(position);
            final String state = task.getCallNow();
            final String title = task.getCallTitle();
            final String desp = task.getCallDesp();
            final int money = task.getCallMoney();

            holder.taskState.setText(String.valueOf(state));
            holder.taskTitle.setText(String.valueOf(title));
            holder.taskDesp.setText(String.valueOf(desp));
            holder.taskMoney.setText(String.valueOf(money));


            //通过为条目设置点击事件触发回调
            if (mOnItemClickLitener != null) {
                holder.cardView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onItemClick(view,position,task.getCallId());
                            }
                        });
            }
        }

        @Override
        public int getItemCount(){
            return mTaskList.size();
        }

    //设置回调接口
    public interface OnItemClickLitener{
        void onItemClick(View view, int position,int callId);
    }

    public void setOnItemClickLitener(TaskAdapter.OnItemClickLitener mOnItemClickLitener){
            this.mOnItemClickLitener = mOnItemClickLitener;
        }
}
