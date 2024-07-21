package com.moyu.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class HttpRequestUtil {
    /**
     * 发送HTTP GET请求
     * @param imageUrl 请求的URL
     * @param headers 请求头的键值对
     * @return 响应内容
     * @throws Exception
     */
    public ByteArrayOutputStream sendGet(String imageUrl, Map<String, String> headers) throws Exception {

        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {

            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
//            connection.setRequestProperty("Cookie", cookie);
            inputStream = connection.getInputStream();
            outputStream = new ByteArrayOutputStream();

            int bytesRead = 0;
            byte[] buffer = new byte[2048];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            return outputStream;

        } catch (Exception e) {
            log.error("e==={}", e);
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
