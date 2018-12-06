package com.avl.volley.businessLayer.main;

import android.content.Context;

public interface IMainManager {
      public void getPhoneArea(Context context, String url, String tag, IMainDelegate delegate);
}
