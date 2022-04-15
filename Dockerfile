FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} client.jar
ENTRYPOINT ["java","-jar","/client.jar"]