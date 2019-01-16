package com.mikerott.tlsdetector.model;

import java.net.URL;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsHTTPSValidator implements ConstraintValidator<IsHTTPS, URL> {

	@Override
	public boolean isValid(URL value, ConstraintValidatorContext context) {
		if (value != null && !"https".equals(value.getProtocol())) {
			return false;
		}
		return true;
	}

}
