package com.nayidemo.juc.myThreadPool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// 定义我的线程池
public class MyThreadPool {
    private final int poolSize;
    private final BlockingQueue<Runnable> taskQueue;
    private final List<WorkerThread> workersList;

    public MyThreadPool(int poolSize, int queueSize) {
        this.poolSize = poolSize;
        this.taskQueue = new LinkedBlockingQueue<Runnable>(queueSize);
        this.workersList = new LinkedList<WorkerThread>();
    }

    // 线程池生命周期管理：
    // 1.初始化
    public void start() {
        for (int i = 0; i < poolSize; i++) {
            // 调用WorkerThread的构造函数 让线程处理taskQueue中的task
            WorkerThread worker = new WorkerThread(taskQueue);
            workersList.add(worker);
            worker.start();
        }
    }
    // 2.提交任务到队列
    public void submit(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    // 3.关闭线程池
    public void shutdown() {
        for(WorkerThread worker : workersList) {
            worker.interrupt();
        }
    }

}
