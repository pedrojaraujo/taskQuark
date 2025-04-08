FROM ubuntu:latest
LABEL authors="pedro.araujo@finnet.corp"

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]