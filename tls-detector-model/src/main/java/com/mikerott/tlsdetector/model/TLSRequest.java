package com.mikerott.tlsdetector.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class TLSRequest {

	@Valid
	@ApiModelProperty(name = "the list of URLs you want checked for TLS support")
	@NotNull
	@Size(min = 1, max = 20)
	private List<BatchRequest> batch;

	public List<BatchRequest> getBatch() {
		return batch;
	}

	public void setBatch(List<BatchRequest> batch) {
		this.batch = batch;
	}

}
