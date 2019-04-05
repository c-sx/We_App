package com.soft.zkrn.weilin_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Message_Mainpage extends AppCompatActivity {

    private Message[] messages;
    private MessageAdapter adapter;
    private List<Message> messageList = new ArrayList<>();

    /**
     * 录入卡片
     */
    private Message[] initData(){
        Message[] cmy = {
                //int point,int num,String name,String description,Image image
                new Message(201900202,"不知道"),
                new Message(2003033,"该说啥"),
                new Message(2044343,"那么就"),
                new Message(4524242,"不说了"),
                new Message(433335,"好不好")
        };
        return cmy;
    }

    private void initGift(){
        messageList.clear();
        messages = initData();
        for(int i = 0 ; i < 10 ; i++ ){
            messageList.add(messages[i % 5]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage__message);


        /**
         * 卡片布局
         */
        initGift();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickLitener(new MessageAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position, long time, String content) {
//                Intent intent = new Intent(Community_Mainpage.this, Community_Introduction.class);
//                intent.putExtra("extra_num",num);
//                intent.putExtra("extra_type",type);
//                startActivity(intent);
                Toast.makeText(Message_Mainpage.this,"请重试", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
