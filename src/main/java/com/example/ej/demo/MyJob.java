package com.example.ej.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.time.LocalDateTime;

public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println(LocalDateTime.now());
    }
}
