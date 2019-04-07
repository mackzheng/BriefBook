package com.network.engine.protal;

import com.network.BaseRequest;
import com.network.BaseResponse;
import com.network.engine.protal.tcp.TcpClient;
import com.network.engine.protal.tcp.TcpClientManager;
import com.network.engine.protal.tcp.bean.TcpMsg;
import com.network.engine.protal.tcp.TcpServerInfo;
import com.network.engine.protal.tcp.listener.TcpClientListener;
import com.network.engine.protal.tcp.util.SocketLog;

public class TcpManager {
    private static final String TAG ="TcpManager";
    private volatile static TcpManager tcpManager;
    private static TcpServerInfo serverInfo = new TcpServerInfo("192.168.0.1", 80);
    private static TcpClient tcpClient;

    private TcpManager() {
    }

    public static TcpManager getInstance() {
        if (tcpManager == null) {
            synchronized (TcpManager.class) {
                if (tcpManager == null) {
                    tcpClient = TcpClientManager.getTcpClient(serverInfo);
                    tcpManager = new TcpManager();
                }
            }
        }
        return tcpManager;
    }

    public static void send(BaseRequest request, BaseResponse response){
       String message = request.createTcpRequestToString();
        tcpClient.addTcpClientListener(new TcpClientListener() {
            @Override
            public void onConnected(TcpClient client) {
                SocketLog.d(TAG,"onConnected");
            }

            @Override
            public void onSended(TcpClient client, TcpMsg tcpMsg) {
                SocketLog.d(TAG,"sended");
            }

            @Override
            public void onDisconnected(TcpClient client, String msg, Exception e) {
                SocketLog.d(TAG,"onDisconnected");
                if(response!=null)
                {
                    response.onFailed();
                }
            }

            @Override
            public void onReceive(TcpClient client, TcpMsg tcpMsg) {
                SocketLog.d(TAG,"onReceive");
                if(response!=null)
                {
                    response.onSuccess();
                }
            }

            @Override
            public void onValidationFail(TcpClient client, TcpMsg tcpMsg) {
                SocketLog.d(TAG,"onValidationFail");
                if(response!=null)
                {
                    response.onFailed();
                }
            }
        });
        tcpClient.sendMsg(message);
    }

    public  void send(byte[] message)
    {
        tcpClient.sendMsg(message);
    }

    public  void send(TcpMsg message)
    {
        tcpClient.sendMsg(message);
    }

    public  void cancel(int messageId)
    {
        tcpClient.cancelMsg(messageId);
    }

    public  void cancel(TcpMsg msg)
    {
        tcpClient.cancelMsg(msg);
    }
}
