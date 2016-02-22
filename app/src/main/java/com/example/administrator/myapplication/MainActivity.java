package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private Handler clickHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String type = msg.getData().getString("key");
            if("f".equals(type)){
                Toast.makeText(context,"操作完成", Toast.LENGTH_LONG).show();
            } else {
                etUserName.setText(type);
            }
        }
    };

    @ViewInject(R.id.etUserName)
    private EditText etUserName;
    @ViewInject(R.id.ivImg1)
    private ImageView ivImg1;
    @ViewInject(R.id.etUrl)
    private EditText etUrl;
    @ViewInject(R.id.tvResult)
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LogUtil.i("created!");
        context = this;
        x.view().inject(this);
    }


    @Event(value = R.id.btnConfirm,
            type = View.OnClickListener.class)
    private void btnConfirmClicked(View view){
        LogUtil.i("btnConfirm clicked");
        Runnable r = new Runnable(){
            @Override
            public void run() {
                long releaseTime = 5000 + new Date().getTime();
                while(new Date().getTime() < releaseTime){
                    try {
                        synchronized (this) {
                            wait(1000);
                        }
                        Bundle b = new Bundle();
                        b.putString("key", "还剩 " + (releaseTime - new Date().getTime()) / 1000 + " 秒");
                        Message msg = new Message();
                        msg.setData(b);
                        clickHandler.sendMessage(msg);
                    } catch (Exception e) {
                        LogUtil.e("错误："+e);
                    }
                }
                Bundle b = new Bundle();
                b.putString("key", "f");
                Message msg = new Message();
                msg.setData(b);
                clickHandler.sendMessage(msg);
            }
        };

        new Thread(r).start();
    }

    @Event(value = R.id.btnReset,
        type = View.OnClickListener.class)
    private void btnResetClicked(View view){
        LogUtil.i("btnReset clicked");
        etUserName.setText("");
        tvResult.setText("");
    }

    @Event(value = R.id.btnLoadPic,
        type = View.OnClickListener.class)
    private void btnLoadPicClicked(View view){
//        String url= "http://192.168.10.2/shop/resources/uploadfiles/images/1445308242413.jpg";
        String url = "http://img3.imgtn.bdimg.com/it/u=3651984164,2137314275&fm=15&gp=0.jpg";
        x.image().bind(ivImg1, url);

    }

    @Event(value = R.id.ivImg1,
        type = View.OnLongClickListener.class)
    private boolean ivImg1LongClicked(View view){
        Toast.makeText(context, "长按", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Event(value = R.id.btnLoad, type = View.OnClickListener.class)
    private void btnLoadClicked(View view){
        String url = etUrl.getText().toString();
        LogUtil.i("btnLoad Clicked");
        LogUtil.i("Url:"+url);
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                tvResult.setText(result.toString());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Event(value = R.id.btnOpenSecActivity, type = View.OnClickListener.class)
    private void btnOpenSecActivity(View view){
        try {
            LogUtil.i("startting new activity");
            startActivity(new Intent(this, SecActivity.class));
        } catch (Exception e) {
            Toast.makeText(context, "Err:"+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Event(value = R.id.btnOpenThirActivity, type = View.OnClickListener.class)
    private void btnOpenThirActivity(View view){
        try {
            LogUtil.i("startting new activity");
            startActivity(new Intent(this, ThirdActivity.class));
        } catch (Exception e) {
            Toast.makeText(context, "Err:"+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            LogUtil.i("Settings Clicked");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
