package com.example.administrator.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import database.DbUtils;
import database.ListItem;

/**
 * Created by Administrator on 2016/2/22.
 */
public class SecActivity extends Activity {

    @ViewInject(R.id.lvList)
    private ListView lvList;
    @ViewInject(R.id.etInsert)
    private EditText etInsert;

    private Context context;
    private int positionToDelete=-1;
    private List<String> strs = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secactivity);
        x.view().inject(this);
        context = this;
        initList();

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionToDelete = position;
                AlertDialog dialog = new AlertDialog.Builder(context).setMessage("确定删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete(positionToDelete);
                                Toast.makeText(context, "已删除", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "已取消", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .create();
                dialog.show();
            }
        });
    }

    private void delete(int position){
        Toast.makeText(context, "deleting item:"+position, Toast.LENGTH_SHORT).show();
        String v = strs.get(position);
        strs.remove(position);
        refreshList();
        try {
            DbUtils.getInstance().getDb().execNonQuery("DELETE from list_item where value = '"+v+"'");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Event(value = R.id.btnInsert, type = View.OnClickListener.class)
    private void insert(View view){
        String value = etInsert.getText().toString();
        if(null == value || value.isEmpty()){
            Toast.makeText(context, "请输入文字", Toast.LENGTH_SHORT).show();
        } else {
            strs.add(value);
            etInsert.setText("");
            refreshList();
        }
    }

    @Event(value = R.id.btnClearList, type = View.OnClickListener.class)
    private void clearList(View view){
        try {
            DbUtils.getInstance().getDb().execNonQuery("DELETE FROM list_item where 1=1");
            strs = new ArrayList<String>();
            refreshList();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Event(value = R.id.btnPersist, type = View.OnClickListener.class)
    private void persist(View view){
        LogUtil.i("persisting...");
        for(String s : strs){
            ListItem a = new ListItem();
            a.setValue(s);
            try {
                DbUtils.getInstance().getDb().save(a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(context, "保存成功",Toast.LENGTH_SHORT).show();
    }

    private void initList(){
        try {
            List<ListItem> items = DbUtils.getInstance().getDb().selector(ListItem.class).findAll();
            if(items != null && items.size()>0){
                for(ListItem i : items){
                    strs.add(i.getValue());
                }
            }
            refreshList();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void refreshList(){
        lvList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,strs));
    }
}
