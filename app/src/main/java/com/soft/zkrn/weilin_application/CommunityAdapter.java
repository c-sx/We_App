package com.soft.zkrn.weilin_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder>{

    private Context mContext;
    private List<community> mCommunityList;
    private OnItemClickLitener mOnItemClickLitener;


    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView communityImage;
        TextView communityName;
        TextView communityType;
        TextView communityNum;
        LinearLayout communityItem;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            communityImage = (ImageView)view.findViewById(R.id.community_headPortrait);
            communityName = (TextView) view.findViewById(R.id.community_name);
            communityNum = (TextView)view.findViewById(R.id.community_num);
            communityType = (TextView)view.findViewById(R.id.community_type);
            communityItem = (LinearLayout)view.findViewById(R.id.community_item);
        }
    }

    /*
     * 构造函数
     */
    public CommunityAdapter(List<community> communityList){
        this.mCommunityList = communityList;
///////////////////
//        this.mContext = context;
//        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.community_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){//////

        final community community = mCommunityList.get(position);

        final int num = community.get_num();
        final String type =community.get_type();

        holder.communityName.setText(community.get_name());
        holder.communityType.setText("种类："+ type);
        holder.communityNum.setText("人数："+ String.valueOf(num));
        if(community.get_image() != 0)
            Glide.with(mContext).load(community.get_image()).into(holder.communityImage);

///////////////////////////////
//        holder.tv.setText(mDatas.get(position));
        //通过为条目设置点击事件触发回调
        if (mOnItemClickLitener != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickLitener.onItemClick(view,position,num,type);
                }
            });
        }


    }

    @Override
    public int getItemCount(){
        return mCommunityList.size();
    }


///////////////////////////
    //设置回调接口
    public interface OnItemClickLitener{
        void onItemClick(View view, int position, int n, String s);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
//        return this.mContext.
    }
}
