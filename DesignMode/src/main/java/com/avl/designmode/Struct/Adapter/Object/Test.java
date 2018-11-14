package com.avl.designmode.Struct.Adapter.Object;

import com.avl.designmode.Struct.Adapter.Class.Source;
import com.avl.designmode.Struct.Adapter.Class.Targetable;

public class Test {

    public static void main(String[] args) {
        Source source = new Source();
        Targetable targetable = new Wrapper(source);
        targetable.method1();
        targetable.method2();
    }
}
