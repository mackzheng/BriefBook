package com.avl.designmode.Other.Producer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable{

    private volatile boolean isRunning = true;
    private BlockingQueue<PCData> queue;
    private static AtomicInteger count = new AtomicInteger();
    private static final int SLEEPTIME = 1000;

    public Producer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        PCData data = null;
        Random r = new Random();
        System.out.println("start producting id:"+Thread.currentThread().getId());

        try
        {
            while (isRunning)
            {
                Thread.sleep(SLEEPTIME);
                data = new PCData(count.incrementAndGet());
                System.out.println(data+"加入队列");
                if(!queue.offer(data,2, TimeUnit.SECONDS))
                {
                    System.out.println("加入队列失败");
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }


    public void stop()
    {
        isRunning = false;
    }

}
