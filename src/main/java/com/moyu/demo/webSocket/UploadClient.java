package com.moyu.demo.webSocket;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 按照我们正常的思路加入反馈信息，结果却没反应。为什么呢?
 * 读取文本文件是可以以null作为结束信息的，但是呢，通道内是不能这样结束信息的。
 * 所以，服务器根本就不知道你结束了。而你还想服务器给你反馈。所以，就相互等待了。
 *
 * 如何解决呢?
 * A:在多写一条数据，告诉服务器，读取到这条数据说明我就结束，你也结束吧。
 * 		这样做可以解决问题，但是不好。
 * B:Socket对象提供了一种解决方案
 * 		public void shutdownOutput()
 */



public class UploadClient {


	/**
	 *	模拟上传文件，并接收反馈结果
	 */
	public static void main(String[] args) throws IOException {
		// 创建客户端Socket对象
		Socket s = new Socket("192.168.12.92", 11111);

		// 封装文本文件
		BufferedReader br = new BufferedReader(new FileReader(
				"aa.java"));
		// 封装通道内流
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				s.getOutputStream()));

		String line = null;
		while ((line = br.readLine()) != null) { // 阻塞
			bw.write(line);
			bw.newLine();
			bw.flush();
		}

		//自定义一个结束标记
//		bw.write("over");
//		bw.newLine();
//		bw.flush();

		//Socket提供了一个终止，它会通知服务器你别等了，我没有数据过来了
		s.shutdownOutput();

		// 接收反馈
		BufferedReader brClient = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		String client = brClient.readLine(); // 阻塞
		System.out.println(client);

		// 释放资源
		br.close();
		s.close();
	}


	/**
	 * 模拟上传服务器，并反馈结果
	 * @throws IOException
	 */
	@Test
	public void uploadServer() throws IOException {
		// 创建服务器端的Socket对象
		ServerSocket ss = new ServerSocket(11111);

		// 监听客户端连接
		Socket s = ss.accept();// 阻塞

		// 封装通道内的流
		BufferedReader br = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		// 封装文本文件
		BufferedWriter bw = new BufferedWriter(new FileWriter("Copy.java"));

		String line = null;
		while ((line = br.readLine()) != null) { // 阻塞
			// if("over".equals(line)){
			// break;
			// }
			bw.write(line);
			bw.newLine();
			bw.flush();
		}

		// 给出反馈
		BufferedWriter bwServer = new BufferedWriter(new OutputStreamWriter(
				s.getOutputStream()));
		bwServer.write("文件上传成功");
		bwServer.newLine();
		bwServer.flush();

		// 释放资源
		bw.close();
		s.close();
	}
}
