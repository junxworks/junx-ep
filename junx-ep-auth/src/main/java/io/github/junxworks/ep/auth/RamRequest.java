/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RamRequest.java   
 * @Package io.github.junxworks.ep.auth   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:41   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

/**
 * {类的详细说明}.
 *
 * @ClassName:  RamRequest
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
public class RamRequest {
	
	/** access key. */
	private String accessKey;

	/** context path. */
	private String contextPath;

	/** request uri. */
	private String requestUri;

	/** request method. */
	private String requestMethod;

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

}
