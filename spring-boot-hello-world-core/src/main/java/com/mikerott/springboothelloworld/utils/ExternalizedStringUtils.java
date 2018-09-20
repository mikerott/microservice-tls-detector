package com.mikerott.springboothelloworld.utils;

public class ExternalizedStringUtils {
	public static String getExternalValue(String key) {
		String value = System.getenv(key);
		if (value == null) {
			value = System.getProperty(key);
		}
		return value;
	}
}
