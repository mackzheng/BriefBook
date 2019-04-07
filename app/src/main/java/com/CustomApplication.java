package com;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.ui.ActivityLifeCycleMoniter;

public class CustomApplication extends Application {

    private static final String TAG = "CustomApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    private ActivityLifeCycleMoniter activityLifecycleCallbacks = new ActivityLifeCycleMoniter() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstancetate) {
            Log.d(TAG, "onActivityCreated");
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.d(TAG, "onActivityStarted");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d(TAG, "onActivityResumed");

        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.d(TAG, "onActivityPaused");

        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.d(TAG, "onActivityStopped");

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.d(TAG, "onActivitySaveInstanceState");

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.d(TAG, "onActivityDestroyed");

        }
    };
}


