package com.biz.login;

import com.network.BaseResponse;

public interface ILoginManager {
    public void loginHttpRequest(String userName, String password, LoginResponse response);

    public void loginTcpRequest(String userName, String password, LoginResponse response);

    public interface LoginResponse extends BaseResponse {

    }
}
