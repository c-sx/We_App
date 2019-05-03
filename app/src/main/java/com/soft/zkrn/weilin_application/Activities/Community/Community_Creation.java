package com.soft.zkrn.weilin_application.Activities.Community;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.soft.zkrn.weilin_application.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class Community_Creation extends AppCompatActivity {

    private TextView location;
    private TextView type;
    private Button complete;
    private EditText name;
    private ImageView add;

    public static final int CHOOSE_PHOTO = 2;

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
                type.setText(listData.get(options1));
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
            add.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"失败了", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_creation);

        location = findViewById(R.id.tv_location);
        type = findViewById(R.id.tv_type);
        complete = findViewById(R.id.bt_complete);
        name = findViewById(R.id.et_name);
        add = findViewById(R.id.iv_add);

        IntentFilter close_intentFilter=new IntentFilter("com.example.Close_Community");
        registerReceiver(close_receiver,close_intentFilter);

        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Community_Creation.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Community_Creation.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String communityName = name.getText().toString().trim();
                String communityType = type.getText().toString().trim();

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
                            Toast.makeText(Community_Creation.this,"好吧", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.setAction("com.example.Refresh_MyCommunity");
                            sendBroadcast(intent);

                            Intent intent1 = new Intent(Community_Creation.this, SuccessfulCreation.class);
                            startActivity(intent1);

                            finish();
                        }
                    });
                    builder_1.setNegativeButton("不对", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Community_Creation.this,"行", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder_1.show();
                }
            }
        });

    }
}
