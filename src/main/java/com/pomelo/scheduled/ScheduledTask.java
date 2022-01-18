package com.pomelo.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName ScheduledTask
 * @Author weijian
 * @Date 2021/8/25
 */

//@Component
public class ScheduledTask {

    @Scheduled(fixedRate = 3000)
    public void scheduledTask() {
        System.out.println("任务执行时间：" + LocalDateTime.now());
    }

}
