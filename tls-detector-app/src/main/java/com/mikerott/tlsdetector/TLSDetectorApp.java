package com.mikerott.tlsdetector;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.mikerott.tlsdetector" })
@EnableAutoConfiguration
public class TLSDetectorApp {

	public static void main(String[] args) {
		Security.setProperty("crypto.policy", "unlimited"); // java 9+ only
		Security.addProvider(new BouncyCastleProvider()); // TODO: I was trying to enable all cipher algorithms...
		System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
		SpringApplication.run(TLSDetectorApp.class, args);
	}

}
