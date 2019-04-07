package com.network;

import com.network.engine.protal.tcp.bean.TcpMsg;
import com.network.engine.protal.udp.bean.UdpMsg;

public class BaseRequest {

    public enum RequestType
    {
        HTTP,
        TCP,
        UDP
    }
    protected RequestType requestType;
    protected String url;
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public BaseRequest() {

    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private void createHttpRequest()
    {

    }

    public String createTcpRequestToString()
    {

        return "";
    }

    public byte[] createTcpRequestToByteArray()
    {

        return new byte[10];
    }

    public TcpMsg createTcpRequestToMsg()
    {

        return new TcpMsg(1);
    }

    public UdpMsg createUdpRequestToMsg()
    {
        return new UdpMsg(1);

    }
}
