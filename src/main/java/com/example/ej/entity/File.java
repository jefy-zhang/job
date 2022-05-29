package com.example.ej.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {

    private Integer id;

    private String name;

    /**
     * 文件类型, 如text/image/radio/video
     */
    private String type;

    private String content;

    private Boolean isBackup;

}
