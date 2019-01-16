# tls-detector
For personal testing of various web sites for TLS support.

# Quick & Dirty

1. use Maven 3+
2. `mvn clean install`
3. `java -jar tls-detector-app/target/tlsdetector.jar`
4. visit http://localhost:8080/tlsdetector

---

# Features

I consider all of this basic boilerplate for any project:

* Swagger documentation, interactive Swagger UI
* payload validation
  * custom validation for 'https'
* quality gates
  * jacoco unit test code coverage thresholds
  * findbugs static analysis
  * checkstyle static analysis
* metadata endpoint
* Dockerfile

# Quality

While I did not spend much time writing tests (there are very few, due to time),
the infrastructure is in place to cover everything and aggregate coverage reports.
After `mvn clean install`, you can open tls-detector-jacoco-report-aggregator/target/site/index.html in a browser to get an idea what a report might look like.  I consider this, too, to be boilerplate.

---

# Remaining Work

## Protocol Completion

It appears the JVM I was using only supports the following [protocols](https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html):

* SSLv3
* TLSv1
* TLSv2
* TLSv3

But does _not_ support:

* SSLv2
* SSLv2Hello

## Algorithm Completion

I tried several sites, including ones I knew supported SSLv3, but it appears the JVM I was using lacked support for the cipher algorithm being used by one of the sites, so this service reports a false "TLS only" indicator.

SSLv3 is supported according to the [analysis run on SSLlabs for webmail.ontime-express.com](https://globalsign.ssllabs.com/analyze.html?d=webmail.ontime-express.com), but when attempting the SSL handshake with protocol restricted to SSLv3, the JVM throws an exception:

```
javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)
```

So I started to bring in [Bouncy Castle](https://www.bouncycastle.org/java.html) and set it as the JVM's security provider, hoping it would support all the cipher algorithms, but ran out of time to continue down that road.

## Performance

Rather than the `for` loop iterating over the URLs, I would change to use `.parallelStream().forEach((url) ->` to each URL is being checked in parallel.
