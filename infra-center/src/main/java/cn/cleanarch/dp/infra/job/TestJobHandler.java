package cn.cleanarch.dp.infra.job;

import cn.cleanarch.dp.common.job.annotation.XxlRegister;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestJobHandler {
    @XxlJob("TestJobHander-job1")
    @XxlRegister(cron = "0 1 * * * ?",author = "lihaifeng",jobDesc = "job1")
    public void job1() throws Exception{
        log.info("job1开始执行");
        Thread.sleep(60*1000);
        log.info("job1结束执行");
    }

    @XxlJob("TestJobHander-job2")
    @XxlRegister(cron = "0 1 * * * ?",author = "lihaifeng",jobDesc = "job2")
    public void job2() throws Exception{
        log.info("job2开始执行");
        Thread.sleep(30*1000);
        log.info("job2结束执行");
    }
}
