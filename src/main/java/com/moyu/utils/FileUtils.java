package com.moyu.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Configuration
public class FileUtils {

    @Autowired
    private HttpClientUtil httpClientUtil;
    @Resource
    private ResourceLoader resourceLoader;


    public boolean downloadFile(String httpUrl, String fileName, String savePath) throws IOException {
        return downloadFile(httpUrl, fileName, savePath, null);
    }

    /**
     * 根据https地址从远端下载文件
     *
     * @param fileName
     * @param httpUrl
     * @throws IOException
     */
    public boolean downloadFile(String httpUrl, String fileName, String savePath, Map<String, String> headers) throws IOException {
        ByteArrayOutputStream outputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            outputStream = httpClientUtil.doGetDownloadFile(httpUrl, headers);
            if (outputStream == null) {
                log.error("-----downloadFile失败-----,error={}");
                return false;
            }
            byte[] bytes = outputStream.toByteArray();
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            fileOutputStream = new FileOutputStream(saveDir + "/" + fileName);
            fileOutputStream.write(bytes);
        } catch (Exception e) {
            log.error("downloadFile出错了，info====={}", e);
        } finally {
            if (outputStream != null)
                outputStream.close();
            if (outputStream != null)
                fileOutputStream.close();
        }
        return true;
    }

    /**
     * 从静态资源下载文件
     *
     * @param fileName
     * @param path
     * @param response
     */
    public void downloadInsertExcelTemplate(String fileName, String path, HttpServletResponse response) {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:" + path);
            inputStream = resource.getInputStream();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取CSV文件
     *
     * @param filepath
     */
    public void readCSVFile(String filepath) {
        //String filepath = "C:\\Users\\1\\Desktop\\新建文本文档.csv";
        File csv = new File(filepath); // CSV文件路径
        csv.setReadable(true);//设置可读‪‪‪
        csv.setWritable(true);//设置可写‪‪‪‪
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = "";
        String everyLine = "";
        ArrayList<String> allString = new ArrayList<>();
        try {
            while ((line = br.readLine()) != null) // 读取到的内容给line变量
            {
                everyLine = line;
                System.out.println(everyLine);
                allString.add(everyLine);
            }
            System.out.println("csv表格中所有行数：" + allString.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @throws IOException
     */
    public static InputStream downLoadFromUrl(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        return inputStream;
    }


    public void downloadZipFile(HttpServletResponse response) {
        try {
            String filename = new String("文件名".getBytes("UTF-8"), "ISO8859-1");
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + filename);
            OutputStream os = response.getOutputStream();
            ZipOutputStream zos = new ZipOutputStream(os);
            zos.putNextEntry(new ZipEntry("文件名称"));
            //文件字节数组
            byte[] bytes = new byte[1024];
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
