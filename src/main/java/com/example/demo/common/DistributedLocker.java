package com.example.demo.common;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * description: redis锁
 * create: 2020/3/13 11:52
 *
 * @author NieMingXin
 * @version 1.0
 */
public interface DistributedLocker {
    /**
     * create: 2020/3/13 13:01
     * description: 加锁
     *
     * @param lockKey: redisKey
     * @return boolean 是否拿到锁(加锁是否成功)
     * @author niemingxin
     */
    boolean lock(String lockKey);

    /**
     * create: 2020/3/13 12:36
     * description: 加锁
     *
     * @param lockKey:redisKey
     * @param timeout:锁的生存时间(秒)
     * @return boolean 是否拿到锁(加锁是否成功)
     * @author niemingxin
     */
    boolean lock(String lockKey, int timeout);

    /**
     * create: 2020/3/13 13:01
     * description: 加锁
     *
     * @param lockKey:redisKey
     * @param unit:时间单位
     * @param timeout:锁的生存时间(秒)
     * @return boolean 是否拿到锁(加锁是否成功)
     * @author niemingxin
     */
    boolean lock(String lockKey, TimeUnit unit, int timeout);

    /**
     * create: 2020/3/13 12:36
     * description: 加锁
     *
     * @param lockKey:redisKey
     * @param unit:时间单位
     * @param waitTime:未获取到锁等待的最长时间
     * @param leaseTime:锁的生存时间(如果获取锁加锁成功)
     * @return boolean 是否拿到锁(加锁是否成功)
     * @author niemingxin
     */
    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    /**
     * create: 2020/3/13 12:36
     * description: 解锁
     *
     * @param lockKey:redisKey
     * @author niemingxin
     */
    void unlock(String lockKey);

    /**
     * create: 2020/3/13 12:37
     * description: 解锁
     *
     * @param lock:Rlock对象
     * @author niemingxin
     */
    void unlock(RLock lock);
}