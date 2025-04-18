package com.nayidemo.juc;

public class RaceConditionDemo {
    // 多线程安全问题 -- 竞态条件
    public static void main(String[] args) {
        BankAccount acc = new BankAccount();
        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                acc.withdraw(300);
            }
        };
        Thread thread1 = new Thread(task, "Thread1");
        Thread thread2 = new Thread(task, "Thread2");

        thread1.start();
        thread2.start();
    }

    public static class BankAccount {
        private int balance = 1000;

        // 可以在方法加锁 但是我认为这样锁粒度可能有点大
        public void withdraw(int amount) {
            synchronized (this) {
                if (balance < amount) {
                    System.out.println("Insufficient Balance");
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    balance -= amount;
                    System.out.println("转出者" + Thread.currentThread().getName() + "  balance:" + balance);
                }
            }
        }
    }
}
