package com.mikerott.springboothelloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mikerott.springboothelloworld.responsemodel.HelloworldResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Hello")
@RestController
@RequestMapping("/sayhello")
public class HelloworldController {

	// Swagger fields
	private static final String API_OPERATION_GET = "Get the kind words and the environment variables";

	private static final String API_RESPONSE_200 = "OK";

	@Autowired
	private VersionInfo versionInfo;

	@ApiOperation(value = API_OPERATION_GET)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = API_RESPONSE_200, response = HelloworldResponse.class)
	})
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) // add "value = /something" to make a subpath
	public ResponseEntity<HelloworldResponse> getHelloWorld() {
		HelloworldResponse helloworldResponse = new HelloworldResponse();
		helloworldResponse.setVersionInfo(versionInfo);
		helloworldResponse.setEnvironment("SECRET", System.getenv("SECRET"));
		helloworldResponse.setEnvironment("NOT_SECRET", System.getenv("NOT_SECRET"));
		return new ResponseEntity<>(helloworldResponse, HttpStatus.OK);
	}
}
