package com.example.ej.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.example.ej.entity.File;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件备份任务
 */
public class FileBackupJob implements SimpleJob {

    // 模拟文件列表
    public static List<File> files = new ArrayList<>();

    // 每次任务要备份的文件数量
    private final int COUNT = 1;


    // 要执行的任务逻辑
    @Override
    public void execute(ShardingContext shardingContext) {
        // 获取要备份的文件列表
        List<File> list = fetchUpBackupFiles(COUNT);

        // 执行文件备份
        backupFile(list);

    }

    // 获取未备份的文件
    public List<File> fetchUpBackupFiles(int count) {
        ArrayList<File> list = new ArrayList<>();
        int num = 0;
        for (File file : files) {
            if (!file.getIsBackup()) {
                list.add(file);
                num++;
            }
            if (num == count) {
                break;
            }
        }
        System.out.printf("%s, 获取文件%d个%n", LocalDateTime.now(), num);
        return list;
    }

    // 备份文件
    public void backupFile(List<File> list) {
        list.forEach(i -> {
            i.setIsBackup(true);
            System.out.printf("%s, 备份文件 名称:%s, 类型: %s%n", LocalDateTime.now(), i.getName(), i.getType());
        });

    }


}
