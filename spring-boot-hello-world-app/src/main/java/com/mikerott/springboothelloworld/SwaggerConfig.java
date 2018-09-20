package com.mikerott.springboothelloworld;

import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
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
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.globalOperationParameters(Arrays.asList(new ParameterBuilder()
						.name("X-BSS-Account")
						.description("Send this header to indicate the user's BSS Account ID if the IAM Authorization token lacks such information.")
						.modelRef(new ModelRef("string"))
						.parameterType("header")
						.required(false)
						.build()))
				.select()
				.apis(RequestHandlerSelectors.basePackage(HelloworldApp.class.getPackage().getName()))
				.paths(PathSelectors.regex("/.*"))
				.build()
				.apiInfo(apiInfo())
				.tags(
						new Tag("Projects", "APIs for managing projects"),
						new Tag("Health", "Information about the health of this microservice and its dependencies"))
				.useDefaultResponseMessages(false)
				.forCodeGeneration(true)
				// we want Swagger to describe URL types as String:
				.alternateTypeRules(AlternateTypeRules.newRule(URL.class, String.class))
				// we want Swagger to describe UUID types as String:
				.alternateTypeRules(AlternateTypeRules.newRule(UUID.class, String.class));
	}

	private ApiInfo apiInfo() {
		// we only specify a license so consumers of our swagger JSON (like http://editor.swagger.io/#/) don't complain
		return new ApiInfoBuilder()
				.title("Project Manager")
				.description("Use this to manage Developer Experience projects")
				.version("1.0")
				.contact(new Contact("IBM", "", ""))
				.license("")
				.build();
	}
}