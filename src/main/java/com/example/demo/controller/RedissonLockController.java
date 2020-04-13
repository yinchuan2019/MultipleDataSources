package com.example.demo.controller;


import com.example.demo.util.RedisLockUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * description:
 * create: 2020/3/13 11:55
 *
 * @author NieMingXin
 * @version 1.0
 */
@RestController
@Api(tags = "redisson", description = "redis分布式锁控制器")
@RequestMapping("/redisson")
@Slf4j
public class RedissonLockController {

    /**
     * 锁测试共享变量
     */
    private Integer lockCount = 10;

    /**
     * 无锁测试共享变量
     */
    private Integer count = 10;

    /**
     * 模拟线程数
     */
    private static int threadNum = 10;

    /**
     * 模拟并发测试加锁和不加锁
     *
     * @return
     */
    @GetMapping("/test")
    @ApiOperation(value = "模拟并发测试加锁和不加锁")
    public void lock() {
        // 计数器
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < threadNum; i++) {
            MyRunnable myRunnable = new MyRunnable(countDownLatch);
            Thread myThread = new Thread(myRunnable);
            myThread.start();
        }
        // 释放所有线程
        countDownLatch.countDown();
    }

    @GetMapping("/test1")
    @ApiOperation(value = "测试")
    public void locsk() {
        boolean lock = RedisLockUtil.lock("test1", 10);
        if (lock) {
            System.out.println("获取锁成功");
            long start = System.currentTimeMillis();
            // 计数器
            boolean test = RedisLockUtil.tryLock("test", 5, 1000);
            if (!test) {
                System.out.println("try lock 失败 等待 " + (System.currentTimeMillis() - start) / 1000 + "秒");
            }
        } else {
            System.out.println("获取锁失败");
        }
    }

    /**
     * 加锁测试
     */
    private void testLockCount() {
        String lockKey = "lock-test";
        try {
            // 加锁，设置超时时间2s
            RedisLockUtil.tryLock(lockKey, 5, 2);
            lockCount--;
            log.info("lockCount值：" + lockCount);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 释放锁
            RedisLockUtil.unlock(lockKey);
        }
    }

    /**
     * 无锁测试
     */
    private void testCount() {
        count--;
        log.info("count值：" + count);
    }


    public class MyRunnable implements Runnable {
        /**
         * 计数器
         */
        final CountDownLatch countDownLatch;

        public MyRunnable(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                // 阻塞当前线程，直到计时器的值为0
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            // 无锁操作
            testCount();
            // 加锁操作
            testLockCount();
        }

    }

}
