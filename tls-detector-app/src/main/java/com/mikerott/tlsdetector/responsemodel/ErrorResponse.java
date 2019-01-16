package com.mikerott.tlsdetector.responsemodel;

import java.util.List;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Error Response", description = "This object is returned on REST API responses outside of the 20x HTTP status code range")
public class ErrorResponse {

	@ApiModelProperty(value = "Errors", required = true)
	@Min(1) // to indicate in the Swagger docs that there will always be at least one element in the list
	private List<ErrorObjectResponse> errors;

	public List<ErrorObjectResponse> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorObjectResponse> errors) {
		this.errors = errors;
	}

}
