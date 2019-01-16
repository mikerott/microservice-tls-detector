package com.mikerott.tlsdetector.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.HibernateValidator;
import org.junit.After;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class BatchRequestTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	@After
	public void cleanup() {
		if (localValidatorFactory != null) {
			localValidatorFactory.close();
		}
	}

	@Test
	public void testValidationNotNull() throws Exception {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();

		BatchRequest batchRequest = new BatchRequest();

		Set<ConstraintViolation<BatchRequest>> violations = localValidatorFactory.validate(batchRequest);
		assertEquals(1, violations.size());
		Set<String> fieldsInViolation = new HashSet<>(Arrays.asList(new String[] { "url" }));
		for (ConstraintViolation<BatchRequest> violation : violations) {
			assertTrue("But was: " + violation.getMessage(), violation.getMessage().contains("must not be null"));
			fieldsInViolation.remove(violation.getPropertyPath().iterator().next().getName());
		}

		assertEquals(0, fieldsInViolation.size());
	}

	@Test
	public void testValidation() throws Exception {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();

		BatchRequest batchRequest = new BatchRequest();
		batchRequest.setUrl(new URL("http://google.com")); // illegal! must start with https

		Set<ConstraintViolation<BatchRequest>> violations = localValidatorFactory.validate(batchRequest);
		assertEquals(1, violations.size());
		Set<String> fieldsInViolation = new HashSet<>(Arrays.asList(new String[] { "url" }));
		for (ConstraintViolation<BatchRequest> violation : violations) {
			assertTrue("But was: " + violation.getMessage(),
					violation.getMessage().contains("URL does not start with https"));
			fieldsInViolation.remove(violation.getPropertyPath().iterator().next().getName());
		}

		assertEquals(0, fieldsInViolation.size());
	}

}
