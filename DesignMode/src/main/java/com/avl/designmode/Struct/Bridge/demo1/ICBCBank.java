package com.avl.designmode.Struct.Bridge.demo1;

public class ICBCBank extends Bank {

    public ICBCBank(Account account) {
        super(account);
    }

    @Override
    public Account openAccount() {
        System.out.println("打开中国农业银行");
        account.openAccount();
        return account;
    }
}
