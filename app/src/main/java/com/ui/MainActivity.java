package com.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.avl.briefbook.R;
import com.biz.login.ILoginManager;
import com.biz.login.LoginManager;

public class MainActivity extends AppCompatActivity implements ILoginManager.LoginResponse {
    private static final String TAG = "MainActivity";
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                LoginManager.getLoginManager().loginHttpRequest("mack","abc",MainActivity.this);
            }
        });
        this.getSupportFragmentManager().popBackStack();

        AppCompatButton button = new AppCompatButton(this);
        Log.i(TAG,"------onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"------onRestart");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"------onStart");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i(TAG,"------onSaveInstanceState");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG,"------onRestoreInstanceState");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG,"------onConfigurationChanged");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"------onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"------onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"------onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG,"------onDestroy");

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
    public void onSuccess() {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onTimeout() {

    }
}
