package com.network.engine;

import com.network.BaseRequest;
import com.network.BaseResponse;

public interface INetWorkEngine {
    public void send(BaseRequest request, BaseResponse response);

    public void cancel(BaseRequest request);
}
