package com.soft.zkrn.weilin_application.Activities.Gift;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.Class.Gift;
import com.soft.zkrn.weilin_application.Adapter.GiftAdapter;
import com.soft.zkrn.weilin_application.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Gift_Mainpage extends AppCompatActivity {

    private LinearLayout ll_record;

    private Gift[] gifts;
    private GiftAdapter adapter;
    private List<Gift> giftList = new ArrayList<>();

    /**
     * 录入卡片
     */
    private Gift[] initData(){
        Gift[] cmy = {
                //int point,int num,String name,String description,Image image
                new Gift(20,1,"五三","好好学习",null),
                new Gift(20,2,"黄冈练习册","好好学习",null),
                new Gift(30,3,"吃剩的口香糖","好吃",null),
                new Gift(0,4,"菜徐坤打过的篮球","口区",null),
                new Gift(2000,5,"嚼过的甘蔗","好吃",null)
        };
        return cmy;
    }

    private void initGift(){
        giftList.clear();
        gifts = initData();
        for(int i = 0 ; i < 10 ; i++ ){
            giftList.add(gifts[i % 5]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage__gift);

        ll_record = findViewById(R.id.ll_record);

        ll_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gift_Mainpage.this,Gift_Record.class));
            }
        });

        /**
         * 卡片布局
         */
        initGift();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GiftAdapter(giftList);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickLitener(new GiftAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position, int point, int num, String name, String description, Image image) {
//                Intent intent = new Intent(Community_Mainpage.this, Community_Introduction.class);
//                intent.putExtra("extra_num",num);
//                intent.putExtra("extra_type",type);
//                startActivity(intent);
                Toast.makeText(Gift_Mainpage.this,"请重试", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }


}
