package com.nayidemo.structure;

import org.openjdk.jol.vm.VM;

import java.util.ArrayList;
import java.util.List;

public class arrayListDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        System.out.println(list.size());
//        VM.current().sizeOf(list);
        list.add("a");
        list.add("b");
        System.out.println(list.size());
//        VM.current().sizeOf(list);

        // 对于ArrayList 最好提前分配足够容量 防止发生连续扩容

    }

}
