package com.avl.designmode.Struct.Adapter.Class;

public class Test {
    public static void main(String[] args) {
        Targetable targetable = new Adapter();
        targetable.method1();
        targetable.method2();
    }
}
