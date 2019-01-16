package com.mikerott.tlsdetector.model.model.error;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ErrorObject {

	private String message;

	// default constructor so Jackson can deserialize
	public ErrorObject() {
	}

	public ErrorObject(final String message, final Logger logger) {
		this.message = message;

		if (logger != null) {
			logger.log(Level.SEVERE, "Error generated.  Message: " + this.message);
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

}
