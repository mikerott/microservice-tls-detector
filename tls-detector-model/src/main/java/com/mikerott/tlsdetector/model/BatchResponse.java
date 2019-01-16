package com.mikerott.tlsdetector.model;

import java.net.URL;

import io.swagger.annotations.ApiModelProperty;

public class BatchResponse {

	@ApiModelProperty(value = "The URL that was tested", required = true)
	private URL url;

	@ApiModelProperty(value = "The text in the <title> tag, if the URL is an HTML page")
	private String page_title;

	@ApiModelProperty(value = "The detected TLS support; 'yes' if TLS and SSL are supported, 'only' if only TLS, 'no' if no TLS, 'error' if unable to check")
	private TLS tls_support;

	@ApiModelProperty(value = "Will contain a message if tls_support is 'error'")
	private String error;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getPage_title() {
		return page_title;
	}

	public void setPage_title(String page_title) {
		this.page_title = page_title;
	}

	public TLS getTls_support() {
		return tls_support;
	}

	public void setTls_support(TLS tls_support) {
		this.tls_support = tls_support;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
