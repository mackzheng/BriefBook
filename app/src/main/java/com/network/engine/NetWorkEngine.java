package com.network.engine;

import com.network.BaseRequest;
import com.network.BaseResponse;
import com.network.engine.protal.HttpManager;
import com.network.engine.protal.TcpManager;
import com.network.engine.protal.UdpManager;

/**
 * 核心服务
 * 主要是一个网络接入层.
 * <p>
 * 主要作用：
 * 1.管理业务请求；
 * 2.分发网络返回；
 * 3.管理所有的请求；
 *
 * 所有请求都是异步。
 */
public class NetWorkEngine implements INetWorkEngine {

    private static NetWorkEngine instance = new NetWorkEngine();

    private NetWorkEngine()
    {
        //初始化 HTTP

        //初始化 TCP

        //初始化 UDP
    }

    public static NetWorkEngine getInstance() {
        return instance;
    }

    @Override
    public void send(BaseRequest request, BaseResponse response) {
        switch (request.getRequestType())
        {
            case HTTP:
                //httpManager
                //TODO 这里后续 判断是Get还是Post
                HttpManager.getInstance().post(request, response);
                break;
            case TCP:
                //tcpManager
                TcpManager.getInstance().send(request,response);
                break;
            case UDP:
                //udpanager
                UdpManager.getInstance().send(request,response);
                break;
        }
    }

    @Override
    public void cancel(BaseRequest request) {
        switch (request.getRequestType())
        {
            case HTTP:
                //httpManager
                //TODO 这里后续 判断是Get还是Post
                HttpManager.getInstance().cancel(request);
                break;
            case TCP:
                //tcpManager
                TcpManager.getInstance().cancel(request.createTcpRequestToMsg());
                break;
            case UDP:
                //udpanager
                UdpManager.getInstance().cancel(request.createUdpRequestToMsg());
                break;
        }
    }


}