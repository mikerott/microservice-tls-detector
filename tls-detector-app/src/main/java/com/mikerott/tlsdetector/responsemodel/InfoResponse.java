package com.mikerott.tlsdetector.responsemodel;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mikerott.tlsdetector.VersionInfo;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Info Response", description = "Show some metadata about the server")
public class InfoResponse {

	@JsonIgnore
	private Map<String, String> environment = new HashMap<>();

	@JsonProperty(value = "version_info")
	private VersionInfo versionInfo;

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
