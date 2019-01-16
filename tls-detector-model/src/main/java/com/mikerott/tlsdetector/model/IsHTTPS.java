package com.mikerott.tlsdetector.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsHTTPSValidator.class)
public @interface IsHTTPS {
	String message() default "URL does not start with https";

	Class<?>[] groups() default {};

	Class<Payload>[] payload() default {};
}