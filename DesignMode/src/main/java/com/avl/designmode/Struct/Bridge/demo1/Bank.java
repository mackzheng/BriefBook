package com.avl.designmode.Struct.Bridge.demo1;

public abstract class Bank {

   protected Account account;

    public Bank(Account account)
    {
        this.account = account;
    }

    public abstract Account openAccount();
}
