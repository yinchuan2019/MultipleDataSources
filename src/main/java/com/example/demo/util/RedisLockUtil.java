package com.example.demo.util;


import com.example.demo.common.DistributedLocker;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * description:redis分布式锁 redisson实现
 * create: 2020/3/13 11:45
 *
 * @author NieMingXin
 * @version 1.0
 */
@Component
public class RedisLockUtil {


    @Autowired
    private DistributedLocker locker;

    private static DistributedLocker distributedLocker;


    @PostConstruct
    private void init() {
        distributedLocker = locker;
    }


    /**
     * create: 2020/3/13 12:31
     * description: 加锁
     *
     * @param lockKey: redisKey
     * @return boolean
     * @author niemingxin
     */
    public static boolean lock(String lockKey) {
        return distributedLocker.lock(lockKey);
    }

    /**
     * create: 2020/3/13 12:31
     * description: 释放锁
     *
     * @param lockKey: redisKey
     * @author niemingxin
     */
    public static void unlock(String lockKey) {
        distributedLocker.unlock(lockKey);
    }

    /**
     * create: 2020/3/13 13:10
     * description: 解锁
     *
     * @param lock:RLock对象
     * @author niemingxin
     */
    public static void unlock(RLock lock) {
        distributedLocker.unlock(lock);
    }


    /**
     * create: 2020/3/13 12:31
     * description: 加锁
     *
     * @param lockKey: redisKey
     * @param timeout: 超时时间(秒)
     * @return boolean
     * @author niemingxin
     */
    public static boolean lock(String lockKey, int timeout) {
        return distributedLocker.lock(lockKey, timeout);
    }

    /**
     * create: 2020/3/13 12:33
     * description: 加锁
     *
     * @param lockKey: redisKey
     * @param timeout: 超时时间
     * @param unit:    时间单位
     * @return boolean
     * @author niemingxin
     */
    public static boolean lock(String lockKey, int timeout, TimeUnit unit) {
        return distributedLocker.lock(lockKey, unit, timeout);
    }

    /**
     * create: 2020/3/13 12:33
     * description: 尝试获取锁
     *
     * @param lockKey:   redisKey
     * @param waitTime:  最多等待时间(秒)
     * @param leaseTime: 上锁后自动释放锁时间(秒)
     * @return boolean
     * @author niemingxin
     */
    public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        return distributedLocker.tryLock(lockKey, TimeUnit.SECONDS, waitTime, leaseTime);
    }


    /**
     * create: 2020/3/13 12:34
     * description: 尝试获取锁
     *
     * @param lockKey:   redisKey
     * @param unit:      时间单位
     * @param waitTime:  最多等待时间
     * @param leaseTime: 上锁后自动释放锁时间
     * @return boolean
     * @author niemingxin
     */
    public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        return distributedLocker.tryLock(lockKey, unit, waitTime, leaseTime);
    }
}