package com.network;

public interface IBaseManager {
    public void sendHttp(BaseRequest request, BaseResponse response);

    public void sendTcp(BaseRequest request, BaseResponse response);

    public void sendUdp(BaseRequest request, BaseResponse response);
}
