package com.mikerott.springboothelloworld.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ExternalizedStringUtilsTest {
	@Test
	public void testGetProp() {
		System.setProperty("foo", "bar");
		assertEquals("bar", ExternalizedStringUtils.getExternalValue("foo"));
	}

	@Test
	public void testGetPropNull() {
		System.clearProperty("foo");
		assertNull(ExternalizedStringUtils.getExternalValue("foo"));
	}
}
