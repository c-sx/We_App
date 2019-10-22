package com.soft.zkrn.weilin_application.Activities.Message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.soft.zkrn.weilin_application.Activities.Task.TaskCenter;
import com.soft.zkrn.weilin_application.Activities.Task.TaskPublish;
import com.soft.zkrn.weilin_application.Adapter.MessageAdapter;
import com.soft.zkrn.weilin_application.Class.Message;
import com.soft.zkrn.weilin_application.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Message_Mainpage extends AppCompatActivity {

    public static final int NULL = 0;
    public static final int TASK_RECEIVED = 1;


    int event_kind;
    private Message[] messages;
    private MessageAdapter adapter;
    private List<Message> messageList = new ArrayList<>();

    /**
     * 录入卡片
     */
    private Message[] initData(){
        String title = null;
        switch (event_kind){
            case TASK_RECEIVED:
                title = "您有任务被接受了";
                break;
            default:
                break;
        }
        Message[] cmy = {
                //int point,int num,String name,String description,Image image
                new Message(201900202,title),
        };
        return cmy;
    }

    private void initGift(){
        messageList.clear();
        messages = initData();
//        for(int i = 0 ; i < 10 ; i++ ){
//            messageList.add(messages[i % 5]);
//        }
        messageList.add(messages[0]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage__message);

        event_kind = (int) getIntent().getIntExtra("event",NULL);
        if(event_kind == NULL){
            setContentView(R.layout.activity_homepage__message_empty);
        }else{
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
                    click();
                    //Toast.makeText(Message_Mainpage.this,"请重试", Toast.LENGTH_SHORT).show();
                }
            });
            recyclerView.setAdapter(adapter);
        }


    }

    private void click(){
        switch (event_kind){
            case TASK_RECEIVED:
                startActivity(new Intent(Message_Mainpage.this, TaskCenter.class));
                finish();
                break;
            default:
                break;
        }
    }

}
