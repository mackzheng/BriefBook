package com.network.engine.protal.udp;

import java.util.HashSet;
import java.util.Set;

/**
 * tcpclient的管理者
 */
public class UdpClientManager {
    private static Set<UdpClient> sMXUdpClients = new HashSet<>();

    public static void putUdpClient(UdpClient tcpClient) {
        sMXUdpClients.add(tcpClient);
    }

    public static UdpClient getUdpClient(UdpServerInfo targetInfo) {
        for (UdpClient tc : sMXUdpClients) {
            if (tc.getmTargetInfo().equals(targetInfo)) {
                return tc;
            }
        }
        return null;
    }
}
