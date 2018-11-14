package com.avl.designmode.Struct.Bridge.demo1;

public class DepositAccount implements Account {
    @Override
    public Account openAccount()
    {
        System.out.println("打开定期账号");
        return null;
    }

    @Override
    public void showAccountType() {
        System.out.println("这是一个定期账号");

    }
}
