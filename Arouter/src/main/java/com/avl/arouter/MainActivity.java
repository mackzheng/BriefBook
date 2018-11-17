package com.avl.arouter;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    HandlerThread handlerThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        new MyThread1().start();
    }


    private Handler handler1 ;

    class MyThread1 extends Thread {
        @Override
        public void run() {
            super.run();
            Looper.prepare();

            handler1 = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    System.out.println( "threadName--" + Thread.currentThread().getName() + "messageWhat-"+ msg.what );
                }
            };

            handler1.sendEmptyMessageDelayed( 10 ,10*1000 ) ;
            handler1.sendEmptyMessageDelayed( 5,5*1000 ) ;

            try {
                sleep( 15*1000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Looper.loop();
            }
    }
}