package com.example.ej.config;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.example.ej.entity.File;
import com.example.ej.job.FileBackupJob;
import lombok.var;

//@Configuration
public class ZookeeperConfig {

    private static final String ZOOKEEPER_CONNECT_STRING = "localhost:2181";

    private static final String JOB_NAMESPACE = "elastic-job-example-java";

    // 启动任务
    public static void main(String[] args) {
        generateTestFiles();
        startJob(setupRegistryCenter());

    }

    private static void generateTestFiles() {
        for (int i = 1; i < 10; i++) {
            FileBackupJob.files.add(new File(i + 10, "文件" + (i + 10), "txt", "内容" + (i + 10), false));
            FileBackupJob.files.add(new File(i + 20, "文件" + (i + 20), "jpg", "内容" + (i + 20), false));
            FileBackupJob.files.add(new File(i + 30, "文件" + (i + 30), "mp3", "内容" + (i + 30), false));
            FileBackupJob.files.add(new File(i + 40, "文件" + (i + 40), "avi", "内容" + (i + 40), false));
        }
        System.out.println("zjf生产测试数据完成");

    }

    private static ZookeeperRegistryCenter setupRegistryCenter() {
        ZookeeperConfiguration config = new ZookeeperConfiguration(ZOOKEEPER_CONNECT_STRING, JOB_NAMESPACE);
        config.setSessionTimeoutMilliseconds(100);
        var zookeeperRegistryCenter = new ZookeeperRegistryCenter(config);
        zookeeperRegistryCenter.init();
        return zookeeperRegistryCenter;
    }

    private static void startJob(ZookeeperRegistryCenter registryCenter) {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder("files-job", "0/3 * * * * ?", 1).build();
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, FileBackupJob.class.getCanonicalName());
        new JobScheduler(registryCenter, LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build()).init();

    }


}

