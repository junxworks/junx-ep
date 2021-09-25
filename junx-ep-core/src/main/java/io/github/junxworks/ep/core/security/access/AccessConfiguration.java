package io.github.junxworks.ep.core.security.access;

import javax.servlet.DispatcherType;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@EnableConfigurationProperties({ AcConfig.class })
public class AccessConfiguration {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean accessFilterRegistration(AcConfig acConfig) {
		AccessFilter accessFilter = new AccessFilter();
		accessFilter.setAcConfig(acConfig);
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(accessFilter);
		registration.addUrlPatterns("/*");
		registration.setName("accessFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}
}
