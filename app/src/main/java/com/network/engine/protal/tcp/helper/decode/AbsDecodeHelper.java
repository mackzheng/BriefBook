package com.network.engine.protal.tcp.helper.decode;

import com.network.engine.protal.tcp.TcpClientConfig;
import com.network.engine.protal.tcp.TcpServerInfo;

/**
 * 解析消息的处理
 */
public interface AbsDecodeHelper {
    /**
     *
     * @param data  完整的数据包
     * @param targetInfo    对方的信息(ip/port)
     * @param tcpConnConfig    tcp连接配置，可自定义
     * @return
     */
    byte[][] execute(byte[] data, TcpServerInfo targetInfo, TcpClientConfig tcpConnConfig);
}
