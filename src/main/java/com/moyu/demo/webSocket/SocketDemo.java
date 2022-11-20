package com.moyu.demo.webSocket;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;


public class SocketDemo {
    /**
     * 接接收UDP请收
     * @throws IOException
     */

    @Test
    public void receiveUDPSocket() throws IOException {
        DatagramSocket ds = new DatagramSocket(10086);

        byte[] bytes = new byte[1024];
        int length = bytes.length;
        DatagramPacket dp = new DatagramPacket(bytes,length);

        ds.receive(dp);

        InetAddress address = dp.getAddress();
        String ip = address.getHostAddress();

        byte[] data = dp.getData();
        int len = dp.getLength();
        String string = new String(data, 0, len);
        System.out.println(ip+"-----------"+string);


        //释放资源
        System.out.println("要释放资源了");
        ds.close();


    }

    /**
     * 发送UDP请求
     * @throws IOException
     */
    @Test
    public void sendUDPSocketDemo() throws IOException {


        DatagramSocket ds = new DatagramSocket();

        byte[] bytes = "发送个数据测试玩玩".getBytes();
        int length = bytes.length;
        InetAddress address = InetAddress.getByName("192.168.2.117");
        int port = 10086;
        DatagramPacket dp = new DatagramPacket(bytes,length,address,port);

        ds.send(dp);

        //释放资源
        ds.close();

    }


    /**
     * 接收TCP请求
     * @throws IOException
     */
    @Test
    public void receiveTCPSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        //监听客户端连接
        Socket accept = serverSocket.accept();

        //获取输入流
        InputStream inputStream = accept.getInputStream();

        byte[] bytes = new byte[1024];
        int read = inputStream.read(bytes);//阻塞式方法
        String str = new String(bytes, 0, read);

        String ip = serverSocket.getInetAddress().getHostAddress();

        System.out.println(ip+"---"+str);


        //接收到数据后返回给客户端一句话
        OutputStream outputStream = accept.getOutputStream();
        outputStream.write("我已经收到数据了".getBytes());


        inputStream.close();

    }


    /**
     * 发送TCP请求
     * @throws IOException
     */
    @Test
    public void sendTCPSocket() throws IOException {

        Socket socket = new Socket("192.168.12.92", 8080);

        //写发送的数据
        OutputStream os = socket.getOutputStream();
        os.write("tcp测试demo".getBytes());

        //接受返回的结果信息
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int read = inputStream.read(bytes);
        String s = new String(bytes, 0, read);
        System.out.println(s);


        socket.close();

    }



}
