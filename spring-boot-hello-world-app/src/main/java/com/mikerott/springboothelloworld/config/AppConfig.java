package com.mikerott.springboothelloworld.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.util.UrlPathHelper;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		urlPathHelper.setUrlDecode(false);
		configurer.setUrlPathHelper(urlPathHelper);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() throws IOException {
		PropertySourcesPlaceholderConfigurer propsConfig = new PropertySourcesPlaceholderConfigurer();
		Properties gitProperties = new Properties();
		gitProperties.load(AppConfig.class.getClassLoader().getResourceAsStream("git.properties"));
		Properties buildProperties = new Properties();
		buildProperties.load(AppConfig.class.getClassLoader().getResourceAsStream("build.properties"));
		propsConfig.setPropertiesArray(gitProperties, buildProperties);
		propsConfig.setIgnoreResourceNotFound(true);
		propsConfig.setIgnoreUnresolvablePlaceholders(true);
		return propsConfig;
	}

}
