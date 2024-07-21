package com.moyu.service.impl;

import com.moyu.mapper.DownFileMapper;
import com.moyu.utils.FileUtils;
import com.moyu.utils.HttpClientUtil;
import com.moyu.utils.HttpRequestUtil;
import com.moyu.vo.DownloadFileVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

@Slf4j
@Service
public class FileService {

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private DownFileMapper downFileMapper;

    private volatile boolean runStatus = true;


    /**
     * 单文件夹下载图片
     *
     * @param fileVo
     */
    @Async
    public void downFileFromUrl(DownloadFileVo fileVo, Map<String, String> headers) {
        //下载的文件集名称
        String folderStr = fileVo.getFolderStr();
        Integer picIndex = 1;
        boolean haveNext = true;
        try {
            while (haveNext && runStatus) {
                log.info("folderStr==={}, picIndex==={}", folderStr, picIndex);
                String picIndexStr = String.format("%04d", picIndex);
                String httpUrl = fileVo.getHttpUrl() + folderStr + "/" + picIndexStr + ".jpg";
                String fileName = httpUrl.substring(httpUrl.lastIndexOf("/") + 1);
                String savePath = fileVo.getSavePath() + "/" + folderStr;
                boolean success = fileUtils.downloadFile(httpUrl, fileName, savePath, headers);
                if (success) {
                    picIndex++;
                } else {
                    log.info("下载完成,一共{}个文件", picIndex);
                    return;
                }
            }
        } catch (IOException e) {
            log.error("存储文件失败了=={}", e);
        }
    }

    /**
     * 循环多文件夹下载图片
     *
     * @param fileVo
     */
    @Async
    public void downFolderFromUrl(DownloadFileVo fileVo) {
        Integer folderNo = fileVo.getFolderNo();
        Integer picIndex = 1;
        boolean haveNext = true;
        try {
            while (haveNext && runStatus) {
                log.info("folderNo==={},picIndex==={}", folderNo, picIndex);
                String httpUrl = fileVo.getHttpUrl() + folderNo + "/" + picIndex + ".jpg";
                String fileName = httpUrl.substring(httpUrl.lastIndexOf("/") + 1);
                boolean success = fileUtils.downloadFile(httpUrl, fileName, fileVo.getSavePath() + "/" + folderNo);
                if (success) {
                    picIndex++;
                } else {
                    picIndex = 1;
                    folderNo++;
                }
                if (folderNo > fileVo.getEndFolderNo()) {
                    haveNext = false;
                }
            }
        } catch (IOException e) {
            downFileMapper.insertData(folderNo.toString());
            log.error("存储文件失败了=={}", e);
        }
    }

    public void runStatus(Integer run) {
        if (run == 0) {
            this.runStatus = false;
        } else {
            this.runStatus = true;
        }
    }


    /**
     * 下载ts文件
     *
     * @param fileVo
     * @throws IOException
     */
    @Async
    public void downloadTsFile(DownloadFileVo fileVo) throws IOException {
        boolean haveNext = true;
        Integer tsIndex = 0;

        String saveFileName = makeTsFileName(fileVo.getHttpUrl());
        File saveDir = new File(fileVo.getSavePath());
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(saveDir + "/" + saveFileName);

        ByteArrayOutputStream byteArrayOutputStream = null;

        //将多个ts缓存到list
        while (haveNext) {
            try {
                String fileName = makeTsIndex(tsIndex) + ".ts";
                String url = fileVo.getHttpUrl() + fileName;
                log.info("url==={}", url);
                //fileOutputStream = new FileOutputStream(fileVo.getSavePath() + fileName);
                byteArrayOutputStream = httpClientUtil.doGetDownloadIgnoreVerify(url);
                if (byteArrayOutputStream == null) {
                    haveNext = false;
                } else {
                    tsIndex++;
                    fileOutputStream.write(byteArrayOutputStream.toByteArray());
                    fileOutputStream.flush();
                }
            } catch (Exception e) {
                log.error("downloadTsFile下载ts文件出错了，info====={}", e);
            } finally {
                if (byteArrayOutputStream != null)
                    byteArrayOutputStream.close();
            }
        }

        if (fileOutputStream != null)
            fileOutputStream.close();
        log.info(saveFileName + "执行结束");

    }

    /**
     * @param fileVo
     * @throws IOException
     */
    public void downloadWebFile(DownloadFileVo fileVo) throws IOException {
        Integer folderNo = fileVo.getFolderNo();
        Integer endFolderNo = fileVo.getEndFolderNo();

        ByteArrayOutputStream outputStream = null;
        while (folderNo <= endFolderNo) {
            log.info("index===={}", folderNo);
            try {
                String httpUrl = fileVo.getHttpUrl() + folderNo + ".html";
                outputStream = httpClientUtil.doGetDownloadIgnoreVerify(httpUrl);
                if (outputStream == null) {
                    folderNo++;
                    continue;
                }
                byte[] bytes = outputStream.toByteArray();
                String webString = new String(bytes);
                int m3u8Index = webString.indexOf("m3u8");
                if (m3u8Index < 0) {
                    folderNo++;
                    continue;
                }
                String m3u8String = webString.substring(0, m3u8Index);
                int httpIndex = m3u8String.lastIndexOf("http");
                String url = m3u8String.substring(httpIndex, m3u8Index);
                System.out.println(url);
            } catch (Exception e) {
                log.error("downloadWebFile出错了，info==={}", e);
            } finally {
                folderNo++;
                if (outputStream != null)
                    outputStream.close();
            }
        }
    }

    public void downloadWebFile2(DownloadFileVo fileVo) throws IOException {
        String saveFileName = "aaa.txt";
        File saveDir = new File(fileVo.getSavePath());
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        FileOutputStream fileOutputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            fileOutputStream = new FileOutputStream(saveDir + "/" + saveFileName);
            outputStream = httpClientUtil.doGetDownloadIgnoreVerify(fileVo.getHttpUrl());
            fileOutputStream.write(outputStream.toByteArray());
        } catch (Exception e) {

        } finally {
            if (fileOutputStream != null)
                fileOutputStream.close();
            if (outputStream != null)
                outputStream.close();
        }
    }

    public String makeTsIndex(Integer index) {
        if (index < 10) {
            return "00" + index;
        } else if (index < 100) {
            return "0" + index;
        } else {
            return index.toString();
        }
    }

    public String makeTsFileName(String url) {
        int outIndex = url.lastIndexOf("/out");
        String substring = url.substring(0, outIndex);
        int nameIndex = substring.lastIndexOf("/");
        return substring.substring(nameIndex + 1) + ".mp4";
    }


    public void downloadTsFile2(String httpUrl) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = httpClientUtil.doGetDownloadIgnoreVerify(httpUrl);
        if (byteArrayOutputStream == null) {
            return;
        }
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\test22\\out.ts");
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();
        fileOutputStream.close();
        byteArrayOutputStream.close();
    }


}
