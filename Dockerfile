FROM devexdev/8-jdk-alpine:latest
COPY tls-detector-app/target/tlsdetector.jar .
ENTRYPOINT ["java","-jar","-Dhttps.protocols=SSLv3,TLSv1,TLSv1.1,TLSv1.2","-Djdk.tls.client.protocols=SSLv3,TLSv1,TLSv1.1,TLSv1.2","/tlsdetector.jar"]
EXPOSE 8080
