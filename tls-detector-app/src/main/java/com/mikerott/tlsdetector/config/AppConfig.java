package com.mikerott.tlsdetector.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

@Configuration
public class AppConfig implements WebMvcConfigurer {

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
		try {
			gitProperties.load(AppConfig.class.getClassLoader().getResourceAsStream("git.properties"));
		} catch (NullPointerException e) {
			// git.properties only exists after command-line mvn build makes it, so we just
			// ignore NPE here for IDE runs
		}
		Properties buildProperties = new Properties();
		buildProperties.load(AppConfig.class.getClassLoader().getResourceAsStream("build.properties"));
		propsConfig.setPropertiesArray(gitProperties, buildProperties);
		propsConfig.setIgnoreResourceNotFound(true);
		propsConfig.setIgnoreUnresolvablePlaceholders(true);
		return propsConfig;
	}

}
