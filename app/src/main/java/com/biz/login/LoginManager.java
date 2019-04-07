package com.biz.login;

import com.network.BaseManager;
import com.network.BaseRequest;

public class LoginManager extends BaseManager implements ILoginManager{

    private static LoginManager loginManager = new LoginManager();

    private LoginManager()
    {
    }

    public static LoginManager getLoginManager() {
        return loginManager;
    }

    @Override
    public void loginHttpRequest(String userName, String password, LoginResponse response) {
        BaseRequest request = new BaseRequest();
        sendHttp(request,response); //send是一个异步接口
    }

    @Override
    public void loginTcpRequest(String userName, String password, LoginResponse response) {
        BaseRequest request = new BaseRequest();
        sendTcp(request,response); //send是一个异步接口
    }
}