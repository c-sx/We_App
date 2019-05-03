package com.soft.zkrn.weilin_application.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soft.zkrn.weilin_application.Class.Message;
import com.soft.zkrn.weilin_application.R;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private Context mContext;
    private List<Message> mMessageList;
    private OnItemClickLitener mOnItemClickLitener;


    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView messageContent;
        LinearLayout messageItem;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            messageContent = (TextView)view.findViewById(R.id.tv_message);
            messageItem = (LinearLayout) view.findViewById(R.id.ll_click);
        }
    }

    /*
     * 构造函数
     */
    public MessageAdapter(List<Message> messageList){
        this.mMessageList = messageList;
//        this.mContext = context;
//        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessageAdapter.ViewHolder holder, final int position){//////

        final Message message = mMessageList.get(position);
        final long time = message.gettime();
        final String content = message.getcontent();

        holder.messageContent.setText(String.valueOf(content));

        //通过为条目设置点击事件触发回调
        if (mOnItemClickLitener != null) {
            holder.cardView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemClick(view,position,time,content);
                        }
                    });
        }
    }

    @Override
    public int getItemCount(){
        return mMessageList.size();
    }

    //设置回调接口
    public interface OnItemClickLitener{
        void onItemClick(View view, int position, long time, String content);
    }

    public void setOnItemClickLitener(MessageAdapter.OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
