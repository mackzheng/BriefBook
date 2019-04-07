package com.network.engine.protal.tcp.helper.decode;

import com.network.engine.protal.tcp.TcpClientConfig;
import com.network.engine.protal.tcp.TcpServerInfo;

public class BaseDecodeHelper implements AbsDecodeHelper {
    @Override
    public byte[][] execute(byte[] data, TcpServerInfo targetInfo, TcpClientConfig tcpConnConfig) {
        return new byte[][]{data};
    }
}
