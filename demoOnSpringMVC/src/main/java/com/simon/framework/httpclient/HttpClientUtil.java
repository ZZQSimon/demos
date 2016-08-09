/**
 * Title: HttpClientUtil.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.httpclient;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName: HttpClientUtil <br>
 * @Description: HTTP调用统一封装类 <br>
 */
public class HttpClientUtil {

	/** Httpclient 调用实体 */
	private static CloseableHttpClient httpClient;

	/** 编码格式 */
	public static final String ENCODING = "UTF-8";

	/** 初始化HTTPClient，保证在程序中只有一个对象存在 */
	static {
		synchronized (HttpClientUtil.class) {
			if (httpClient == null) {
				PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
				cm.setMaxTotal(100);
				httpClient = HttpClients.custom().setConnectionManager(cm).build();
			}
		}
	}

	/**
	 * 
	 * <p>
	 * Discription:[HTTP POST方式的同步调用]
	 * </p>
	 * 
	 * @param uri
	 * @param jsonContent
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String synchPost(URI uri, String jsonContent) throws ClientProtocolException, IOException {

		HttpPost post = new HttpPost(uri);
		post.setEntity(getJsonEntity(jsonContent));

		CloseableHttpResponse response = httpClient.execute(post, new BasicHttpContext());

		try {

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return new String(EntityUtils.toByteArray(entity), ENCODING);
			} else {
				throw new RuntimeException("Response为空");
			}
		} finally {
			response.close();
		}
	}

	/**
	 * 
	 * <p>
	 * Discription:[传递文件 同步]
	 * </p>
	 * 
	 * @param uri
	 * @param fileName
	 * @param file
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String synchPost(URI uri, String fileName, File file) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(uri);

		HttpEntity he = new FileEntity(file);
		post.setEntity(he);
		// 为httpPost设置头信息
		post.setHeader("filename", URLEncoder.encode(fileName, ENCODING));// 服务器可以读取到该文件名
		// post.setHeader("Content-Length",
		// String.valueOf(he.getContentLength()));// 设置传输长度

		CloseableHttpResponse response = httpClient.execute(post, new BasicHttpContext());

		try {

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return new String(EntityUtils.toByteArray(entity), ENCODING);
			} else {
				throw new RuntimeException("Response为空");
			}
		} finally {
			response.close();
		}
	}

	/**
	 * 
	 * <p>
	 * Discription:[设置POST方式的Entity内容 - 发送的消息体，编码格式和头消息]
	 * </p>
	 * 
	 * @param jsonContent
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static StringEntity getJsonEntity(String jsonContent) throws UnsupportedEncodingException {
		StringEntity se = new StringEntity(jsonContent, ContentType.APPLICATION_JSON);
		se.setContentEncoding(ENCODING);
		se.setContentType("application/json");
		return se;
	}

	/**
	 * 
	 * <p>
	 * Discription:[HTTP GET方式的同步调用]
	 * </p>
	 * 
	 * @param uri
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String synchGet(URI uri) throws ClientProtocolException, IOException {

		HttpGet get = new HttpGet(uri);
		CloseableHttpResponse response = httpClient.execute(get, new BasicHttpContext());
		try {

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return new String(EntityUtils.toByteArray(entity), ENCODING);
			} else {
				throw new RuntimeException("Response为空");
			}
		} finally {
			response.close();
		}
	}

	/**
	 * 
	 * <p>
	 * Discription:[HTTP POST方式的异步调用]
	 * </p>
	 * 
	 * @param uri
	 * @param jsonContent
	 * @param postThread
	 * @throws UnsupportedEncodingException
	 */
	public static void asyncPost(URI uri, String jsonContent, PostThread postThread)
			throws UnsupportedEncodingException {
		HttpPost post = new HttpPost(uri);
		post.setEntity(getJsonEntity(jsonContent));
		postThread.setHttpPost(post);
		postThread.setHttpClient(httpClient);

		ThreadPoolManager.defaultThreadPool(HttpClientUtil.class).execute(postThread);
	}

	/**
	 * 
	 * <p>
	 * Discription:[HTTP GET方式的异步调用]
	 * </p>
	 * 
	 * @param uri
	 * @param getThread
	 * @throws UnsupportedEncodingException
	 */
	public static void asyncGet(URI uri, GetThread getThread) throws UnsupportedEncodingException {
		getThread.setHttpGet(new HttpGet(uri));
		getThread.setHttpClient(httpClient);
		ThreadPoolManager.defaultThreadPool(HttpClientUtil.class).execute(getThread);
	}

	/**
	 * 
	 * <p>
	 * Discription:[HTTP PUT方式的同步调用]
	 * </p>
	 * 
	 * @param uri
	 * @param getThread
	 * @throws ClientProtocolException,
	 *             IOException
	 */
	public static String synchPut(URI uri, String jsonContent) throws ClientProtocolException, IOException {

		HttpPut put = new HttpPut(uri);
		put.setEntity(getJsonEntity(jsonContent));

		CloseableHttpResponse response = httpClient.execute(put, new BasicHttpContext());
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return new String(EntityUtils.toByteArray(entity), ENCODING);
			} else {
				throw new RuntimeException("Response为空");
			}
		} finally {
			response.close();
		}
	}

	/**
	 * 
	 * <p>
	 * Discription:[HTTP DELETE方式的同步调用]
	 * </p>
	 * 
	 * @param uri
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String synchDelete(URI uri) throws ClientProtocolException, IOException {

		HttpDelete delete = new HttpDelete(uri);

		CloseableHttpResponse response = httpClient.execute(delete, new BasicHttpContext());

		try {

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return new String(EntityUtils.toByteArray(entity), ENCODING);
			} else {
				throw new RuntimeException("Response为空");
			}
		} finally {
			response.close();
		}
	}

}
