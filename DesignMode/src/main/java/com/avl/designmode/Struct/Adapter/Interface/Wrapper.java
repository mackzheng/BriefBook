package com.avl.designmode.Struct.Adapter.Interface;

public abstract class Wrapper implements Sourceable {
    @Override
    public void method1() {
        System.out.println("Wrapper method1");
    }

    @Override
    public void method2() {
        System.out.println("Wrapper method2");
    }
}
