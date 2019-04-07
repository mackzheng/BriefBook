package com.network;

public interface BaseResponse {
    public void onSuccess();
    public void onFailed();
    public void onTimeout();
}
