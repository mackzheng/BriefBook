package com.avl.javabasics.Lock;


import java.util.concurrent.atomic.AtomicInteger;

public class TicketLock {
    private AtomicInteger serviceNum = new AtomicInteger();
    private AtomicInteger ticketNum = new AtomicInteger();

    private static final ThreadLocal<Integer> LOCAL = new ThreadLocal<>();

    public void lock() {
        int myticker = ticketNum.getAndIncrement();

        LOCAL.set(myticker);

        while (myticker != serviceNum.get()) {

        }
    }

    public void unlock() {
        int myticker = LOCAL.get();
        serviceNum.compareAndSet(myticker, myticker + 1);
    }
}
