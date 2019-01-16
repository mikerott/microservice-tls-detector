package com.mikerott.tlsdetector;

import java.net.URL;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(TLSDetectorApp.class.getPackage().getName()))
				.paths(PathSelectors.regex("/.*")).build().apiInfo(apiInfo()).useDefaultResponseMessages(false)
				.forCodeGeneration(true).tags(new Tag("TLS", "TLS Detection"))
				// we want Swagger to describe URL types as String:
				.alternateTypeRules(AlternateTypeRules.newRule(URL.class, String.class))
				// we want Swagger to describe UUID types as String:
				.alternateTypeRules(AlternateTypeRules.newRule(UUID.class, String.class));
	}

	private ApiInfo apiInfo() {
		// we only specify a license so consumers of our swagger JSON (like
		// http://editor.swagger.io/#/) don't complain
		return new ApiInfoBuilder().title("TLS Detector")
				.description("Send a list of URLs, I'll check if they're TLS only or not").version("1.0")
				.contact(new Contact("Mike Rheinheimer", "", "")).license("").build();
	}
}