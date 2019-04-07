package com.network.engine.protal;

import com.network.BaseRequest;
import com.network.BaseResponse;
import com.network.engine.protal.tcp.util.SocketLog;
import com.network.engine.protal.udp.UdpClient;
import com.network.engine.protal.udp.UdpClientManager;
import com.network.engine.protal.udp.UdpServerInfo;
import com.network.engine.protal.udp.bean.UdpMsg;
import com.network.engine.protal.udp.listener.UdpClientListener;

public class UdpManager {

    private static final String TAG ="TcpManager";
    private volatile static UdpManager udpManager;
    private static UdpServerInfo serverInfo = new UdpServerInfo("192.168.0.1", 80);
    private static UdpClient udpClient;

    private UdpManager() {
    }

    public static UdpManager getInstance() {
        if (udpManager == null) {
            synchronized (TcpManager.class) {
                if (udpManager == null) {
                    udpClient = UdpClientManager.getUdpClient(serverInfo);
                    udpManager = new UdpManager();
                }
            }
        }
        return udpManager;
    }

    public static void send(BaseRequest request, BaseResponse response)
    {
        udpClient.addUdpClientListener(new UdpClientListener() {
            @Override
            public void onStarted(UdpClient XUdp) {
                SocketLog.d(TAG,"onStarted");
            }

            @Override
            public void onStoped(UdpClient XUdp) {
                SocketLog.d(TAG,"onStoped");
            }

            @Override
            public void onSended(UdpClient XUdp, UdpMsg udpMsg) {
                SocketLog.d(TAG,"onSended");
            }

            @Override
            public void onReceive(UdpClient client, UdpMsg udpMsg) {
                SocketLog.d(TAG,"onReceive");
                if(response!=null)
                {
                    response.onSuccess();
                }
            }

            @Override
            public void onError(UdpClient client, String msg, Exception e) {
                SocketLog.d(TAG,"onError");
                if(response!=null)
                {
                    response.onFailed();
                }
            }
        });
        udpClient.sendMsg(request.createUdpRequestToMsg());
    }


    public  void cancel(int messageId)
    {
        udpClient.cancelMsg(messageId);
    }

    public  void cancel(UdpMsg msg)
    {
        udpClient.cancelMsg(msg);
    }
}
