package com.soft.zkrn.weilin_application.Activities.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.soft.zkrn.weilin_application.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class UserInformation_Mainpage extends AppCompatActivity {

    private LinearLayout ll_headportrait;
    private LinearLayout ll_id;
    private LinearLayout ll_sex;
    private LinearLayout ll_description;
    private LinearLayout ll_mobilephone;
    private LinearLayout ll_creditcard;
    private LinearLayout ll_address;
//    private LinearLayout  ll_setting;
    private ImageView iv_headportrait;
    private TextView tv_id;
    private TextView tv_sex;
    private TextView tv_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information_mainpage);

        ll_headportrait = findViewById(R.id.ll_UserInformation_HeadPortrait);
        ll_sex = findViewById(R.id.ll_UserInformation_Sex);
        ll_mobilephone = findViewById(R.id.ll_UserInformation_MobilePhone);
        ll_address = findViewById(R.id.ll_UserInformation_Address);

        iv_headportrait = findViewById(R.id.iv_UserInformation_Picture);
        tv_id = findViewById(R.id.tv_UserInformation_TrueID);
        tv_sex = findViewById(R.id.tv_UserInformation_Sex);
        tv_phone = findViewById(R.id.tv_UserInformation_PhoneNumber);

//        initData();

        ll_headportrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                resetHeadportrait();
            }
        });

        ll_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseSex();
            }
        });

        ll_mobilephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                resetPhone();
                startActivity(new Intent(UserInformation_Mainpage.this,UserInformation_ResetPhoneNumber.class));
            }
        });

        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                resetAddress();
            }
        });
    }

    private void chooseSex(){
        final List<String> listData = getData();
//      监听选中
        OptionsPickerView pvOptions = new OptionsPickerBuilder(UserInformation_Mainpage.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
//               返回的分别是三个级别的选中位置
//              展示选中数据
                tv_sex.setText(listData.get(options1));
            }
        })
                .setSelectOptions(0)//设置选择第一个
                .setOutSideCancelable(false)//点击背的地方不消失
                .build();//创建
//      把数据绑定到控件上面
        pvOptions.setPicker(listData);
//      展示
        pvOptions.show();

//        passSexData();

    }

    /**
     * 数据
     */
    private List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("男");
        list.add("女");
        return list;
    }

//    private void passSexData(){
//
//    }
}
