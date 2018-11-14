package com.avl.designmode.Struct.Proxy.demo1.Static;

import com.avl.designmode.Struct.Proxy.demo1.IOrderService;
import com.avl.designmode.Struct.Proxy.demo1.Order;
import com.avl.designmode.Struct.Proxy.demo1.OrderServiceImpl;

public class OrderServiceStaticProxy {

    private IOrderService  iOrderService;
    public int saveOrder(Order order)
    {
        beforeMethod();
        iOrderService =  new OrderServiceImpl();
        int userId = order.getUserId();
        int dbRoute = userId%2;

        System.out.println("order = [" + order + "]");

        return 0;
    }


    private void beforeMethod()
    {
        System.out.println("before");
    }

    private void afterMethod()
    {
        System.out.println("after");
    }

}
