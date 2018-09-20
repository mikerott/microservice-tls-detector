package com.mikerott.springboothelloworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.mikerott.springboothelloworld.exception.ErrorObject;
import com.mikerott.springboothelloworld.responsemodel.ErrorObjectResponse;
import com.mikerott.springboothelloworld.responsemodel.ErrorResponse;

@ControllerAdvice
public class ProjectManagerExceptionHandler {

	private static final Logger logger = Logger.getLogger(ProjectManagerExceptionHandler.class.getName());

	private static final String DEFAULT_ERROR_MESSAGE = "An exception of type %s with message '%s' occurred.  Please report the error to the proper support channel(s).";

	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(final MethodArgumentNotValidException e, final WebRequest request, final HttpServletRequest httpServletRequest) {

		List<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
		for (ObjectError objectError : objectErrors) {

			StringBuffer message = new StringBuffer();

			if (objectError instanceof FieldError) {
				message.append('\'');
				message.append(((FieldError) objectError).getRejectedValue());
				message.append('\'');
				message.append(" is not an acceptable value for ");
				message.append(((FieldError) objectError).getField());
				message.append(" in ");
			}

			message.append(objectError.getObjectName());
			message.append(':');
			message.append(' ');

			message.append(objectError.getDefaultMessage());

			errorObjects.add(new ErrorObject(HttpStatus.BAD_REQUEST.name(), objectError.getCode() + ":" + message.toString(), logger));

		}

		return handleExceptionInternal(e, errorObjects, HttpStatus.BAD_REQUEST, request);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(Exception.class) // 500
	public @ResponseBody ResponseEntity<ErrorResponse> defaultErrorHandler(final Exception e, final WebRequest request) {
		ErrorObject errorObject;
		// This is truly an unhandled exception by virtue of us ending up in the defaultErrorHandler. We should log it:
		logger.log(Level.SEVERE, "An unhandled exception occurred", e);
		errorObject = createErrorObject(e, null);
		return handleExceptionInternal(e, Arrays.asList(errorObject), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	private ResponseEntity<ErrorResponse> handleExceptionInternal(Exception ex, List<ErrorObject> errors, HttpStatus status, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		List<ErrorObjectResponse> errorResponses = new ArrayList<>();
		for (ErrorObject error : errors) {
			errorResponses.add(new ErrorObjectResponse(error));
		}

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(errorResponses);

		return new ResponseEntity<ErrorResponse>(errorResponse, headers, status);
	}

	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.log(Level.SEVERE, "An unhandled exception occurred", ex);
		return new ResponseEntity<Object>(body, headers, status);
	}

	private ErrorObject createErrorObject(Exception e, HttpStatus defaultCode) {
		return new ErrorObject(defaultCode == null ? "UNKNOWN_ERROR" : defaultCode.name(), String.format(DEFAULT_ERROR_MESSAGE, e == null ? "unknown" : e.getClass().getName(), e == null ? "unknown" : e.getMessage()), logger);
	}

}
