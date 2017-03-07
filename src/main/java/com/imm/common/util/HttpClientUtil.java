package com.imm.common.util;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
//import org.apache.log4j.spi.LoggerFactory;

import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;

public final class HttpClientUtil {

	final static Log log = LogFactory.getLogger(HttpClientUtil.class);
     
	private static PoolingHttpClientConnectionManager connManager = null;
	private static CloseableHttpClient httpclient = null;
	public final static int connectTimeout = 5000;
	
	static {
	    connManager = new PoolingHttpClientConnectionManager();
	    httpclient = HttpClients.custom().setConnectionManager(connManager).build();
	    // Create socket configuration
	    SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
	    connManager.setDefaultSocketConfig(socketConfig);
	    connManager.setMaxTotal(200);
	    connManager.setDefaultMaxPerRoute(20);
	}
	     
	@SuppressWarnings("deprecation")
	public static String doHttpGet(String url, Map<String, String> params, String encodeResp) {
		String responseString = null;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout)
				.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();

		final StringBuilder sb = new StringBuilder();
		sb.append(url);
		if (params != null) {
			int i = 0;
			for (Entry<String, String> entry : params.entrySet()) {
				if (i == 0 && !url.contains("?")) {
					sb.append("?");
				} else {
					sb.append("&");
				}
				sb.append(entry.getKey());
				sb.append("=");
				String value = entry.getValue();
				try {
					sb.append(URLEncoder.encode(value, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					log.warn("encode http get params error, value is " + value, e);
					sb.append(URLEncoder.encode(value));
				}
				i++;
			}
		}
		log.info("[HttpUtils Get] begin invoke:" + sb.toString());
		HttpGet get = new HttpGet(sb.toString());
		get.setConfig(requestConfig);

		try {
			CloseableHttpResponse response = httpclient.execute(get);
			try {
				HttpEntity entity = response.getEntity();
				try {
					if (entity != null) {
						if (encodeResp != null) {
							responseString = EntityUtils.toString(entity, encodeResp);
						} else {
							responseString = EntityUtils.toString(entity);
						}
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} catch (Exception e) {
				log.error(String.format("[HttpUtils Get]get response error, url:%s", sb.toString()), e);
				return responseString;
			} finally {
				if (response != null) {
					response.close();
				}
			}
			log.info(String.format("[HttpUtils Get]Debug url:%s , response string %s:", sb.toString(), responseString));
		} catch (SocketTimeoutException e) {
			log.error(String.format("[HttpUtils Get]invoke get timout error, url:%s", sb.toString()), e);
			return responseString;
		} catch (Exception e) {
			log.error(String.format("[HttpUtils Get]invoke get error, url:%s", sb.toString()), e);
		} finally {
			get.releaseConnection();
		}
		return responseString;
	}
}
