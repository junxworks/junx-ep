/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  FileStorageClient.java   
 * @Package io.github.junxworks.ep.core.fs   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-6-5 16:55:43   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.exception.FatalException;
import io.github.junxworks.junx.core.lifecycle.Service;
import io.github.junxworks.junx.core.util.ExceptionUtils;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * 文件访问客户端[线程安全]
 * 
 * @ClassName: FileStorageClient
 * @author: michael
 * @date: 2019-2-22 13:30:35
 * @since: v1.0
 */
public class FsClient extends Service {
	/** 文件服务器地址，url或者ip地址。例如: https://xxxxx 或者 http://192.168.1.100 */
	private String fsAddr;

	/** 文件分组，有利于业务处理，默认为default */
	private String fileGroup = "default";

	private String orgNo;

	/** 基础restful地址. */
	private String baseUrl;

	/**
	 * 构造一个新的 file storage client 对象.
	 */
	public FsClient(String fsAddr, String orgNo) {
		this.fsAddr = fsAddr;
		this.orgNo = orgNo;
	}

	public String getFsAddr() {
		return fsAddr;
	}

	public void setFsAddr(String fsAddr) {
		this.fsAddr = fsAddr;
	}

	public String getFileGroup() {
		return fileGroup;
	}

	public void setFileGroup(String fileGroup) {
		this.fileGroup = fileGroup;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	/**
	 * Upload file.
	 *
	 * @param file      spring封装的MultipartFile对象
	 * @param url       文件服务器url
	 * @param orgNo     组织编码
	 * @param fileGroup 文件分组
	 * @param accKey    分配给业务单元的key
	 * @param accSecret 分配给业务单元的secret
	 * @return the result 返回结果
	 * @throws Exception the exception
	 */
	public Result upload(MultipartFile file) {
		try {
			return upload(file.getOriginalFilename(), file.getInputStream());
		} catch (IOException e) {
			return Result.error("Upload resource failed,error=" + ExceptionUtils.getCause(e).toString());
		}
	}

	public Result upload(File localFile) {
		try (FileInputStream fi = new FileInputStream(localFile)) {
			return upload(localFile.getName(), fi);
		} catch (IOException e) {
			return Result.error("Upload resource failed,error=" + ExceptionUtils.getCause(e).toString());
		}
	}

	public Result upload(String fileName, InputStream fileInputStream) {
		try {
			if (!isRunning()) {
				throw new BaseRuntimeException("File storage client is not running.");
			}
			CloseableHttpClient httpClient = createHttpClient();
			// 创建自定义的httpclient对象
			String result = "";
			try {
				HttpPost httpPost = new HttpPost(baseUrl);
				MultipartEntityBuilder builder = MultipartEntityBuilder.create().setCharset(Charset.forName("UTF-8")).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				builder.addBinaryBody("file", fileInputStream, ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
				builder.addTextBody("filename", fileName);
				builder.addTextBody("orgNo", orgNo);
				builder.addTextBody("group", fileGroup);
				HttpEntity entity = builder.build();
				httpPost.setEntity(entity);

				HttpResponse response = httpClient.execute(httpPost);// 执行提交
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					HttpEntity responseEntity = response.getEntity();
					result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
					Result res = JSON.parseObject(result, Result.class);
					if (res.isOk()) {
						JSONObject jo = (JSONObject) res.getData();
						res.setData(JSON.toJavaObject(jo, FileInfo.class));
					}
					return res;
				} else {
					return Result.error("Upload resource failed,response status " + statusCode);
				}
			} finally {
				if (httpClient != null) {
					httpClient.close();
				}
			}
		} catch (Exception e) {
			return Result.error("Upload resource failed,error=" + ExceptionUtils.getCause(e).toString());
		}
	}

	/**
	 * 返回 file info 属性.
	 *
	 * @param fileId the file id
	 * @return file info 属性
	 */
	public FileInfo getFileInfo(String fileId) throws Exception {
		try {
			if (!isRunning()) {
				throw new BaseRuntimeException("File storage client is not running.");
			}
			CloseableHttpClient httpClient = createHttpClient();
			// 创建自定义的httpclient对象
			String result = "";
			try {
				HttpGet get = new HttpGet(baseUrl + "/" + fileId + "/metadata");
				HttpResponse response = httpClient.execute(get);// 执行提交
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					HttpEntity responseEntity = response.getEntity();
					result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
					Result res = JSON.parseObject(result, Result.class);
					if (res.isOk()) {
						JSONObject jo = (JSONObject) res.getData();
						return JSON.toJavaObject(jo, FileInfo.class);
					} else {
						throw new BaseRuntimeException("Get file metadata failed,message:" + res.getMsg());
					}
				} else {
					throw new BaseRuntimeException("Get file metadata failed,response status " + statusCode);
				}
			} finally {
				if (httpClient != null) {
					httpClient.close();
				}
			}
		} catch (Exception e) {
			throw new BaseRuntimeException("Get file metadata failed,error=" + ExceptionUtils.getCause(e).toString());
		}
	}

	/**
	 * 以流的方式直接返回
	 *
	 * @param httpReponse the http reponse
	 * @param url         the url
	 * @param accKey      the acc key
	 * @param accSecret   the acc secret
	 * @throws Exception the exception
	 */
	public void downloadIntoResponse(String fileId, HttpServletResponse httpReponse) throws Exception {
		downloadIntoResponse(fileId, baseUrl + "/" + fileId, httpReponse);
	}

