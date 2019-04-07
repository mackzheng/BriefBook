package com.network;

import com.network.engine.NetWorkEngine;

public abstract class BaseManager implements IBaseManager {

     @Override
    public void sendHttp(BaseRequest request, BaseResponse response) {
        //走OKhttp
         if(request!=null) request.setRequestType(BaseRequest.RequestType.HTTP);
         NetWorkEngine.getInstance().send(request,response);
    }

    @Override
    public void sendTcp(BaseRequest request, BaseResponse response) {
        //走Socket
        if(request!=null) request.setRequestType(BaseRequest.RequestType.TCP);
        NetWorkEngine.getInstance().send(request,response);
    }

    @Override
    public void sendUdp(BaseRequest request, BaseResponse response) {
        //走Udp
        if(request!=null) request.setRequestType(BaseRequest.RequestType.UDP);
        NetWorkEngine.getInstance().send(request,response);
    }
}
