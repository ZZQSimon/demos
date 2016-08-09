/**
 * Title: PostThread.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName: PostThread <br>
 * @Description: 异步POST请求封装 <br>
 */
public abstract class PostThread implements Runnable {
	/** The httpClient. */
	private CloseableHttpClient httpClient;

	/** The httpPost. */
	private HttpPost httpPost;

	/**
	 * <p>
	 * Discription:[Get httpClient]
	 * </p>
	 * 
	 * @return CloseableHttpClient httpClient.
	 */
	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * <p>
	 * Discription:[Set httpClient]
	 * </p>
	 * 
	 * @param httpClient
	 *            The httpClient to set.
	 */
	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * <p>
	 * Discription:[Get httpPost]
	 * </p>
	 * 
	 * @return HttpPost httpPost.
	 */
	public HttpPost getHttpPost() {
		return httpPost;
	}

	/**
	 * <p>
	 * Discription:[Set httpPost]
	 * </p>
	 * 
	 * @param httpPost
	 *            The httpPost to set.
	 */
	public void setHttpPost(HttpPost httpPost) {
		this.httpPost = httpPost;
	}

	/**
	 * 
	 * <p>
	 * Discription:[异步执行]
	 * </p>
	 * 
	 */
	public void run() {
		try {

			CloseableHttpResponse response = httpClient.execute(httpPost, new BasicHttpContext());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				this.callBack(new String(EntityUtils.toByteArray(entity), HttpClientUtil.ENCODING));
			} else {
				throw new RuntimeException("Response为空");
			}
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * <p>
	 * Discription:[接口的回调函数]
	 * </p>
	 * 
	 * @param responseContent
	 */
	public abstract void callBack(String responseContent);
}
