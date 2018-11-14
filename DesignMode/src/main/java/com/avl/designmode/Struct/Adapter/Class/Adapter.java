package com.avl.designmode.Struct.Adapter.Class;

public class Adapter extends Source implements Targetable {
    @Override
    public void method2() {
        System.out.println("adapter method2");
    }
}
