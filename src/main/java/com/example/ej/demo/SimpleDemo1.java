package com.example.ej.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleDemo1 {
    public static void main(String[] args) throws SchedulerException {
        test4();


    }

    public static void test1() {
        // 任务间隔时间
        final long timeInterval = 3000;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println(LocalDateTime.now());
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public static void test2() {
        Timer timer1 = new Timer();
        Timer timer2 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(LocalDateTime.now());
            }
        }, 1000, 2000);

        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(LocalTime.now());
            }
        }, 1000, 3000);
    }

    public static void test3() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        service.scheduleAtFixedRate(() -> System.out.println(LocalDateTime.now()), 1, 2, TimeUnit.SECONDS);
    }

    public static void test4() throws SchedulerException {
        // 创建一个Scheduler
        StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 创建JobDetail
        JobBuilder jobBuilder = JobBuilder.newJob(MyJob.class);
        jobBuilder.withIdentity("jobName", "jobGroupname");
        JobDetail jobDetail = jobBuilder.build();
        // 创建触发的CronTrigger, 支持日历调度
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("triggerName", "triggerGoupName")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("1 * * * * ?"))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
