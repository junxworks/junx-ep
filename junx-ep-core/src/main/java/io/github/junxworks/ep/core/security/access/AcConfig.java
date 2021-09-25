/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  AcConfig.java   
 * @Package io.github.junxworks.ep.core.security.access   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021年9月11日 上午11:07:23   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.security.access;

import java.util.List;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * access的日志配置.
 *
 * @ClassName:  AcConfig
 * @author: Michael
 * @date:   2021-6-27 17:12:41
 * @since:  v1.0
 */
@ConfigurationProperties(prefix = "junx.ep.access-log")
public class AcConfig {

	/** 最大缓存大小，缓存需要打印日志的url，默认是100000. */
	private Integer maxCacheSize = 100000;

	/** 是否使用EP默认的静态资源排除表达式，默认是. */
	private boolean useEpDefaultExclude = true;

	/**  包含哪些访问资源需要打印访问日志，用ant url路径表达式，优先级高于exclude. */
	private List<String> includeUrl = Lists.newArrayList();

	/** 排除哪些访问资源打印，用ant url路径表达式，如果include中有包含，则以include为准. */
	private List<String> excludeUrl = Lists.newArrayList();

	/** 包含访问的Method.采用大写字母配置 */
	private Set<String> includeMethod = Sets.newHashSet();

	/** 排除访问的Method. */
	private Set<String> excludeMethod = Sets.newHashSet();

	public boolean isUseEpDefaultExclude() {
		return useEpDefaultExclude;
	}

	public void setUseEpDefaultExclude(boolean useEpDefaultExclude) {
		this.useEpDefaultExclude = useEpDefaultExclude;
	}

	public List<String> getIncludeUrl() {
		return includeUrl;
	}

	public void setIncludeUrl(List<String> includeUrl) {
		this.includeUrl = includeUrl;
	}

	public List<String> getExcludeUrl() {
		return excludeUrl;
	}

	public void setExcludeUrl(List<String> excludeUrl) {
		this.excludeUrl = excludeUrl;
	}

	public Integer getMaxCacheSize() {
		return maxCacheSize;
	}

	public void setMaxCacheSize(Integer maxCacheSize) {
		this.maxCacheSize = maxCacheSize;
	}

	public Set<String> getIncludeMethod() {
		return includeMethod;
	}

	public void setIncludeMethod(Set<String> includeMethod) {
		this.includeMethod = includeMethod;
	}

	public Set<String> getExcludeMethod() {
		return excludeMethod;
	}

	public void setExcludeMethod(Set<String> excludeMethod) {
		this.excludeMethod = excludeMethod;
	}

}
