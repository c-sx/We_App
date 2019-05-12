package com.soft.zkrn.weilin_application.Activities.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.soft.zkrn.weilin_application.GsonClass.UserInformationData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class UserInformation_Mainpage extends AppCompatActivity {

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();

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

    private int uid;//	int	用户id
    private String userName;//	string	昵称
    private String userPassword;//	string	密码
    private String userPhonenumber;//	string	电话号码
    private String userDept;//	string	权限
    private String userSex;//	string	性别
    private String userDesp;//	string	个人描述
    private String userNamecheck;//	string	实名情况
    private int userCreditlevel;//	int	信用级别
    private String userMessagelevel;//	string	消息提示等级
    private int userPoint;//	int	积分

    private static final int SUCCESS = 1;
    private static final int FAIL = 2;

    private String readPsw(String userName){
        SharedPreferences userSettings = (SharedPreferences) getSharedPreferences("setting",MODE_PRIVATE);
        return userSettings.getString(userName,"");
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    UserInformationData data = (UserInformationData) msg.obj;
                    UserInformationData.Extend.User user = data.getExtend().getUser();
                    uid = user.getUid();
                    userPhonenumber = user.getUserPhonenumber();
                    userCreditlevel = user.getUserCreditlevel();
                    userSex = user.getUserSex();
                    userDept = user.getUserDept();
                    userNamecheck = user.getUserNamecheck();
                    userMessagelevel = user.getUserMessagelevel();
                    userPoint = user.getUserPoint();

                    System.out.println("id:"+uid);
                    System.out.println("phone:"+userPhonenumber);
                    System.out.println("sex:"+userSex);

                    tv_id.setText(String.valueOf(uid));
                    tv_phone.setText(String.valueOf(userPhonenumber));
                    tv_sex.setText(userSex);
                    break;
                case FAIL:
                    Toast.makeText(UserInformation_Mainpage.this, "获取个人信息失败，请检查网络状况", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


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


        userName = readPsw("userName");
        System.out.println("userName :" + userName);
        httpUtil.GET("http://www.xinxianquan.xyz:8080/zhaqsq/user/get", "userName", userName, new CallBack_Get() {
            @Override
            public void onFinish(String response) {
                System.out.println("json:" +response);
                gsonUtil.translateJson(response, UserInformationData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        Message msg = Message.obtain();
                        UserInformationData data = (UserInformationData) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS;
                            msg.obj = obj;
                            System.out.println(1);
                        }else{
                            msg.what = FAIL;
                            System.out.println(2);
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFail(Exception e) {
                        Message msg = Message.obtain();
                        msg.what = FAIL;
                        System.out.println(3);
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Message msg = Message.obtain();
                msg.what = FAIL;
                System.out.println(4);
                handler.sendMessage(msg);
            }
        });


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

}
