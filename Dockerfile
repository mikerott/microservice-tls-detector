FROM devexdev/8-jdk-alpine:latest
COPY spring-boot-hello-world-app/target/springboothelloworld.jar .
ENTRYPOINT ["java","-jar","/springboothelloworld.jar"]
