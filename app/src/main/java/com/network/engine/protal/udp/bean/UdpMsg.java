package com.network.engine.protal.udp.bean;


import com.network.engine.protal.tcp.bean.TcpMsg;
import com.network.engine.protal.udp.UdpServerInfo;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public class UdpMsg{

    public enum MsgType {
        Send, Receive
    }

    private static final AtomicInteger IDAtomic = new AtomicInteger();
    private int id;
    private byte[] sourceDataBytes;//数据源
    private String sourceDataString;//数据源
    private UdpServerInfo target;
    private long time;//发送、接受消息的时间戳
    private TcpMsg.MsgType mMsgType = TcpMsg.MsgType.Send;
    private byte[][] endDecodeData;

    public UdpMsg(int id) {
        this.id = id;
    }

    public UdpMsg(byte[] data, UdpServerInfo target, TcpMsg.MsgType type) {
        this.sourceDataBytes = data;
        this.target = target;
        this.mMsgType = type;
        init();
    }

    public UdpMsg(String data, UdpServerInfo target, TcpMsg.MsgType type) {
        this.target = target;
        this.sourceDataString = data;
        this.mMsgType = type;
        init();
    }

    public void setTime() {
        time = System.currentTimeMillis();
    }

    private void init() {
        id = IDAtomic.getAndIncrement();
    }

    public long getTime() {
        return time;
    }

    public byte[][] getEndDecodeData() {
        return endDecodeData;
    }

    public void setEndDecodeData(byte[][] endDecodeData) {
        this.endDecodeData = endDecodeData;
    }

    public TcpMsg.MsgType getMsgType() {
        return mMsgType;
    }

    public void setMsgType(TcpMsg.MsgType msgType) {
        mMsgType = msgType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UdpMsg tcpMsg = (UdpMsg) o;
        return id == tcpMsg.id;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (endDecodeData != null) {
            for (byte[] bs : endDecodeData) {
                sb.append(Arrays.toString(bs));
            }
        }
        return "TcpMsg{" +
                "sourceDataBytes=" + Arrays.toString(sourceDataBytes) +
                ", id=" + id +
                ", sourceDataString='" + sourceDataString + '\'' +
                ", target=" + target +
                ", time=" + time +
                ", msgtyoe=" + mMsgType +
                ", enddecode=" + sb.toString() +
                '}';
    }

    @Override
    public int hashCode() {
        return id;
    }


    public byte[] getSourceDataBytes() {
        return sourceDataBytes;
    }

    public void setSourceDataBytes(byte[] sourceDataBytes) {
        this.sourceDataBytes = sourceDataBytes;
    }

    public String getSourceDataString() {
        return sourceDataString;
    }

    public void setSourceDataString(String sourceDataString) {
        this.sourceDataString = sourceDataString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static AtomicInteger getIDAtomic() {
        return IDAtomic;
    }

    public UdpServerInfo getTarget() {
        return target;
    }

    public void setTarget(UdpServerInfo target) {
        this.target = target;
    }
}
