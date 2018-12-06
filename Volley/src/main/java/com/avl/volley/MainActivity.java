package com.avl.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.avl.volley.baseVolley.VolleyRequest;
import com.avl.volley.businessLayer.BusinessProtal;
import com.avl.volley.businessLayer.main.IMainDelegate;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IMainDelegate {

    private TextView responseTextView;
    private Button postBtn;
    private Button getBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseTextView = findViewById(R.id.volley_response);
        getBtn = findViewById(R.id.volley_get_btn);
        getBtn.setOnClickListener(this);
        postBtn = findViewById(R.id.volley_post_btn);
        postBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.volley_get_btn:
                String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=18664590625";
//                volley_get(url);
//                VolleyRequest.requestGet(this,url,"volley_get",this);
                BusinessProtal.mainManager().getPhoneArea(this,url,"volley_get",this);

                break;
            case R.id.volley_post_btn:
                String url2 = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
//                volley_post(url2);
                Map<String,String>  map = new HashMap<>();
                map.put("tel","18664590625");
                VolleyRequest.requestPost(this,url2,"volley_get",map,this);
                break;
        }
    }

    //简单调用
    private void volley_post(String url) {

//        JsonObjectRequest??
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("mack", response);
                responseTextView.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mack", "Error" + error.networkResponse.data);
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tel", "18664590625");
                return map;
            }
        };
        request.setTag(MainActivity.class.getName());
        MyApplication.getQueue().add(request);
    }


    private void volley_get(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("mack", response);
                responseTextView.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mack", "Error" + error.networkResponse.data);
            }
        });
        request.setTag(MainActivity.class.getName());
        MyApplication.getQueue().add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getQueue().cancelAll(MainActivity.class.getName());
    }

    @Override
    public void onSuccess(String response) {

    }

    @Override
    public void onError(String error) {

    }
}
