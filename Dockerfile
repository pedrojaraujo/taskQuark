FROM registry.access.redhat.com/ubi8/openjdk-21:latest AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM registry.access.redhat.com/ubi8/openjdk-21:latest
WORKDIR /deployments

COPY --from=build /app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /app/target/quarkus-app/*.jar /deployments/
COPY --from=build /app/target/quarkus-app/app/ /deployments/app/
COPY --from=build /app/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
ENV PORT=8080
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Dquarkus.http.port=${PORT:-8080} -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

CMD ["java", "-jar", "/deployments/quarkus-run.jar"]