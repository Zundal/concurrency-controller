package com.flexsible.concurrency.cupon.lock;

import com.flexsible.concurrency.util.RedisDistributedLock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class DistributedLockTest {

    @Autowired
    private RedisDistributedLock distributedLock;

    @Test
    void testDistributedLock()
            throws InterruptedException {

        String lockKey = "resourceLock";
        String lockValue = "uniqueIdentifier";

        Long lockTimeout = 10L;

        Runnable task = () -> {

            if (distributedLock.acquireLock(lockKey, lockValue, lockTimeout)) {

                try {
                    System.out.println(
                            Thread.currentThread().getName() + " acquired the lock");

                    Thread.sleep(5000);

                } catch (InterruptedException e) {

                    e.printStackTrace();

                } finally {

                    distributedLock.releaseLock(lockKey);

                    System.out.println(
                            Thread.currentThread().getName() +
                                    " released the lock");

                }
            } else {

                System.out.println(
                        Thread.currentThread().getName() +
                                " could not acquire the lock");

            }
        };

        Executors.newFixedThreadPool(2).submit(task);
        Executors.newFixedThreadPool(2).submit(task);

        TimeUnit.SECONDS.sleep(20);
    }
}
