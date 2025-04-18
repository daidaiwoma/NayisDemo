package com.nayidemo.structure;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CHMDemo {
    public static void main(String[] args) throws InterruptedException {
        Map<Integer,String> map1 = new HashMap<Integer,String>();
        // concurrent hashmap
        Map<Integer,String> ccMap = new ConcurrentHashMap<Integer,String>();
        System.out.println("begin");
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
//                    map1.put(i,"thread1:"+i);
                    ccMap.put(i,"cc");
//                    System.out.println(i+ccMap.get(i));
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
//                    map1.put(i,"thread2:"+i);
                    ccMap.put(i,"dd");
//                    System.out.println(i+ccMap.get(i));
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(ccMap.size());
        System.out.println(ccMap);

    }
}
