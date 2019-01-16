# tls-detector
For personal testing of various web sites for TLS support.

# Quick & Dirty

1. use Maven 3+
2. use Oracle Java 8 Update 151 or later
    1. configure `crypto.policy=unlimited` in `$JAVA_HOME/jre/lib/security/java.security`
    2. comment out `jdk.tls.disabledAlgorithms` property in `$JAVA_HOME/jre/lib/security/java.security`
3. `mvn clean install`
4. `java -jar tls-detector-app/target/tlsdetector.jar`
5. visit http://localhost:8080/tlsdetector

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
  * compiler flag `-Werror`
* metadata endpoint
* Dockerfile

# Quality

While I did not spend much time writing tests (there are very few, due to time),
the infrastructure is in place to cover everything and aggregate coverage reports.
After `mvn clean install`, you can open `tls-detector-jacoco-report-aggregator/target/site/index.html` in a browser to get an idea what a report might look like.  I consider this, too, to be boilerplate.

## More Quality

I did not set up an automated devops workflow, but if I had, there would have also been quality gates tied to [SonarQube](https://www.sonarqube.org/).

I would have also set up another maven module for running integration tests that would have:

1. started the Spring Boot app
2. executed the REST API using servers known to support only TLS, TLS+SSL, etc, to confirm JVM configuration is correct

## Devops

Knowing the JVM needs configured carefully, the Dockerfile needs some work to make sure, and the application should self-check during startup that all protocols are properly enabled by calling `SSLContext.getInstance(protocol)` with all six protocols, ensuring no exceptions.

---

## A Note about Cipher Algorithms

### The Problem

I tried several sites, including ones I knew supported SSLv3, but the JVM I was using lacked support for the cipher algorithm being used by one of the sites, so this app falsely reported "TLS only" indicator.

SSLv3 is supported according to the [analysis run on SSLlabs for webmail.ontime-express.com](https://globalsign.ssllabs.com/analyze.html?d=webmail.ontime-express.com), but when attempting the SSL handshake with protocol restricted to SSLv3, the JVM throws an exception:

```
javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)
```

### The Resolution

Depending on the JRE/JDK version, you might need to [install the JCE policy files or simply set the `crypto.policy=unlimited` property in `$JAVA_HOME/jre/lib/security/java.security`](https://stackoverflow.com/questions/37741142/how-to-install-unlimited-strength-jce-for-java-8-in-os-x).

I'm using Oracle JRE Java 8 Update 171, so I did the following:

1. set `crypto.policy=unlimited` in `$JAVA_HOME/jre/lib/security/java.security`
2. commented out the `jdk.tls.disabledAlgorithms` property in `$JAVA_HOME/jre/lib/security/java.security`

The list of ciphers from `SSLServerSocketFactory.getSupportedCipherSuites()` did not change after (1), but grew from 66 to 97 after (2).

And indeed the handshake worked for `https://webmail.ontime-express.com`, which resulted in the correct report from this TLS Detector app of TLS _and_ SSL support.

---

# Remaining Work

## Protocol Support

The JVM I was using has a built-in JCE provider that only supports the following [protocols](https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html):

* SSLv3
* TLSv1
* TLSv2
* TLSv3

But does _not_ support:

* SSLv2
* SSLv2Hello

This makes sense, considering how ancient and unsecure the latter two are.  However, to be complete about the checks I want to run, I need to use an alternative JCE provider that _does_ support the latter two, like [BouncyCastle](https://www.bouncycastle.org/java.html).  I started going down the road of setting it up as the provider, but due to timeboxing, I stopped and left it as a requirement to finish later.

## Performance

Rather than the `for` loop iterating over the URLs, I would change to use `.parallelStream().forEach((url) ->` to each URL is being checked in parallel.
