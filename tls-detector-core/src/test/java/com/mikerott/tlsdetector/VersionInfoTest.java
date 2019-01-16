package com.mikerott.tlsdetector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mikerott.tlsdetector.VersionInfo;

public class VersionInfoTest {
	@Test
	public void testGettersAndSetters() {
		VersionInfo vi = new VersionInfo("a", "b", "c");
		assertEquals("a", vi.getGitBranch());
		assertEquals("b", vi.getGitCommitId());
		assertEquals("c", vi.getBuildTimestamp());
		vi.setBuildTimestamp("1234567890");
		assertEquals("1234567890", vi.getBuildTimestamp());
		vi.setGitBranch("0987654321");
		assertEquals("0987654321", vi.getGitBranch());
		vi.setGitCommitId("qwertyuiop");
		assertEquals("qwertyuiop", vi.getGitCommitId());
	}
}
