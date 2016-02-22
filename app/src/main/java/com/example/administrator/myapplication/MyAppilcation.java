package com.example.administrator.myapplication;

import android.app.Application;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

/**
 * Created by Administrator on 2016/2/22.
 */
public class MyAppilcation extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        LogUtil.i("application created");
    }
}
