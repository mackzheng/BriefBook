package com.avl.designmode.Struct.Bridge.demo1;

public class Test {

    public static void main(String[] args)
    {
        Bank bank = new ABCBank(new DepositAccount());
        Account abcAccount = bank.openAccount();
        abcAccount.showAccountType();


        Bank bank1 = new ABCBank(new SavingAccount());
        Account abcAccount1 = bank1.openAccount();
        abcAccount1.showAccountType();
    }
}
