package com.example.demo.common;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * description: redis锁实现类
 * create: 2020/3/13 11:52
 *
 * @author NieMingXin
 * @version 1.0
 */
@Component
public class RedisDistributedLocker implements DistributedLocker {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean lock(String lockKey) {
        //如果锁存在,并且不判断锁的状态直接上锁，那么当前线程会一直等待redis中的key到期
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.isLocked()) {
            return false;
        }
        lock.lock();
        return lock.isLocked();
    }

    @Override
    public boolean lock(String lockKey, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        //如果锁存在,并且不判断锁的状态直接上锁，那么当前线程会一直等待redis中的key到期
        if (lock.isLocked()) {
            return false;
        }
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock.isLocked();
    }

    @Override
    public boolean lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        //如果锁存在,并且不判断锁的状态直接上锁，那么当前线程会一直等待redis中的key到期
        if (lock.isLocked()) {
            return false;
        }
        lock.lock(timeout, unit);
        return lock.isLocked();
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }
}
