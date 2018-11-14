package com.avl.designmode.Struct.Proxy.demo1;

public class OrderServiceImpl implements IOrderService {

    IOrderDao  iOrderDao;

    @Override
    public int saveOrder(Order order) {
        iOrderDao = new OrderDaoImpl();
        System.out.println("service order = [" + order + "]");
        return 0;
    }
}
