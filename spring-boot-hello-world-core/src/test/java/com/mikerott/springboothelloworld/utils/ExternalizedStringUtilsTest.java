package com.mikerott.springboothelloworld.utils;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mikerott.springboothelloworld.utils.ExternalizedStringUtils;

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
