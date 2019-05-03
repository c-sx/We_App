package com.soft.zkrn.weilin_application.NewGson;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class GsonUtil{

    private Gson gson = new Gson();


    public <T> void translateJson(String reader,Type typeOfT,CallBackGson callBackGson){

        try {
            T t = gson.fromJson(reader,typeOfT);
            callBackGson.onSuccess(t);
        } catch (JsonSyntaxException e) {
            callBackGson.onFail(e);
        }

//        return null;
    }
}
