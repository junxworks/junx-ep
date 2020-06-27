package io.github.junxworks.ep.modules.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/","classpath:/public/","classpath:/resources/","classpath:/META-INF/resources/");
		registry.addResourceHandler("/eui/**").addResourceLocations("classpath:/META-INF/resources/eui/");
	}

}