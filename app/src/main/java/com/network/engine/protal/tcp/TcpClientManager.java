package com.network.engine.protal.tcp;

import java.util.HashSet;
import java.util.Set;

/**
 * tcpclient的管理者
 */
public class TcpClientManager {
    private static Set<TcpClient> sMXTcpClients = new HashSet<>();

    public static void putTcpClient(TcpClient tcpClient) {
        sMXTcpClients.add(tcpClient);
    }

    public static TcpClient getTcpClient(TcpServerInfo targetInfo) {
        for (TcpClient tc : sMXTcpClients) {
            if (tc.getServerInfo().equals(targetInfo)) {
                return tc;
            }
        }
        return null;
    }
}
