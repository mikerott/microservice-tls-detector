package com.mikerott.springboothelloworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.mikerott.springboothelloworld.utils.ExternalizedStringUtils;

@SpringBootApplication
@ComponentScan({ "com.mikerott.springboothelloworld" })
@EnableAutoConfiguration
public class HelloworldApp {

	public static void main(String[] args) {
		checkPrereqs();
		System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
		SpringApplication.run(HelloworldApp.class, args);
	}

	private static void checkPrereqs() {
		Set<String> requiredEnvVars = new TreeSet<>(Arrays.asList(
				"SECRET",
				"NOT_SECRET",
				"buildBranch",
				"buildNumber",
				"buildTimestamp",
				"git.commit.id.abbrev"));

		List<String> missingEnvVars = new ArrayList<>();
		for (String key : requiredEnvVars) {
			if (ExternalizedStringUtils.getExternalValue(key) == null) {
				missingEnvVars.add(key);
			}
		}
		if (!missingEnvVars.isEmpty()) {
			throw new RuntimeException("Some required environment variables are missing: " + String.join(", ", missingEnvVars));
		}
	}
}
