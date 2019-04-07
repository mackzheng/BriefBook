package com.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.Service.IMyAidlInterface;

public class ServiceDemo extends Service
{
    public static final String TAG = "MyService";

    private IBinder myS = new IMyAidlInterface.Stub() {
        @Override
        public void setName(String name) throws RemoteException {
            Log.i(TAG,"name ="+name);
        }

        @Override
        public void setAge(int age) throws RemoteException {
            Log.i(TAG,"age ="+age);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return myS;
    }


}
