package com.network.engine.protal.tcp.listener;

import com.network.engine.protal.tcp.TcpClient;
import com.network.engine.protal.tcp.bean.TcpMsg;

/**
 */
public interface TcpClientListener {

    void onConnected(TcpClient client);

    void onSended(TcpClient client, TcpMsg tcpMsg);

    void onDisconnected(TcpClient client, String msg, Exception e);

    void onReceive(TcpClient client, TcpMsg tcpMsg);

    void onValidationFail(TcpClient client, TcpMsg tcpMsg);

    class SimpleTcpClientListener implements TcpClientListener {

        @Override
        public void onConnected(TcpClient client) {

        }

        @Override
        public void onSended(TcpClient client, TcpMsg tcpMsg) {

        }

        @Override
        public void onDisconnected(TcpClient client, String msg, Exception e) {

        }

        @Override
        public void onReceive(TcpClient client, TcpMsg tcpMsg) {

        }

        @Override
        public void onValidationFail(TcpClient client, TcpMsg tcpMsg) {

        }
    }
}
