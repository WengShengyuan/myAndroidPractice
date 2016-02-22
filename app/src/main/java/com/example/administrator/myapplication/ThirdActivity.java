package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/2/22.
 */
public class ThirdActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdactivity);
        x.view().inject(this);
    }


}
