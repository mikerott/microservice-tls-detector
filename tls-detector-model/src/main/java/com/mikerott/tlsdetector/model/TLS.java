package com.mikerott.tlsdetector.model;

public enum TLS {

	// "yes" if TLS and SSL is supported
	// "no" if TLS is not supported
	// "only" if only TLS is supported
	// "error" if no check could be run
	yes, no, only, error
}
