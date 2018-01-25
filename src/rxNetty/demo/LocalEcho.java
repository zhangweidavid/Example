/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月5日
 */

package rxNetty.demo;



/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月5日 上午9:42:56
 * @version v 0.1
 */
public final class LocalEcho {

	public static void main(String[] args) throws Exception {
//		HttpServer<ByteBuf, ByteBuf> server = RxNetty.createHttpServer(8080, (request, response) -> {
//			System.out.println("Server => Request: " + request.getPath());
//			try {
//				if ("/error".equals(request.getPath())) {
//					throw new RuntimeException("forced error");
//				}
//				response.setStatus(HttpResponseStatus.OK);
//				response.writeString("Path Requested =>: " + request.getPath() + '\n');
//				return response.close();
//			} catch (Throwable e) {
//				System.err.println("Server => Error [" + request.getPath() + "] => " + e);
//				response.setStatus(HttpResponseStatus.BAD_REQUEST);
//				response.writeString("Error 500: Bad Request\n");
//				return response.close();
//			}
//		});
//
//		server.start();
//
//		RxNetty.createHttpGet("http://localhost:8080/").flatMap(response -> response.getContent())
//				.map(data -> "Client => " + data.toString(Charset.defaultCharset())).toBlocking()
//				.forEach(System.out::println);
//
//		RxNetty.createHttpGet("http://localhost:8080/error").flatMap(response -> response.getContent())
//				.map(data -> "Client => " + data.toString(Charset.defaultCharset())).toBlocking()
//				.forEach(System.out::println);
//
//		RxNetty.createHttpGet("http://localhost:8080/data").flatMap(response -> response.getContent())
//				.map(data -> "Client => " + data.toString(Charset.defaultCharset())).toBlocking()
//				.forEach(System.out::println);
//
//		server.shutdown();

	}
}