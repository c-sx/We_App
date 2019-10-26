package com.soft.zkrn.weilin_application.Activities.User;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.soft.zkrn.weilin_application.Activities.Community.Community_Introduction2;
import com.soft.zkrn.weilin_application.GsonClass.StateData;
import com.soft.zkrn.weilin_application.GsonClass.UserInformationData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.Widget.PickerView;
import com.soft.zkrn.weilin_application.Widget.ScreenUtils;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Delete;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Put;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserInformation_Mainpage extends AppCompatActivity {

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();
    private String url = "http://119.23.190.83:8080/zhaqsq/user/get";

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
    private TextView tv_name;

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
    private static final int SUCCESS_SEX = 3;
    private static final int FAIL_CHANGE = 4;
    private static final int SUCCESS_DESP = 5;

    private final String url_change = "http://119.23.190.83:8080/zhaqsq/user/{uid}";

    private int readPsw(String userId){
        SharedPreferences userSettings = (SharedPreferences) getSharedPreferences("setting",MODE_PRIVATE);
        return userSettings.getInt(userId,0);
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
                    userName = user.getUserName();
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
                    tv_name.setText(userName);
                    break;
                case FAIL:
                    Toast.makeText(UserInformation_Mainpage.this, "获取个人信息失败，请检查网络状况", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS_SEX:
                    Toast.makeText(UserInformation_Mainpage.this,"性别修改成功",Toast.LENGTH_SHORT).show();
                    tv_sex.setText(String.valueOf(msg.obj));
                    break;
                case SUCCESS_DESP:
                    Toast.makeText(UserInformation_Mainpage.this,"描述修改成功",Toast.LENGTH_SHORT).show();
                    break;
                case FAIL_CHANGE:
                    Toast.makeText(UserInformation_Mainpage.this,"修改失败",Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    };

    //获得后台String数据
    private String readPsw_String(String userName){
        SharedPreferences userSettings = (SharedPreferences) getSharedPreferences("setting",MODE_PRIVATE);
        return userSettings.getString(userName,"");
    }
    //获得后台int数据
    private int readPsw_Int(String userID){
        SharedPreferences userSettings = (SharedPreferences) getSharedPreferences("setting",MODE_PRIVATE);
        return userSettings.getInt(userID,0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information_mainpage);

        ll_headportrait = findViewById(R.id.ll_UserInformation_HeadPortrait);
        ll_sex = findViewById(R.id.ll_UserInformation_Sex);
        ll_mobilephone = findViewById(R.id.ll_UserInformation_MobilePhone);
        ll_address = findViewById(R.id.ll_UserInformation_Address);
        ll_description = findViewById(R.id.ll_UserInformation_Description);

        iv_headportrait = findViewById(R.id.iv_UserInformation_Picture);
        tv_id = findViewById(R.id.tv_UserInformation_TrueID);
        tv_sex = findViewById(R.id.tv_UserInformation_Sex);
        tv_phone = findViewById(R.id.tv_UserInformation_PhoneNumber);
        tv_name = findViewById(R.id.tv_UserInformation_Nickname);

        uid = readPsw("userID");
        System.out.println("userName :" + userName);
        getUserInformation();

        ll_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_changeDesp = new Intent(UserInformation_Mainpage.this,UserInformation_ChangeDescription.class);
                startActivityForResult(intent_changeDesp,1);
            }
        });

        ll_headportrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                resetHeadportrait();
            }
        });

        ll_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForSex();
            }
        });

        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                resetAddress();
            }
        });
    }

    /**
     * 获得UserInformation_ChangeDescription返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 10){
            switch (requestCode){
                case 1:
                    callForChangeDesp(data.getStringExtra("content"));
                    break;
                default:
                    break;
            }
        }
    }

    private void getUserInformation(){
        httpUtil.GET(UserInformation_Mainpage.this,url, "uid", String.valueOf(uid), new CallBack_Get() {
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
    }

    /**
     * 初始化并弹出对话框方法
     */
    private void showDialogForSex(){
        final View view = LayoutInflater.from(this).inflate(R.layout.user_information_mainpage_dialog_sex,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        Button btn_agree = view.findViewById(R.id.btn_agree);
        final PickerView pv_sex = view.findViewById(R.id.pv_sex);

        /*
         *设定性别
         */
        final List<String> sex_list = new ArrayList<>();
        sex_list.add("女");
        sex_list.add("男");
        pv_sex.setData(sex_list);

        pv_sex.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
            }
        });

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSex(pv_sex.getText());
                dialog.dismiss();
            }
        });

        dialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)), LinearLayout.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setLayout((ScreenUtils.getScreenHeight(this)), LinearLayout.LayoutParams.MATCH_PARENT);
//        dialog.setCanceledOnTouchOutside(false);
    }

    private void changeSex(String sex){
        if(sex != userSex){
            callForChangeSex(sex);
        }else{
        }
    }

    private void callForChangeDesp(String dept){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("uid" , String.valueOf(uid));
        paramsMap.put("userDept", String.valueOf(dept));
        Message msg = Message.obtain();
        httpUtil.PUT(UserInformation_Mainpage.this,url_change, paramsMap, new CallBack_Put() {
            @Override
            public void onFinish(String response) {
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        StateData data = (StateData) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS_DESP;
                        }else{
                            msg.what = FAIL_CHANGE;
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = FAIL_CHANGE;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAIL_CHANGE;
                handler.sendMessage(msg);
            }
        });
    }

    private void callForChangeSex(String sex){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("uid" , String.valueOf(uid));
        paramsMap.put("userSex", String.valueOf(sex));
        Message msg = Message.obtain();
        httpUtil.PUT(UserInformation_Mainpage.this,url_change, paramsMap, new CallBack_Put() {
            @Override
            public void onFinish(String response) {
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        StateData data = (StateData) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS_SEX;
                            msg.obj = sex;
                        }else{
                            msg.what = FAIL_CHANGE;
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = FAIL_CHANGE;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAIL_CHANGE;
                handler.sendMessage(msg);
            }
        });
    }
}
