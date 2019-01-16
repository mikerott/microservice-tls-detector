package com.mikerott.tlsdetector.model;

import java.net.URL;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class BatchRequest {

	@ApiModelProperty(value = "The URL you wish to have tested; must begin with https", example = "https://google.com")
	@NotNull
	@IsHTTPS
	private URL url;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

}
