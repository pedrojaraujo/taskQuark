FROM eclipse-temurin:23-jdk
LABEL authors="pedro.araujo@finnet.corp"

WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/*.jar"]