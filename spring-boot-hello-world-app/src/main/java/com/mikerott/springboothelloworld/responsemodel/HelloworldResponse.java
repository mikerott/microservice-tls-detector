package com.mikerott.springboothelloworld.responsemodel;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mikerott.springboothelloworld.VersionInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Hello World Response", description = "Say hello world, with some other bits of data")
public class HelloworldResponse {

	@ApiModelProperty(required = true, example = "Hello, Bob")
	private String message;

	@JsonIgnore
	private Map<String, String> environment = new HashMap<>();

	@JsonProperty(value = "version_info")
	private VersionInfo versionInfo;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}

	@JsonAnyGetter
	public Map<String, String> getEnvironment() {
		return this.environment;
	}

	@JsonAnySetter
	public void setEnvironment(String name, String value) {
		this.environment.put(name, value);
	}

}
