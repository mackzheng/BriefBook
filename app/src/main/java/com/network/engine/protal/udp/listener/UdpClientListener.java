package com.network.engine.protal.udp.listener;

 import com.network.engine.protal.udp.UdpClient;
import com.network.engine.protal.udp.bean.UdpMsg;

/**
 */
public interface UdpClientListener {
    void onStarted(UdpClient XUdp);

    void onStoped(UdpClient XUdp);

    void onSended(UdpClient XUdp, UdpMsg udpMsg);

    void onReceive(UdpClient client, UdpMsg udpMsg);

    void onError(UdpClient client, String msg, Exception e);

    class SimpleUdpClientListener implements UdpClientListener {

        @Override
        public void onStarted(UdpClient XUdp) {

        }

        @Override
        public void onStoped(UdpClient XUdp) {

        }

        @Override
        public void onSended(UdpClient XUdp, UdpMsg udpMsg) {

        }

        @Override
        public void onReceive(UdpClient client, UdpMsg msg) {

        }

        @Override
        public void onError(UdpClient client, String msg, Exception e) {

        }
    }

}
