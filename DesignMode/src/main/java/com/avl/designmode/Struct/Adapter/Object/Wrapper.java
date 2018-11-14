package com.avl.designmode.Struct.Adapter.Object;

import com.avl.designmode.Struct.Adapter.Class.Source;
import com.avl.designmode.Struct.Adapter.Class.Targetable;

public class Wrapper implements Targetable
{
    private Source source;

    public Wrapper(Source source) {
        this.source = source;
    }

    @Override
    public void method1() {
        source.method1();
//        System.out.println("Wrapper method1");
    }

    @Override
    public void method2() {
        System.out.println("Wrapper method2");
    }
}
