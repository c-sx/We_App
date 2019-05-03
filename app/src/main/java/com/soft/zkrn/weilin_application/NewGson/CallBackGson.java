package com.soft.zkrn.weilin_application.NewGson;

public interface CallBackGson<T> {

    void onSuccess(T obj);

    void onFail(Exception e);
}