	/**
	 * 下载附件的方式返回
	 *
	 * @param httpReponse the http reponse
	 * @param url         the url
	 * @param accKey      the acc key
	 * @param accSecret   the acc secret
	 * @throws Exception the exception
	 */
	public void downloadAttachmentIntoResponse(String fileId, HttpServletResponse httpReponse) throws Exception {
		downloadIntoResponse(fileId, baseUrl + "/" + fileId + "/attachments", httpReponse);
	}

	/**
	 * 以inline方式下载缩略图到response
	 *
	 * @param fileId      the file id
	 * @param width       the width
	 * @param height      the height
	 * @param httpReponse the http reponse
	 * @throws Exception the exception
	 */
	public void downloadThumbnailIntoResponse(String fileId, int width, int height, HttpServletResponse httpReponse) throws Exception {
		downloadIntoResponse(fileId, baseUrl + "/" + fileId + "/thumbnails/" + width + "/" + height, httpReponse);
	}

	/**
	 * 以附件方式下载缩略图到response
	 *
	 * @param fileId      the file id
	 * @param width       the width
	 * @param height      the height
	 * @param httpReponse the http reponse
	 * @throws Exception the exception
	 */
	public void downloadThumbnailAttachmentIntoResponse(String fileId, int width, int height, HttpServletResponse httpReponse) throws Exception {
		downloadIntoResponse(fileId, baseUrl + "/" + fileId + "/thumbnails/" + width + "/" + height + "/attachments", httpReponse);
	}

	/**
	 * 直接下载到response中
	 *
	 * @param fileId      文件id
	 * @param url         restful URL
	 * @param httpReponse the http reponse
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public void downloadIntoResponse(String fileId, String url, HttpServletResponse httpReponse) throws Exception {
		if (!isRunning()) {
			throw new BaseRuntimeException("File storage client is not running.");
		}
		CloseableHttpClient httpClient = createHttpClient();
		try {
			HttpGet get = new HttpGet(url);
			HttpResponse response = httpClient.execute(get);// 执行提交
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				httpReponse.setContentLength((int) entity.getContentLength());
				Header[] headers = response.getAllHeaders();
				if (headers != null) {
					for (Header h : headers) {
						httpReponse.addHeader(h.getName(), h.getValue());
					}
				}
				try (InputStream in = entity.getContent()) {
					inputStreamToOutputStream(in, httpReponse.getOutputStream());
				}
			} else {
				httpReponse.setStatus(statusCode);
				httpReponse.getWriter().print(Result.error("Download resource failed,code[" + statusCode + "].").toJson());
				return;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 直接下载到outputStream中
	 *
	 * @param fileId 文件id
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public void downloadIntoOutputStream(String fileId, OutputStream out) throws Exception {
		if (!isRunning()) {
			throw new BaseRuntimeException("File storage client is not running.");
		}
		CloseableHttpClient httpClient = createHttpClient();
		try {
			HttpGet get = new HttpGet(baseUrl + "/" + fileId);
			HttpResponse response = httpClient.execute(get);// 执行提交
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				try (InputStream in = entity.getContent()) {
					inputStreamToOutputStream(in, out);
				}
			} else {
				throw new BaseRuntimeException("Download file failed,response status " + statusCode);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 下载文件
	 */
	public void downloadFileIntoResponse(String fileId, String fileName, HttpServletResponse httpReponse) throws Exception {
		if (!isRunning()) {
			throw new BaseRuntimeException("File storage client is not running.");
		}
		String url = baseUrl + "/" + fileId + "/attachments";
		CloseableHttpClient httpClient = createHttpClient();
		try {
			HttpGet get = new HttpGet(url);
			HttpResponse response = httpClient.execute(get);// 执行提交
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				httpReponse.setContentLength((int) entity.getContentLength());
				Header[] headers = response.getAllHeaders();
				if (headers != null) {
					for (Header h : headers) {
						httpReponse.addHeader(h.getName(), h.getValue());
					}
				}

				//处理中文文件名
				String downloadFileName = URLEncoder.encode(fileName, "UTF-8");
				httpReponse.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
				try (InputStream in = entity.getContent()) {
					inputStreamToOutputStream(in, httpReponse.getOutputStream());
				}
			} else {
				httpReponse.setStatus(statusCode);
				httpReponse.getWriter().print(Result.error("Download resource failed,code[" + statusCode + "].").toJson());
				return;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}
	}

	private CloseableHttpClient createHttpClient() throws KeyManagementException, NoSuchAlgorithmException {
		// 采用绕过验证的方式处理https请求
		SSLContext sslcontext = createIgnoreVerifySSL();

		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", new SSLConnectionSocketFactory(sslcontext)).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);

		// 创建自定义的httpclient对象
		return HttpClients.custom().setConnectionManager(connManager).build();
	}

	/**
	 * Input stream to output stream.
	 *
	 * @param in  the in
	 * @param out the out
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void inputStreamToOutputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[8 * 1024];
		int readBytes;
		while ((readBytes = in.read(buffer)) != -1) {
			out.write(buffer, 0, readBytes);
		}
	}

	/**
	 * 绕过验证
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.junxworks.junx.core.lifecycle.Service#doStart()
	 */
	@Override
	protected void doStart() throws Throwable {
		// TODO Auto-generated method stub
		if (StringUtils.isNull(fsAddr)) {
			throw new FatalException("文件服务器地址不能为空");
		}
		if (StringUtils.isNull(orgNo)) {
			throw new FatalException("组织编码不能为空");
		}
		if (fsAddr.endsWith("/"))
			fsAddr = fsAddr.substring(0, fsAddr.length() - 1);
		baseUrl = fsAddr + "/ep/fs/files";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.junxworks.junx.core.lifecycle.Service#doStop()
	 */
	@Override
	protected void doStop() throws Throwable {

	}
}
