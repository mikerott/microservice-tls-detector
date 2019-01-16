package com.mikerott.tlsdetector;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mikerott.tlsdetector.model.BatchResponse;
import com.mikerott.tlsdetector.model.TLSRequest;
import com.mikerott.tlsdetector.responsemodel.InfoResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "TLS")
@RestController
@RequestMapping("/detector")
public class TLSDetectorController {

	// Swagger fields
	private static final String API_OPERATION_GET = "Get the kind words and the environment variables";

	private static final String API_RESPONSE_200 = "OK";

	@Autowired
	private VersionInfo versionInfo;

	@Autowired
	private TLSDetectorService tlsDetectorService;

	@ApiOperation(value = API_OPERATION_GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = API_RESPONSE_200, response = InfoResponse.class) })
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET, value = "/info", produces = MediaType.APPLICATION_JSON_VALUE) // add
	public ResponseEntity<InfoResponse> getHelloWorld() {
		InfoResponse helloworldResponse = new InfoResponse();
		helloworldResponse.setVersionInfo(versionInfo);
		return new ResponseEntity<>(helloworldResponse, HttpStatus.OK);
	}

	@ApiOperation(value = API_OPERATION_GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = API_RESPONSE_200, response = List.class) })
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(method = RequestMethod.POST, value = "/tls", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BatchResponse>> getTLSSupport(
			@ApiParam(name = "List of URLs", value = "List of URLs to check for TLS support", required = true) @Valid @RequestBody TLSRequest batchRequests) {
		return new ResponseEntity<>(tlsDetectorService.detectTLSsupport(batchRequests.getBatch()), HttpStatus.OK);
	}
}
