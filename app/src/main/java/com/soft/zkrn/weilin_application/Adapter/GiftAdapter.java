package com.soft.zkrn.weilin_application.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soft.zkrn.weilin_application.Class.Gift;
import com.soft.zkrn.weilin_application.R;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.ViewHolder>{

    private Context mContext;
    private List<Gift> mGiftList;
    private OnItemClickLitener mOnItemClickLitener;


    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView giftImage;
        TextView giftPoint;
        LinearLayout giftItem;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            giftImage = (ImageView)view.findViewById(R.id.iv_image);
            giftPoint = (TextView) view.findViewById(R.id.tv_point);
            giftItem = (LinearLayout)view.findViewById(R.id.gift_item);
        }
    }

    /*
     * 构造函数
     */
    public GiftAdapter(List<Gift> giftList){
        this.mGiftList = giftList;
//        this.mContext = context;
//        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.gift_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){//////

        final Gift gift = mGiftList.get(position);
        final int point = gift.getPoint();
        final int num = gift.getNum();
        final String name = gift.getName();
        final String description = gift.getDescription();
        Image image_t = null;

        holder.giftPoint.setText(String.valueOf(point));
        if(gift.getImage() != null){
            image_t = gift.getImage();
            Glide.with(mContext).load(gift.getImage()).into(holder.giftImage);
        }
        final Image image = image_t;

        //通过为条目设置点击事件触发回调
        if (mOnItemClickLitener != null) {
            holder.cardView.setOnClickListener(
                    new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickLitener.onItemClick(view,position,point,num,name,description,image);
                }
            });
        }
    }

    @Override
    public int getItemCount(){
        return mGiftList.size();
    }

    //设置回调接口
    public interface OnItemClickLitener{
        void onItemClick(View view, int position, int point, int num, String name, String description, Image image);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
