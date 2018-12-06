package com.avl.volley.baseVolley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.avl.volley.MyApplication;

import java.util.Map;

public class VolleyRequest
{
    public static void requestGet(Context context, String url,String tag, final VolleyDelegate delegate)
    {
        final VolleyResponse response = new VolleyResponse(context ,delegate);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String responseString) {
                Log.d("mack", responseString);
                response.onSuccess(responseString);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mack", "Error" + error.networkResponse.data);
                response.onError(error.networkResponse.data.toString());
            }
        });
        request.setTag(tag);
        MyApplication.getQueue().add(request);
        MyApplication.getQueue().start();
    }

    public static void requestPost(final Context context, String url, String tag, final Map<String,String> map, final VolleyDelegate delegate)
    {
        final VolleyResponse response = new VolleyResponse(context ,delegate);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String responseString) {
                Log.d("mack", responseString);
                response.onSuccess(responseString);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mack", "Error" + error.networkResponse.data);
                response.onError(error.networkResponse.data.toString());
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        request.setTag(tag);
        MyApplication.getQueue().add(request);
        MyApplication.getQueue().start();
    }

}
