/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  HttpClientUtil.java   
 * @Package io.github.junxworks.ep.core.utils   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:37   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;

import io.github.junxworks.ep.core.exception.BusinessException;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;

/**
 * HttpClient封装工具类
 *
 * @ClassName:  HttpClientUtil
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class HttpClientUtil {

	/** 常量 CHARSET_UTF_8. */
	// utf-8字符编码
	public static final String CHARSET_UTF_8 = "utf-8";

	/** 常量 CONTENT_TYPE_JSON_URL. */
	// HTTP内容类型。相当于form表单的形式，提交数据
	public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";

	/** pool. */
	// 连接管理器
	private static PoolingHttpClientConnectionManager pool;

	/** request config. */
	// 请求配置
	private static RequestConfig defaultRequestConfig;

	static {

		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
			// 配置同时支持 HTTP 和 HTPPS
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
			// 初始化连接管理器
			pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			// 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
			pool.setMaxTotal(200);
			// 设置最大路由
			pool.setDefaultMaxPerRoute(2);
			// 根据默认超时限制初始化requestConfig
			int socketTimeout = 600 * 1000;
			int connectTimeout = 5 * 1000;
			int connectionRequestTimeout = 10 * 1000;
			defaultRequestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout, TimeUnit.MILLISECONDS).setResponseTimeout(socketTimeout, TimeUnit.MILLISECONDS).setConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS).build();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	public static CloseableHttpClient getHttpClient() {

		CloseableHttpClient httpClient = HttpClients.custom()
				// 设置连接池管理
				.setConnectionManager(pool)
				// 设置请求配置
				.setDefaultRequestConfig(defaultRequestConfig)
				// 设置重试
				.setRetryStrategy(DefaultHttpRequestRetryStrategy.INSTANCE).build();
		return httpClient;
	}

	public static String sendHttpPost(HttpPost httpPost) {
		return sendHttpPost(httpPost, defaultRequestConfig);
	}

	/**
	 * Send http post.
	 *
	 * @param httpPost the http post
	 * @return the string
	 */
	public static String sendHttpPost(HttpPost httpPost, RequestConfig config) {

		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		// 响应内容
		String responseContent = null;
		try {
			// 创建默认的httpClient实例.
			httpClient = HttpClients.createDefault();
			// 配置请求信息
			httpPost.setConfig(config);

			responseContent = httpClient.execute(httpPost, new HttpClientResponseHandler<String>() {
				@Override
				public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
					int statusCode = response.getCode();
					// 判断响应状态
					if (statusCode >= 300) {
						throw new HttpException("HTTP Request is not success, Response code is " + response.getCode());
					}
					HttpEntity entity = response.getEntity();
					if (HttpStatus.SC_OK == statusCode) {
						String responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
						EntityUtils.consume(entity);
						return responseContent;
					}
					return null;
				}
			});// 执行提交

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	public static String sendHttpGet(HttpGet httpGet) {
		return sendHttpGet(httpGet, defaultRequestConfig);
	}

	/**
	 * Send http get.
	 *
	 * @param httpGet the http get
	 * @return the string
	 */
	public static String sendHttpGet(HttpGet httpGet, RequestConfig config) {

		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		// 响应内容
		String responseContent = null;
		try {
			// 创建默认的httpClient实例.
			httpClient = HttpClients.createDefault();
			// 配置请求信息
			httpGet.setConfig(config);

			responseContent = httpClient.execute(httpGet, new HttpClientResponseHandler<String>() {
				@Override
				public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
					int statusCode = response.getCode();
					// 判断响应状态
					if (statusCode >= 300) {
						throw new HttpException("HTTP Request is not success, Response code is " + response.getCode());
					}
					HttpEntity entity = response.getEntity();
					if (HttpStatus.SC_OK == statusCode) {
						String responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
						EntityUtils.consume(entity);
						return responseContent;
					}
					return null;
				}
			});// 执行提交

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		} finally {
			try {
				// 释放资源
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	/**
	 * Send http post.
	 *
	 * @param httpUrl the http url
	 * @return the string
	 */
	public static String sendHttpPost(String httpUrl) {
		return sendHttpPost(httpUrl, defaultRequestConfig);
	}

	public static String sendHttpPost(String httpUrl, RequestConfig config) {
		// 创建httpPost
		HttpPost httpPost = new HttpPost(httpUrl);
		return sendHttpPost(httpPost, config);
	}

	public static String sendHttpGet(String httpUrl) {
		return sendHttpGet(httpUrl, defaultRequestConfig);
	}

	/**
	 * Send http get.
	 *
	 * @param httpUrl the http url
	 * @return the string
	 */
	public static String sendHttpGet(String httpUrl, RequestConfig config) {
		// 创建get请求
		HttpGet httpGet = new HttpGet(httpUrl);
		return sendHttpGet(httpGet, config);
	}

	/**
	 * Send http get.
	 *
	 * @param httpUrl the http url
	 * @param map the map
	 * @return the string
	 */
	public static String sendHttpGet(String httpUrl, Map<String, String> map) {
		return sendHttpGet(httpUrl, map, defaultRequestConfig);
	}

	public static String sendHttpGet(String httpUrl, Map<String, String> map, RequestConfig config) {
		httpUrl = buildGetParam(httpUrl, map);
		return sendHttpGet(httpUrl, config);
	}

	/**
	 * Send http get.
	 *
	 * @param httpUrl the http url
	 * @param map the map
	 * @param headers the headers
	 * @return the string
	 */
	public static String sendHttpGet(String httpUrl, Map<String, String> map, Map<String, String> headers) {
		return sendHttpGet(httpUrl, map, headers, defaultRequestConfig);
	}

	public static String sendHttpGet(String httpUrl, Map<String, String> map, Map<String, String> headers, RequestConfig config) {
		httpUrl = buildGetParam(httpUrl, map);
		// 创建get请求
		HttpGet httpGet = new HttpGet(httpUrl);
		for (String key : headers.keySet()) {
			String value = headers.get(key);
			httpGet.setHeader(key, value);
		}
		return sendHttpGet(httpGet, config);
	}

	/**
	 * Builds the get param.
	 *
	 * @param url the url
	 * @param map the map
	 * @return the string
	 */
	public static String buildGetParam(String url, Map<String, String> map) {
		return url + "?" + buildGetParam(map);
	}

	/**
	 * Builds the get param.
	 *
	 * @param map the map
	 * @return the string
	 * @throws UnknownHostException 
	 * @throws URISyntaxException 
	 */
	public static String buildGetParam(Map<String, String> map) {
		URIBuilder builder = URIBuilder.loopbackAddress();
		Iterator<Map.Entry<String, String>> entryKeyIterator = map.entrySet().iterator();
		while (entryKeyIterator.hasNext()) {
			Map.Entry<String, String> e = entryKeyIterator.next();
			BasicNameValuePair bnv = new BasicNameValuePair(e.getKey(), e.getValue());
			builder.addParameter(bnv);
		}
		try {
			return builder.setCharset(StandardCharsets.UTF_8).build().getQuery();
		} catch (URISyntaxException e) {
			throw new BaseRuntimeException(e);
		}
	}

	/**
	 * Send http post.
	 *
	 * @param httpUrl the http url
	 * @param maps the maps
	 * @param fileLists the file lists
	 * @return the string
	 */
	public static String sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists) {
		return sendHttpPost(httpUrl, maps, fileLists, defaultRequestConfig);
	}

	public static String sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists, RequestConfig config) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
		if (maps != null) {
			for (String key : maps.keySet()) {
				meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
			}
		}
		if (fileLists != null) {
			for (File file : fileLists) {
				FileBody fileBody = new FileBody(file);
				meBuilder.addPart("files", fileBody);
			}
		}
		HttpEntity reqEntity = meBuilder.build();
		httpPost.setEntity(reqEntity);
		return sendHttpPost(httpPost, config);
	}

	/**
	 * Send http post.
	 *
	 * @param httpUrl the http url
	 * @param params the params
	 * @return the string
	 */
	public static String sendHttpPost(String httpUrl, String params) {
		return sendHttpPost(httpUrl, params, defaultRequestConfig);
	}

	public static String sendHttpPost(String httpUrl, String params, RequestConfig config) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		try {
			// 设置参数
			if (params != null && params.trim().length() > 0) {
				StringEntity stringEntity = new StringEntity(params, ContentType.APPLICATION_FORM_URLENCODED, "UTF-8", false);
				httpPost.setEntity(stringEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendHttpPost(httpPost, config);
	}

	/**
	 * Send http post.
	 *
	 * @param httpUrl the http url
	 * @param maps the maps
	 * @return the string
	 */
	public static String sendHttpPost(String httpUrl, Map<String, String> maps) {
		return sendHttpPost(httpUrl, maps, defaultRequestConfig);
	}

	public static String sendHttpPost(String httpUrl, Map<String, String> maps, RequestConfig config) {
		String parem = convertStringParamter(maps);
		return sendHttpPost(httpUrl, parem, config);
	}

	/**
	 * Send http post.
	 *
	 * @param httpUrl the http url
	 * @param maps the maps
	 * @param headers the headers
	 * @return the string
	 */
	public static String sendHttpPost(String httpUrl, Map<String, String> maps, Map<String, String> headers) {
		return sendHttpPost(httpUrl, maps, headers, defaultRequestConfig);
	}

	public static String sendHttpPost(String httpUrl, Map<String, String> maps, Map<String, String> headers, RequestConfig config) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		//设置请求头
		for (String key : headers.keySet()) {
			String value = headers.get(key);
			httpPost.setHeader(key, value);
		}

		try {
			String params = convertStringParamter(maps);
			// 设置参数
			if (params != null && params.trim().length() > 0) {
				StringEntity stringEntity = new StringEntity(params, ContentType.APPLICATION_FORM_URLENCODED, "UTF-8", false);
				httpPost.setEntity(stringEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendHttpPost(httpPost, config);
	}

	/**
	 * Send http post json.
	 *
	 * @param httpUrl the http url
	 * @param paramsJson the params json
	 * @return the string
	 */
	public static String sendHttpPostJson(String httpUrl, String paramsJson) {
		return sendHttpPostJson(httpUrl, paramsJson, defaultRequestConfig);
	}

	public static String sendHttpPostJson(String httpUrl, String paramsJson, RequestConfig config) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		try {
			// 设置参数
			if (paramsJson != null && paramsJson.trim().length() > 0) {
				StringEntity stringEntity = new StringEntity(paramsJson, ContentType.APPLICATION_FORM_URLENCODED, "UTF-8", false);
				httpPost.setEntity(stringEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendHttpPost(httpPost, config);
	}

	/**
	 * Send http post json.
	 *
	 * @param httpUrl the http url
	 * @param paramsJson the params json
	 * @param headers the headers
	 * @return the string
	 */
	public static String sendHttpPostJson(String httpUrl, String paramsJson, Map<String, String> headers) {
		return sendHttpPostJson(httpUrl, paramsJson, headers, defaultRequestConfig);
	}

	public static String sendHttpPostJson(String httpUrl, String paramsJson, Map<String, String> headers, RequestConfig config) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		//设置请求头
		for (String key : headers.keySet()) {
			String value = headers.get(key);
			httpPost.setHeader(key, value);
		}

		try {
			// 设置参数
			if (paramsJson != null && paramsJson.trim().length() > 0) {
				StringEntity stringEntity = new StringEntity(paramsJson, ContentType.APPLICATION_FORM_URLENCODED, "UTF-8", false);
				httpPost.setEntity(stringEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendHttpPost(httpPost, config);
	}

	/**
	 * Send http post xml.
	 *
	 * @param httpUrl the http url
	 * @param paramsXml the params xml
	 * @return the string
	 */
	public static String sendHttpPostXml(String httpUrl, String paramsXml) {
		return sendHttpPostXml(httpUrl, paramsXml, defaultRequestConfig);
	}

	public static String sendHttpPostXml(String httpUrl, String paramsXml, RequestConfig config) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		try {
			// 设置参数
			if (paramsXml != null && paramsXml.trim().length() > 0) {
				StringEntity stringEntity = new StringEntity(paramsXml, ContentType.TEXT_XML, "UTF-8", false);
				httpPost.setEntity(stringEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendHttpPost(httpPost, config);
	}

	/**
	 * Convert string paramter.
	 *
	 * @param parameterMap the parameter map
	 * @return the string
	 */
	@SuppressWarnings("rawtypes")
	public static String convertStringParamter(Map parameterMap) {
		StringBuffer parameterBuffer = new StringBuffer();
		if (parameterMap != null) {
			Iterator iterator = parameterMap.keySet().iterator();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (parameterMap.get(key) != null) {
					value = (String) parameterMap.get(key);
				} else {
					value = "";
				}
				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}
		return parameterBuffer.toString();
	}

}
