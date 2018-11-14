package com.avl.eventbus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "mack";

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                final String text = "长江长江我是黄河";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(text);//发送一个事件
                    }
                }).start();
            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void post()
    {
        EventBus.getDefault().post("abc");
    }

    private void postSticker()
    {
        EventBus.getDefault().postSticky("abc");

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent1(String text) {
        Log.d(TAG, "onMessageEvent1 : String:"+text);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent2(String text) {
        Log.d(TAG, "onMessageEvent2 : String:"+text);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent3(String text) {
        Log.d(TAG, "onMessageEvent3 : String:"+text);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent4(String text) {
        Log.d(TAG, "onMessageEvent4 : String:"+text);
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMessageEvent4(Integer text) {
        Log.d(TAG, "onMessageEvent4 : Integer:"+text);
    }

}
