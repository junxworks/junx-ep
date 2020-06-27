package io.github.junxworks.ep.auth.ram;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.github.junxworks.ep.auth.EPShiroConfig;
import io.github.junxworks.ep.auth.RamConstants;

@Configuration
@EnableConfigurationProperties({ EPShiroConfig.class })
public class RAMFeignHeaderConfig {

	@Autowired
	private EPShiroConfig config;
	
	@Bean
	public RequestInterceptor headerInterceptor() {
		return new RequestInterceptor() {

			@Override
			public void apply(RequestTemplate template) {
				Map<String, Collection<String>> headers = ImmutableMap.of(RamConstants.RAM_HEADER_ACCESSKEY, Lists.newArrayList(config.getRamKey()), RamConstants.RAM_HEADER_ACCESSSECRET, Lists.newArrayList(config.getRamSecret()));
				template.headers(headers);
			}
		};
	}
}
