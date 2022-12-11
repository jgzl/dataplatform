package cn.cleanarch.dp.message;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class CountDownLatchDemo {
    public static void main(String[] args) {
        ThreadFactory threadFactory = ThreadUtil.newNamedThreadFactory("countdownlatch-pool-", false);
        ExecutorService service = Executors.newCachedThreadPool(threadFactory);
        CountDownLatch cp = new CountDownLatch(1);
        CountDownLatch ydy = new CountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        log.info("运动员:{}正在等待裁判命令",Thread.currentThread().getName());
                        cp.await();
                        log.info("运动员:{}接收到裁判命令",Thread.currentThread().getName());
                        Thread.sleep(new Random().nextLong(1000)*10);
                        log.info("运动员:{}到达终点",Thread.currentThread().getName());
                        ydy.countDown();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            service.execute(runnable);
        }
        log.info("裁判:{}准备发布命令",Thread.currentThread().getName());
        cp.countDown();
        log.info("裁判:{}已经发出命令,等待运动员到达终点",Thread.currentThread().getName());
        try {
            ydy.await();
            log.info("所有运动员到达终点");
            log.info("裁判:{}汇总所有排名",Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        service.shutdown();
    }
}
