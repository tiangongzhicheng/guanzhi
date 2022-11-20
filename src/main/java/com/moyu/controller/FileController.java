package com.moyu.controller;


import com.moyu.mapper.DownFileMapper;
import com.moyu.service.impl.FileService;
import com.moyu.vo.BaseResponse;
import com.moyu.vo.DownloadFileVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 下载图片
     * @param fileVo
     * @return
     */
    @RequestMapping("/downFileFromUrl")
    public BaseResponse downFileFromUrl(DownloadFileVo fileVo){
        try {
            fileService.downFileFromUrl(fileVo);
        }catch (Exception e){
            log.error("下载文件出错了===={}",e);
            return BaseResponse.getFailedRes("下载文件失败");
        }
        return BaseResponse.getSuccessRes();
    }

    @RequestMapping("/runStatus")
    public void runStatus(Integer run){
        fileService.runStatus(run);
    }


    /**
     * 下载Ts文件
     * @param fileVo
     * @return
     */
    @RequestMapping("/downloadTsFile")
    public BaseResponse downloadTsFile(DownloadFileVo fileVo){
        try {
            fileService.downloadTsFile(fileVo);
        }catch (Exception e){
            log.error("下载文件出错了===={}",e);
            return BaseResponse.getFailedRes("下载文件失败");
        }
        return BaseResponse.getSuccessRes();
    }


    /**
     * 获取网页数据
     * @param fileVo
     * @throws IOException
     */
    @RequestMapping("/downloadWebFile")
    public void downloadWebFile(DownloadFileVo fileVo) throws IOException {
        fileService.downloadWebFile(fileVo);
    }

}
