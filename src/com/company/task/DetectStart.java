package com.company.task;

import com.company.tool.AlertConfig;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

import static org.quartz.JobBuilder.newJob;

/**
 * 检测数据库状态的定时任务
 * @author 杨佳颖
 */
public class DetectStart {
    public static void  job() throws SchedulerException {
        Properties properties = AlertConfig.readWaitTime();
        String time = properties.getProperty("waitTime");
        //创建调度器
        //将具体的作业类（RamJob）绑定到调度任务详情中
        //创建触发器
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        JobDetail jobDetail = newJob(Detect.class)
                // 任务名，任务组
                .withIdentity("detect")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/60 * * * ? *"))
                .withSchedule(CronScheduleBuilder.cronSchedule(time))
                .build();
        //将触发器以及调度任务详情绑定到调度器上
        scheduler.scheduleJob(jobDetail, trigger);
        //启动调度器
        scheduler.start();
    }
}
