package com.soft.zkrn.weilin_application.Activities.Community;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.soft.zkrn.weilin_application.GsonClass.CommunityData_Search;
import com.soft.zkrn.weilin_application.GsonClass.StateData;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.Widget.ScreenUtils;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Post;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Put;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class Community_Creation extends AppCompatActivity {

    private  AlertDialog dialog;
    private TextView tv_location;
    private TextView tv_type;
    private Button bt_complete;
    private EditText et_name;
    private EditText et_description;
    private ImageView iv_add;
    private Button btn_cancel;
    private Button btn_agree;

    private int cId;
    private int communityNumber = 0;
    private String communityName;
    private String communityDescription;
    private String communityAddress;
    private String communityType;

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();

    public static final int CHOOSE_PHOTO = 2;
    public static final int SUCCESS = 3;
    public static final int FAIL_CREATE = 4;
    public static final int FINISH = 5;
    public static final int FAIL_JOIN = 6;
    public static final int NEXT = 7;
    public static final int ADD = 8;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FINISH:
                    Toast.makeText(Community_Creation.this,"加入成功",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    end();
                    break;
                case FAIL_CREATE:
                    Toast.makeText(Community_Creation.this,"网络状态异常",Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    showDialogForTime();
                    break;
                case FAIL_JOIN:
                    Toast.makeText(Community_Creation.this,"网络状态异常，加入失败了",Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
                    btn_agree.setEnabled(true);
                    btn_cancel.setEnabled(true);
                    break;
                case NEXT:
                    CommunityData_Search cd = (CommunityData_Search) msg.obj;
                    cId = cd.getExtend().getCommunity().getComId();
                    join(cId);
                    break;
                case ADD:
                    callForNumber(cId);
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 接受关闭社区页面广播
     *
     */
    private BroadcastReceiver close_receiver =new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.example.Close_Community"))
                finish();
        }
    };

    /**
     * 注销广播
     *
     */
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(close_receiver);
    }

    private void showPickerView() {
//      要展示的数据
        final List<String> listData = getData();
//      监听选中
        OptionsPickerView pvOptions = new OptionsPickerBuilder(Community_Creation.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
//               返回的分别是三个级别的选中位置
//              展示选中数据
                tv_type.setText(listData.get(options1));
            }
        })
                .setSelectOptions(0)//设置选择第一个
                .setOutSideCancelable(false)//点击背的地方不消失
                .build();//创建
//      把数据绑定到控件上面
        pvOptions.setPicker(listData);
