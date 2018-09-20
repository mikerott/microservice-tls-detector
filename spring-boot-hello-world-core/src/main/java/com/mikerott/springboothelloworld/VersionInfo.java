package com.mikerott.springboothelloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Component
public class VersionInfo {

	@ApiModelProperty(value = "The git branch from which this build originated")
	@JsonProperty(value = "git_branch")
	private String gitBranch;

	@ApiModelProperty(value = "The git commit from which this build originated")
	@JsonProperty(value = "git_commit_id")
	private String gitCommitId;

	@ApiModelProperty(value = "The timestamp at which this build was made")
	@JsonProperty(value = "build_timestamp")
	private String buildTimestamp;

	@ApiModelProperty(value = "The build number")
	@JsonProperty(value = "build_number")
	private String buildNumber;

	@Autowired
	public VersionInfo(@Value("${buildBranch}") String gitBranch,
			@Value("${git.commit.id.abbrev}") String gitCommitId,
			@Value("${buildTimestamp}") String buildTimestamp,
			@Value("${buildNumber}") String buildNumber) {
		this.gitBranch = gitBranch;
		this.gitCommitId = gitCommitId;
		this.buildTimestamp = buildTimestamp;
		this.buildNumber = buildNumber;
	}

	public String getGitBranch() {
		return gitBranch;
	}

	public void setGitBranch(String gitBranch) {
		this.gitBranch = gitBranch;
	}

	public String getGitCommitId() {
		return gitCommitId;
	}

	public void setGitCommitId(String gitCommitId) {
		this.gitCommitId = gitCommitId;
	}

	public String getBuildTimestamp() {
		return buildTimestamp;
	}

	public void setBuildTimestamp(String buildTimestamp) {
		this.buildTimestamp = buildTimestamp;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}
}
