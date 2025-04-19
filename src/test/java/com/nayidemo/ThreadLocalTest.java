package com.nayidemo;

import com.nayidemo.juc.ThreadLocal.ThreadLocalDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// JUnit不走SpringIoC SpringAOP无法识别 需要借助Bean 所以要使用SpringBootTest
@SpringBootTest
public class ThreadLocalTest {
    @Autowired
    private ThreadLocalDemoService demoService;
    @Test
    public void clearTest(){
        // 这里注意 注解不能用于测试类内部方法 因为类内部方法调用，不走Spring AOP代理
        demoService.demo();
    }

}
