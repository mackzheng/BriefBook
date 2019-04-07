package com.network.engine.protal;

import com.network.BaseRequest;
import com.network.BaseResponse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpManager {
    private static HttpManager httpManager = new HttpManager();
    private Map<BaseRequest, Call> map = new ConcurrentHashMap<>();

    private HttpManager() {

    }

    public static HttpManager getInstance() {
        return httpManager;
    }


    //TODO 这里可以替换为 其他库

    public void get(BaseRequest request, BaseResponse response) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request okRequest = new Request.Builder().url(request.getUrl()).build();
        Call call = mOkHttpClient.newCall(okRequest);
        map.put(request, call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (response != null) {
                    response.onFailed();
                }
            }

            @Override
            public void onResponse(Call call, Response okresponse) throws IOException {
                if (response != null) {
                    response.onSuccess();
                }
            }
        });


    }


    public void post(BaseRequest request, BaseResponse response) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
//                .add("username", "superadmin")
//                .add("pwd", "ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413")
                .build();
        Request okrequest = new Request.Builder().url(request.getUrl()).post(formBody).build();
        Call call = mOkHttpClient.newCall(okrequest);
        map.put(request,call);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

                if (response != null) {
                    response.onFailed();
                }
                map.remove(request);
            }

            @Override
            public void onResponse(Call call, Response okresponse) throws IOException {
                if (response != null) {
                    response.onSuccess();
                }
                map.remove(request);
            }
        });
    }


    public void cancel(BaseRequest request) {
        Call call = map.get(request);
        call.cancel();
        map.remove(request);
    }

}
