package com.nayidemo.juc.myThreadPool;

import java.util.concurrent.BlockingQueue;

public class WorkerThread extends Thread {
    private final BlockingQueue<Runnable> taskQueue;

    // 初始化工作队列
    public WorkerThread(BlockingQueue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }

    // 每个线程不断地从任务队列中获取任务并执行
    @Override
    public void run() {
        while (true) {
            try {
                // 阻塞获取工作队列中的task（Runnable类型）
                Runnable task = taskQueue.take();
                task.run();
            } catch (InterruptedException e) {
                // 线程中断 退出当前循环
                Thread.currentThread().interrupt();
                break;
            }

        }
    }
}
