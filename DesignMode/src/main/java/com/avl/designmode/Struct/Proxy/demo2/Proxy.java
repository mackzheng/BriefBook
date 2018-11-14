package com.avl.designmode.Struct.Proxy.demo2;

public class Proxy  implements Sourceable {

    private Source source;


    public Proxy() {
        source = new Source();
    }

    @Override
    public void method() {
        before();
        source.method();
        after();
    }


    private void before()
    {
        System.out.println("before proxy");
    }

    private void after()
    {
        System.out.println("after proxy");
    }
}
