/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月9日
 */

package common.nio;

/**
 * Desc:TODO 
 * @author wei.zw
 * @since 2017年5月9日 下午7:58:58
 * @version  v 0.1
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

/**
 * 用socket来收发http协议报文
 * 
 * @author luoguansong lgsstart89@163.com
 */
public class SocketHttp {
	public static void main(String[] args) throws Exception {
		// Thread threadReceive = new Thread(new TestReceiveHttp());
		// threadReceive.start();
		Thread threadSend = new Thread(new TestSendHttp());
		threadSend.start();
		TimeUnit.MINUTES.sleep(20);
	}
}

class TestSendHttp implements Runnable {
	@Override
	public void run() {
		try {
			String path = "/";
			String host = "www.tmall.com";
			int port = 80;
			Socket socket = new Socket();
			InetSocketAddress address = new InetSocketAddress(host, port);
			socket.connect(address, 3000);
			StringBuilder sb = new StringBuilder().append("GET " + path + " HTTP/1.1\r\n")
					.append("Host: " + host + " \r\n").append("\r\n");
			System.out.println(sb.toString());
			OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), "utf-8");
			osw.write(sb.toString());
			osw.flush();
			socket.shutdownOutput();

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
			osw.close();
			bufferedReader.close();
			socket.close();
		} catch (ConnectException e) {
			System.out.println("连接失败");
		} catch (SocketTimeoutException e) {
			System.out.println("连接超时");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

class TestReceiveHttp implements Runnable {
	@Override
	public void run() {
		ServerSocket server;
		Socket socket;
		try {
			server = new ServerSocket(8079);
			System.out.println("正在等待8079端口的请求");
			while (true) {
				socket = server.accept();
				if (socket != null) {
					new Thread(new TestReveiveThread(socket)).start();
				}
			}
		} catch (Exception e) {
			System.out.println("异常");
		}
	}
}

class TestReveiveThread implements Runnable {
	Socket socket;

	public TestReveiveThread(Socket s) {
		socket = s;
	}

	public void run() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), "utf-8");
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				if (line.equals("")) {
					break;
				}
			}
			// 模拟http请求到网站，然后把内容转发给当前的http请求
			// String path = "/";
			// String host = "www.oschina.net";
			// int port = 80;
			// Socket socket2 = new Socket(host, port);
			// OutputStreamWriter osw2 = new
			// OutputStreamWriter(socket2.getOutputStream(),"utf-8");
			// osw2.write("GET " + path + " HTTP/1.1\r\n");
			// osw2.write("Host: " + host + " \r\n");
			// osw2.write("\r\n");
			// osw2.flush();
			// BufferedReader bufferedReader2 = new BufferedReader(new
			// InputStreamReader(socket2.getInputStream(), "utf-8"));
			// String line2 = null;
			// while ((line2 = bufferedReader2.readLine()) != null) {
			// osw.write(line2+"\r\n");
			// }
			// bufferedReader2.close();
			// osw2.close();
			// socket2.close();
			osw.write("HTTP/1.1 200 OK\r\n");
			osw.write("Server: Apache-Coyote/1.1\r\n");
			osw.write("Set-Cookie: JSESSIONID=03493794995CE31A0F131787B6C6CBB2; Path=/; HttpOnly\r\n");
			osw.write("Content-Type: text/html;charset=UTF-8\r\n");
			osw.write("Transfer-Encoding: chunked\r\n");
			osw.write("Date: Tue, 19 May 2015 02:48:27 GMT\r\n");
			osw.write("\r\n");
			osw.write("c9\r\n");
			osw.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
			osw.write("<HTML>\r\n");
			osw.write("  <HEAD><TITLE>A Servlet</TITLE></HEAD>\r\n");
			osw.write("  <BODY>\r\n");
			osw.write("    This is class com.serv.myServ, using the GET method\r\n");
			osw.write("  </BODY>\r\n");
			osw.write("</HTML>\r\n");
			osw.write("\r\n");
			osw.write("0");
			osw.write("\r\n");
			osw.write("\r\n");
			osw.flush();
			bufferedReader.close();
			osw.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("客户端接受异常" + e.getMessage());
		}
	}
}