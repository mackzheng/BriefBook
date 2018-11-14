package com.avl.designmode.Struct.Proxy.demo1;

public class OrderDaoImpl implements IOrderDao {
    @Override
    public int insert(Order order) {
        System.out.println("order = [" + order + "]");
        return 1;
    }
}
