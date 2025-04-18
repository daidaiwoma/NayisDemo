package com.nayidemo.JVM;

import java.util.ArrayList;
import java.util.List;

public class OOMDemo {
    public static void main(String[] args) {
        //1.定义一个字节数组类型的list 作为我们的试验品
        List<byte[]> list = new ArrayList<>();
        try {
            while(true){
                //2.持续分配大小为1M的字节数组
                list.add(new byte[1024*1024]);
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

    }
}
