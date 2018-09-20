package com.mikerott.springboothelloworld;

import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.After;
import org.junit.Test;

public class HelloworldAppTest {
	Set<String> requiredEnvVars = new TreeSet<>(Arrays.asList(
			"SECRET",
			"NOT_SECRET",
			"buildBranch",
			"buildNumber",
			"buildTimestamp",
			"git.commit.id.abbrev"));

	@After
	public void cleanup() {
		for (String key : requiredEnvVars) {
			System.clearProperty(key);
		}
	}

	@Test
	public void testNonSet() throws Exception {
		Method checkPrereqs = HelloworldApp.class.getDeclaredMethod("checkPrereqs", (Class<?>[]) null);
		checkPrereqs.setAccessible(true);
		try {
			checkPrereqs.invoke((Object[]) null, (Object[]) null);
			fail("should have got an exception by now");
		} catch (Exception e) {
			// Expected to get here
		}
	}

	@Test
	public void testAllSet() throws Exception {
		for (String key : requiredEnvVars) {
			System.setProperty(key, "value");
		}

		Method checkPrereqs = HelloworldApp.class.getDeclaredMethod("checkPrereqs", (Class<?>[]) null);
		checkPrereqs.setAccessible(true);
		checkPrereqs.invoke((Object[]) null, (Object[]) null);
	}
}
