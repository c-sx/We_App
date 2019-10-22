package com.soft.zkrn.weilin_application.Activities.Map;
import com.soft.zkrn.weilin_application.GsonClass.DeviceData_Map;
import com.soft.zkrn.weilin_application.GsonClass.TaskData_PublishedOrReceived;
import com.soft.zkrn.weilin_application.NewGson.CallBackGson;
import com.soft.zkrn.weilin_application.NewGson.GsonUtil;
import com.soft.zkrn.weilin_application.R;
import com.soft.zkrn.weilin_application.okhttp.CallBack_Get;
import com.soft.zkrn.weilin_application.okhttp.HttpUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class Succour_Center extends AppCompatActivity {

    private final String url_address= "http://119.23.190.83:8080/zhaqsq/request/getBydevice";

    private HttpUtil httpUtil = new HttpUtil();
    private GsonUtil gsonUtil = new GsonUtil();
    private static final int SUCCESS = 1;
    private static final int FAIL = 2;

    private String dimension;//维度
    private String longitude;//经度

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    DeviceData_Map data = (DeviceData_Map)msg.obj;
                    dimension = data.getExtend().getRequest().getDimension();
                    longitude = data.getExtend().getRequest().getLongitude();
                    break;
                case FAIL:
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succour__center);

        Refresh_Thread thread = new Refresh_Thread();
        thread.start();


    }




    //向后台请求设备的数据
    private void callForAddress(){
        //devicenumber	是	String	设备号
        Message msg = Message.obtain();
        httpUtil.GET(Succour_Center.this,url_address,"devicenumber","862177040005277", new CallBack_Get() {
            @Override
            public void onFinish(String response) {
                gsonUtil.translateJson(response, DeviceData_Map.class, new CallBackGson() {
                    @Override
                    public void onSuccess(Object obj) {
                        TaskData_PublishedOrReceived data = (TaskData_PublishedOrReceived) obj;
                        if(data.getCode() == 100){
                            msg.what = SUCCESS;
                            msg.obj = obj;
                        }else{
                            msg.what = FAIL;
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFail(Exception e) {
                        msg.what = FAIL;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                msg.what = FAIL;
                handler.sendMessage(msg);
            }
        });
    }

    //用于向后台发送请求的线程
    private class Refresh_Thread extends Thread{

        public volatile boolean ifWork = true;

        @Override
        public void run() {
            super.run();
            while(ifWork){

                callForAddress();
                System.out.println("wait");
                try {
                    //1000s * 5
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
