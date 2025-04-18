package com.nayidemo.juc.myThreadPool;

public class Main {
    public static void main(String[] args) {
        MyThreadPool pool = new MyThreadPool(5,10);
        //1.第一个核心方法 启动
        pool.start();
        for (int i = 0; i < 10; i++) {
            final int id = i;
            //2.第二个核心方法  提交任务 Runnable task
            pool.submit(()->{
                System.out.println("正在执行"+id+"个任务");
            });
        }
        //3.第三个核心方法 关闭
        pool.shutdown();
    }
}
