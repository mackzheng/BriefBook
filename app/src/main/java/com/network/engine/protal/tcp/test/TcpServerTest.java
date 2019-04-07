package com.network.engine.protal.tcp.test;

import com.network.engine.protal.tcp.TcpClient;
import com.network.engine.protal.tcp.TcpClientConfig;
import com.network.engine.protal.tcp.TcpServerInfo;
import com.network.engine.protal.tcp.helper.stickpackage.StaticLenStickPackageHelper;

public class TcpServerTest {

    public static void main(String[] args) {

        TcpServerInfo targetInfo = new TcpServerInfo("10.155.1.221", 2222);
        TcpClient xTcpClient = TcpClient.getTcpClient(targetInfo);
        xTcpClient.config(new TcpClientConfig.Builder()
                .setConnTimeout(4000)
//              .setStickPackageHelper(new VariableLenStickPackageHelper(ByteOrder.LITTLE_ENDIAN, 2, 2, 4 + 8))
//              .setStickPackageHelper(new SpecifiedStickPackageHelper("888".getBytes(), "999".getBytes()))
                .setStickPackageHelper(new StaticLenStickPackageHelper(3))
                .setLocalPort(22223)
                .create());
//        xTcpClient.addTcpClientListener(this);
        xTcpClient.connect();
    }


    private void tcpServerTest() {
//        TcpServer xTcpServer = TcpServer.getTcpServer(4567);
//        xTcpServer.addTcpServerListener(this);
        TcpClientConfig clientConfig = new TcpClientConfig.Builder().setStickPackageHelper(new StaticLenStickPackageHelper(3)).create();
//        xTcpServer.config(new TcpServerConfig.Builder().setTcpConnConfig(clientConfig).create());
//        xTcpServer.startServer();
    }
}
