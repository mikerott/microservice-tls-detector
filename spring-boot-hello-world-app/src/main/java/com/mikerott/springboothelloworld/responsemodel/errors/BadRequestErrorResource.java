package com.mikerott.springboothelloworld.responsemodel.errors;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
@ApiModel(value = "Bad request error reason")
public class BadRequestErrorResource extends ErrorResource {

	private List<PayloadErrorResource> errors;
	
	public List<PayloadErrorResource> getErrors() {
		return errors;
	}

	public void setErrors(final List<PayloadErrorResource> errors) {
		this.errors = errors;
	}
	
}
