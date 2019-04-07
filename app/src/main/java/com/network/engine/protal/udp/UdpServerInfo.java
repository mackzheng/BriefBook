package com.network.engine.protal.udp;

import com.network.engine.protal.tcp.util.ExceptionUtils;
import com.network.engine.protal.tcp.util.StringCheckUtils;

public class UdpServerInfo {

    private String ip;
    private int port;

    public UdpServerInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private void check() {
        if (!StringCheckUtils.validateRegex(port + "", StringCheckUtils.RegexPort)) {
            ExceptionUtils.throwException("port 格式出错");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UdpServerInfo that = (UdpServerInfo) o;

        if (port != that.port) return false;
        return ip != null ? ip.equals(that.ip) : that.ip == null;

    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + port;
        return result;
    }

    @Override
    public String toString() {
        return "TargetInfo{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
