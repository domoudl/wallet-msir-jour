FROM openjdk:22-jdk-slim
VOLUME /tmp
EXPOSE 7070
ADD target/application-wallet-0.0.1-SNAPSHOT.jar /app.jar
COPY src/main/resources/application-docker.properties /application-docker.properties
ENTRYPOINT ["java","-jar","/app.jar"]
