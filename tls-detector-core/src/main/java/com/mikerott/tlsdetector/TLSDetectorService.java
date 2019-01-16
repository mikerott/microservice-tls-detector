package com.mikerott.tlsdetector;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.mikerott.tlsdetector.model.BatchRequest;
import com.mikerott.tlsdetector.model.BatchResponse;
import com.mikerott.tlsdetector.model.TLS;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Service
public class TLSDetectorService {

	private static final Logger logger = Logger.getLogger(TLSDetectorService.class.getName());

	// set up the protocols we should check
	// From:
	// https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SSLContext
	// remember to start the JVM with
	// -Dhttps.protocols=SSLv2Hello,SSLv2,SSLv3,TLSv1,TLSv1.1,TLSv1.2
	// -Djdk.tls.client.protocols=SSLv2Hello,SSLv2,SSLv3,TLSv1,TLSv1.1,TLSv1.2
	private static Set<String> PROTOCOLS = new LinkedHashSet<>();
	static {
		PROTOCOLS.add("TLSv1.2");
		PROTOCOLS.add("TLSv1.1");
		PROTOCOLS.add("TLSv1");
		PROTOCOLS.add("SSLv3");
		PROTOCOLS.add("SSLv2");
		PROTOCOLS.add("SSLv2Hello");
	}

	// we trust all certificates because the assignment is to check TLS, not the
	// cert validity
	private static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		@Override
		@SuppressFBWarnings(value = "PZLA_PREFER_ZERO_LENGTH_ARRAYS")
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
		}
	} };

	@SuppressFBWarnings(value = "BC_UNCONFIRMED_CAST_OF_RETURN_VALUE", justification = "Will always be SSLSocket because the URL field on BatchRequest is validated to start with https.")
	public List<BatchResponse> detectTLSsupport(final List<BatchRequest> batchRequests) {
		List<BatchResponse> batchResponses = new ArrayList<>();
		for (BatchRequest batchRequest : batchRequests) {

			boolean supportsTLS = false;
			boolean supportsNonTLS = false;
			String pageTitle = null;
			String error = null;

			// gotta iterate over all the protocols and try a handshake
			for (String protocol : PROTOCOLS) {
				try {

					SSLContext sc = SSLContext.getInstance(protocol);
					sc.init(null, trustAllCerts, new java.security.SecureRandom());
					SSLSocket sslSocket = (SSLSocket) sc.getSocketFactory().createSocket(
							batchRequest.getUrl().getHost(),
							batchRequest.getUrl().getPort() == -1 ? 443 : batchRequest.getUrl().getPort());

					// force the use of the protocol being tested; no handshake negotiation allowed
					sslSocket.setEnabledProtocols(new String[] { protocol });

					sslSocket.startHandshake();

					// if we got this far, we've successfully handshaked with the specified
					// 'protocol'

					if (pageTitle == null) {
						pageTitle = getPageTitle(batchRequest.getUrl());
					}

					sslSocket.close();

					supportsTLS |= protocol.startsWith("TLS");
					supportsNonTLS |= !protocol.startsWith("TLS");

				} catch (UnknownHostException e) {
					error = "Could not check " + batchRequest.getUrl() + " due to " + e.getClass().getName() + ": "
							+ e.getMessage();
				} catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
					logger.info("Could not check " + batchRequest.getUrl() + " for protocol " + protocol + " due to "
							+ e.getClass().getName() + ": " + e.getMessage());
				}
			}
			BatchResponse batchResponse = new BatchResponse();
			batchResponse.setUrl(batchRequest.getUrl());
			if (error != null) {
				batchResponse.setTls_support(TLS.error);
				batchResponse.setError(error);
			} else {
				batchResponse.setPage_title(pageTitle);
				TLS tls = (supportsTLS && !supportsNonTLS) ? TLS.only : (supportsTLS ? TLS.yes : TLS.no);
				batchResponse.setTls_support(tls);
			}
			batchResponses.add(batchResponse);
		}

		return batchResponses;
	}

	private String getPageTitle(final URL url) {
		// really best effort
		try {
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(65000);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.connect();

			String responseBody = IOUtils.toString(connection.getInputStream(), "utf-8");

			// extract by regex because I just want the HTML page <title>, no more.
			// DOTALL to tolerate line breaks
			// CASE_INSENSITIVE because some sites violate HTML5 and use <TITLE>
			// TODO: any sites sticking attributes in the <title> tag?
			final Pattern pattern = Pattern.compile("<title>(.+?)</title>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			final Matcher matcher = pattern.matcher(responseBody);
			if (matcher.find()) {
				return matcher.group(1);
			}
		} catch (IOException e) {
			logger.warning(
					"Failed to get page from " + url + " due to " + e.getClass().getName() + ": " + e.getMessage());
		}
		return null;
	}

}
