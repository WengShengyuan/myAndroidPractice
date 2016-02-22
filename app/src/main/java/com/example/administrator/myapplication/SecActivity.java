package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/22.
 */
public class SecActivity extends Activity {

    @ViewInject(R.id.lvList)
    private ListView lvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secactivity);
        x.view().inject(this);

        lvList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,getData()));

    }

    private List<String> getData(){
        List<String> data = new ArrayList<String>();
        for (int i =0 ;i <5; i++) {
            data.add("啦");
            data.add("啦啦");
            data.add("啦啦啦");
        }
        return data;
    }
}
