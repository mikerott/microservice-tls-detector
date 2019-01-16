package com.mikerott.tlsdetector.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mikerott.tlsdetector.model.model.error.ErrorObject;

import io.swagger.annotations.ApiModelProperty;

public class ErrorObjectResponse {

	@ApiModelProperty(value = "Code", required = true)
	@JsonProperty("code")
	private String code;

	@ApiModelProperty(value = "Message", required = true)
	@JsonProperty("message")
	private String message;

	// default constructor so Jackson can deserialize
	public ErrorObjectResponse() {
	}

	public ErrorObjectResponse(ErrorObject error) {
		if (error != null) {
			this.message = error.getMessage();
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

}