//      展示
        pvOptions.show();
    }

    /**
     * 数据
     */
    private List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("学校");
        list.add("生活小区");
        list.add("工作单位");
        list.add("娱乐场所");
        list.add("其他");
        return list;
    }

    /**
     *
     * 调取相册
     */
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this,"不行", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19)
                        handleImageOnKitKat(data);
                    else
                        handleImageBeforeKitkat(data);
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri , null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri , null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitkat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri , null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri , String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri , null , selection , null , null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            iv_add.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"失败了", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_creation);

        tv_location = findViewById(R.id.tv_location);
        tv_type = findViewById(R.id.tv_type);
        bt_complete = findViewById(R.id.bt_complete);
        et_name = findViewById(R.id.et_name);
        et_description = findViewById(R.id.et_content);
        iv_add = findViewById(R.id.iv_add);

        /**
         * toolbar component
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_community_creation);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_Community");
        registerReceiver(close_receiver,close_intentFilter);

        tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView();
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Community_Creation.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Community_Creation.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
            }
        });

        bt_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                communityName = et_name.getText().toString().trim();
                communityType = tv_type.getText().toString().trim();
                communityDescription = et_description.getText().toString().trim();

                if (TextUtils.isEmpty(communityName)) {
                    Toast.makeText(Community_Creation.this, "请输入社区名称", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    AlertDialog.Builder builder_1 = new AlertDialog.Builder(Community_Creation.this);
                    builder_1.setTitle("核对信息");
                    builder_1.setMessage("社区名称：\n"+communityName+"\n"+"类别：\n"+communityType);
                    builder_1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            create();
                        }
                    });
                    builder_1.setNegativeButton("不对", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    builder_1.show();
                }
            }
        });

    }

    private void create(){

        //社区种类不匹配，待补充

//        comTitle	否	string	社区标题
//        comCategory	否	string	社区种类
//        comNumber	否	int	社区人数
//        comDesp	否	string	社区描述
//        comAddress	否	string	社区地址
//        comPicture	否	byte[]	社区头像
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("comTitle", communityName);
//        System.out.println("comTitle" + communityName);
        paramsMap.put("comCategory",communityType);
//        System.out.println("comCategory" + communityType);
        paramsMap.put("comNumber", String.valueOf(communityNumber));
//        System.out.println("comNumber" + String.valueOf(communityNumber));
        paramsMap.put("comDesp", communityDescription);
//        System.out.println("comDesp" + communityDescription);
        paramsMap.put("comAddress", "");
//        paramsMap.put("comPicture", "");
        Message msg = Message.obtain();
        httpUtil.POST("http://www.xinxianquan.xyz:8080/zhaqsq/community/insert", paramsMap, new CallBack_Post() {
            @Override
            public void onFinish(String response) {
                System.out.println("json:" + response);
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        StateData data = (StateData) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS;
                            handler.sendMessage(msg);
                        }else{
                            msg.what = FAIL_CREATE;
//                            System.out.println("1");
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = FAIL_CREATE;
//                        System.out.println("2");
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAIL_CREATE;
//                System.out.println("3");
                handler.sendMessage(msg);
            }
        });
    }

    private void join(int cId){
        System.out.println("cid:" + cId);
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("uId", String.valueOf(readPsw_Int("userID")));
        paramsMap.put("cId", String.valueOf(cId));
        Message msg = Message.obtain();
        httpUtil.POST("http://www.xinxianquan.xyz:8080/zhaqsq/unc/insert", paramsMap, new CallBack_Post() {
            @Override
            public void onFinish(String response) {
                System.out.println("join_json" + response);
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        StateData data = (StateData) obj;
                        if(data.getCode() == 100){
                            System.out.println("1");
                            msg.what = ADD;
                            handler.sendMessage(msg);
                        }else{
                            System.out.println("2");
                            msg.what = FAIL_JOIN;
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = FAIL_JOIN;
                        System.out.println("3");
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAIL_JOIN;
                System.out.println("4");
                handler.sendMessage(msg);
            }
        });
    }

    private void callForNumber(int cId){
//        comId	是	int	社区id
//        comTitle	否	string	社区标题
//        comCategory	否	string	社区种类
//        comNumber	否	int	社区人数
//        comDesp	否	string	社区描述
//        comAddress	否	string	社区地址
//        comPicture	否	byte[]	社区头像*/
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("comTitle", communityName);
        paramsMap.put("comId", String.valueOf(cId));
        paramsMap.put("comNumber" , String.valueOf(1));
        Message msg = Message.obtain();
        httpUtil.PUT("http://www.xinxianquan.xyz:8080/zhaqsq/community/updatepic/{comId}", paramsMap, new CallBack_Put() {
            @Override
            public void onFinish(String response) {
                System.out.println(response);
                gsonUtil.translateJson(response, StateData.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        StateData data = (StateData) obj;
                        if(data.getCode() == 100){
                            msg.what = FINISH;
                            System.out.println(1);
                        }else{
                            System.out.println(2);
                            msg.what = FAIL_JOIN;
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = FAIL_JOIN;
                        System.out.println(3);
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAIL_JOIN;
                System.out.println(4);
                handler.sendMessage(msg);
            }
        });
    }

    private void get(){
//        HashMap<String, String> paramsMap = new HashMap<>();
//        paramsMap.put("comTitle",communityName);
        Message msg = Message.obtain();
        httpUtil.GET("http://www.xinxianquan.xyz:8080/zhaqsq/community/select", "comTitle",communityName,new CallBack_Get() {
            @Override
            public void onFinish(String response) {
                System.out.println("get_json" + response);
                gsonUtil.translateJson(response, CommunityData_Search.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        CommunityData_Search data = (CommunityData_Search) obj;
                        if(data.getCode() == 100){
                            msg.what = NEXT;
                            msg.obj = data;
                            System.out.println("5");
                            handler.sendMessage(msg);
                        }else{
                            msg.what = FAIL_JOIN;
                            System.out.println("6");
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = FAIL_JOIN;
                        System.out.println("7");
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAIL_JOIN;
                System.out.println("8");
                handler.sendMessage(msg);
            }
        });
    }


    private void end(){
        Intent intent = new Intent();
        intent.setAction("com.example.Refresh_MyCommunity");
        sendBroadcast(intent);

//        Intent intent1 = new Intent(Community_Creation.this, SuccessfulCreation.class);
//        startActivity(intent1);

        finish();
    }

    private int readPsw_Int(String s) {
        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        return sp.getInt(s,0);
    }

    private void showDialogForTime(){
        final View view = LayoutInflater.from(this).inflate(R.layout.community_creation_dialog,null,false);
        dialog = new AlertDialog.Builder(this).setView(view).create();

        btn_cancel = view.findViewById(R.id.bt_cancel);
        btn_agree = view.findViewById(R.id.bt_agree);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                end();
            }
        });

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.dismiss();
                btn_agree.setEnabled(false);
                btn_cancel.setEnabled(false);
                get();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)), LinearLayout.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setLayout((ScreenUtils.getScreenHeight(this)), LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(false);
    }
}
