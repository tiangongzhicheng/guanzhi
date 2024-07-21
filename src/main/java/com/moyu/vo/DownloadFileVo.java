package com.moyu.vo;

import lombok.Data;

@Data
public class DownloadFileVo {
    private String httpUrl;
    private String savePath;
    private String folderStr;
    private Integer folderNo;
    private Integer endFolderNo;
}
