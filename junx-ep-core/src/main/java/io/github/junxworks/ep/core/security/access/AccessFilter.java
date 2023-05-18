/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  AccessFilter.java   
 * @Package io.github.junxworks.ep.core.security.access   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-6-27 18:23:55   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.security.access;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.google.common.collect.Lists;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;

import io.github.junxworks.ep.core.security.ServletRequestContext;
import io.github.junxworks.ep.core.utils.IPUtils;
import io.github.junxworks.junx.core.util.ByteUtils;
import io.github.junxworks.junx.core.util.StringUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 访问日志打印filter
 *
 * @ClassName:  AccessFilter
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class AccessFilter implements Filter {

	/** 常量 log. */
	private static final Logger log = LoggerFactory.getLogger(AccessFilter.class);

	private static final String POST_METHOD = "POST";

	public static final String MULTIPART = "multipart/";

	private Map<String, Boolean> lruCache;

	private PathMatcher pathMatcher = new AntPathMatcher();

	private AcConfig acConfig;

	private static List<String> epExclude = Lists.newArrayList("/**/css/**", "/**/font/**", "/**/images/**", "/**/*.css", "/**/*.js", "/**/*.html", "/**/*.jsp", "/**/*.png", "/**/*.jpg", "/**/*.ico", "/**/*.gif", "/**/*.bmp", "/**/*.svg", "/**/*.eot", "/**/*.ttf", "/**/*.woff", "/**/*.woff2");

	private List<String> includeUrl;

	private List<String> excludeUrl;

	private Set<String> includeMethod;

	private Set<String> excludeMethod;

	public AcConfig getAcConfig() {
		return acConfig;
	}

	public void setAcConfig(AcConfig acConfig) {
		this.acConfig = acConfig;
	}

	/**
	 * Inits the.
	 *
	 * @param filterConfig the filter config
	 * @throws ServletException the servlet exception
	 */
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		includeMethod = acConfig.getIncludeMethod();
		excludeMethod = acConfig.getExcludeMethod();
		includeUrl = acConfig.getIncludeUrl();
		excludeUrl = acConfig.getExcludeUrl();
		if (acConfig.isUseEpDefaultExclude()) {
			excludeUrl.addAll(epExclude);
		}
		lruCache = new ConcurrentLinkedHashMap.Builder<String, Boolean>().maximumWeightedCapacity(acConfig.getMaxCacheSize()).weigher(Weighers.singleton()).build();
	}

	/**
	 * Do filter.
	 *
	 * @param request the request
	 * @param response the response
	 * @param chain the chain
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String url = req.getRequestURI().toString().toLowerCase();
		String method = req.getMethod();
		boolean isLog = true;
		String payload = null;
		if (!validateMethod(method)) {
			isLog = false;
		}
		if (isLog && validateUrl(url)) {
			if (!isMultipartContent(req)) {
				request = new BodyReaderHttpServletRequestWrapper(req);
				payload = new String(ByteUtils.inputStreamToBytes(request.getInputStream()));
				if (StringUtils.notNull(payload)) {
					payload = payload.replaceAll("\\n", "");
				}
			} else {
				payload = "MultipartContent";
			}
		} else {
			isLog = false;
		}
		if (isLog) {
			log.info(new StringBuilder().append("remote ip:").append(IPUtils.getIpAddr(req)).append(" URI:").append(req.getRequestURI()).append(" method:").append(req.getMethod()).append(" url-params:").append(req.getQueryString()).append(" body:").append(payload).toString());
		}
		chain.doFilter(request, response);
	}

	/**
	 * Destroy.
	 */
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	private boolean validateMethod(String method) {
		if (StringUtils.isNull(method)) {
			return false;
		}
		if (includeMethod.contains(method)) {
			return true;
		}
		if (!includeMethod.isEmpty() || excludeMethod.contains(method)) {
			return false;
		}
		//默认true
		return true;
	}

	/**
	 * 如果include有，就以include的为准，其次是看exclude包含uri不，如果include和exclude都不包含，则默认为true.
	 *
	 * @param url the url
	 * @return true, if successful
	 */
	private boolean validateUrl(String url) {
		Boolean cache = lruCache.get(url);
		if (cache != null) {
			return cache;
		}
		if (!includeUrl.isEmpty()) {
			for (String pExp : includeUrl) {
				if (antMatch(url, pExp)) {
					lruCache.put(url, true);
					return true;
				}
			}
		}
		if (!excludeUrl.isEmpty()) {
			for (String pExp : excludeUrl) {
				if (antMatch(url, pExp)) {
					lruCache.put(url, false);
					return false;
				}
			}
		}
		lruCache.put(url, true);
		return true;
	}

	private boolean antMatch(String url, String antExps) {
		if (StringUtils.isNull(antExps)) {
			return false;
		}
		return pathMatcher.match(antExps, url);
	}

	public static final boolean isMultipartContent(HttpServletRequest request) {
		if (!POST_METHOD.equalsIgnoreCase(request.getMethod())) {
			return false;
		}
		ServletRequestContext ctx = new ServletRequestContext(request);
		String contentType = ctx.getContentType();
		if (contentType == null) {
			return false;
		}
		if (contentType.toLowerCase(Locale.ENGLISH).startsWith(MULTIPART)) {
			return true;
		}
		return false;
	}
}
