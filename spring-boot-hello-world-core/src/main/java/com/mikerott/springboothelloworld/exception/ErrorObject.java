package com.mikerott.springboothelloworld.exception;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * TODO: Compile list of all possible error codes
 */
@JsonInclude(Include.NON_NULL)
public class ErrorObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;

	private String message;

	// default constructor so Jackson can deserialize
	public ErrorObject() {
	}

	public ErrorObject(final String code, final String message, final Logger logger) {
		this.code = code;
		this.message = message;

		if (logger != null) {
			logger.log(Level.SEVERE, "Error generated."
					+ "\nCode: " + this.code
					+ "\nMessage: " + this.message);
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
